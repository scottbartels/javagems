package gems.collections;

import java.io.Serializable;
import java.util.*;

/**
 * Storing {@code null} objects is not permitted. Stored objects should not rely on defalut {@code equals()} method.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FSBackedList<E extends Serializable> implements Collection<E>, RandomAccess {

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

//	public E get(final int index) {
//		return heads.get(index).getData();
//	}

	// TODO: NOT YET IMPLEMENTED PART OF THE INTERFACE

	public Object[] toArray() { // todo: violates contract if not synchronized
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

	private static final class Head<E extends Serializable> {

		private final E fake;

		Head(final E e) {
			if (e == null) {
				throw new IllegalArgumentException();
			}
			fake = e;
		}

		E getData() {
			return fake;
		}

	}

}
