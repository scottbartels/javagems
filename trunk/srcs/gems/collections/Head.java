package gems.collections;

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

/**
 * A "head" of persistenlty stored serializable collection entry.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of the stored object
 */
final class Head<T extends Serializable> {

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
