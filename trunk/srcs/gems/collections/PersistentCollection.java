package gems.collections;

import gems.Experimental;
import gems.ShouldNeverHappenException;
import gems.io.IOUtils;
import gems.io.RuntimeIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * Storing {@code null} objects is not permitted. Stored objects should not rely on defalut {@code equals()} method.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@Experimental public final class PersistentCollection<E extends Serializable> implements Collection<E>, RandomAccess {

    private final List<Head<E>> heads = new ArrayList<Head<E>>();

    /**
     * {@inheritDoc}
     */
    public int size() {
        return heads.size();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return heads.isEmpty();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code e} is {@code null}.
     */
    public boolean add(final E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        return heads.add(new Head<E>(e));
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(final Object o) {
        if (o != null) {
            for (final Head<E> head : heads) {
                if (head.getData().equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(final Object o) {
        if (o != null) {
            for (int i = 0; i < heads.size(); i++) {
                if (heads.get(i).getData().equals(o)) {
                    heads.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code c} is {@code null}.
     */
    public boolean containsAll(final Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        for (final Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code c} is {@code null}.
     */
    public boolean addAll(final Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c.isEmpty()) {
            return false;
        }
        for (final E e : c) {
            add(e);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code c} is {@code null}.
     */
    public boolean removeAll(final Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (c.isEmpty()) {
            return false;
        }
        boolean changed = false;
        for (int i = heads.size() - 1; i >= 0; i--) {
            if (c.contains(heads.get(i).getData())) {
                heads.remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code c} is {@code null}.
     */
    public boolean retainAll(final Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return false;
        }
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean changed = false;
        for (int i = heads.size() - 1; i >= 0; i--) {
            if (!c.contains(heads.get(i).getData())) {
                heads.remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        heads.clear();
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator() {
        return new IteratorImpl<E>(heads.iterator());
    }

    // TODO: NOT YET IMPLEMENTED PART OF THE INTERFACE

    public Object[] toArray() { // todo: violates contract
        final Object[] result = new Object[heads.size()];
        for (int i = 0; i < heads.size(); i++) {
            result[i] = heads.get(i).getData();
        }
        return result;
    }

    public <T> T[] toArray(final T[] a) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // ITERATOR

    private static final class IteratorImpl<E extends Serializable> implements Iterator<E> {

        private final Iterator<Head<E>> heads;

        IteratorImpl(final Iterator<Head<E>> heads) {
            if (heads == null) {
                throw new IllegalArgumentException();
            }
            this.heads = heads;
        }

        public boolean hasNext() {
            return heads.hasNext();
        }

        public E next() {
            return heads.next().getData();
        }

        public void remove() {
            heads.remove();
        }

    }

    // HEAD

    private static final class Head<T extends Serializable> {

        private final File storage = IOUtils.createTemporaryFile(true);

        // todo: add hashcode cache

        // todo: add compression

        // todo: add encryption

        // todo: add checksum

        // todo: add equals() and hashCode() based on serialized data (think twice)

        Head(final T o) {
            if (o == null) {
                throw new IllegalArgumentException();
            }
            store(o);
        }

        synchronized T getData() {
            return load();
        }

        private void store(final Serializable s) {
            try {
                final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storage));
                try {
                    out.writeObject(s);
                } finally {
                    IOUtils.close(out);
                }
            } catch (final IOException e) {
                throw new RuntimeIOException(e);
            }
        }

        @SuppressWarnings({"unchecked"})
        private T load() {
            try {
                final ObjectInputStream in = new ObjectInputStream(new FileInputStream(storage));
                try {
                    return (T) in.readObject();
                } catch (final ClassNotFoundException e) {
                    throw new ShouldNeverHappenException(e); // todo: introduce something like "StorageConsistencyException"
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                throw new RuntimeIOException(e);
            }
        }

        // todo: add method for manual deletion

        @SuppressWarnings({"ResultOfMethodCallIgnored"})
        @Override protected void finalize() throws Throwable {
            super.finalize();
            storage.delete();
        }

    }

}
