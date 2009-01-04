package gems.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Usefull IO gems.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class IOUtils {

	/**
	 * A size of chunk for a reading of byte content for an input stream..
	 */
	private static final int CHUNK_SIZE = 4096;

	/**
	 * Just disables an instance creation.
	 *
	 * @throws UnsupportedOperationException always.
	 */
	private IOUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Closes a given closeable object.
	 *
	 * @param c an object to close.
	 *
	 * @throws IllegalArgumentException if {@code c} is {@code null}.
	 * @throws RuntimeIOException if any {@code IOException} occurs during an object closing.
	 */
	public static void close(final Closeable c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		try {
			c.close();
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	/**
	 * Reads bytes from a given input stream. The entire stream is read before
	 * returning and the input stream is always closed after reading, regardless
	 * of a reading success. This method never returns {@code null}.
	 *
	 * @param input an input stream to read.
	 *
	 * @return a byte content read from a stream.
	 *
	 * @throws IllegalArgumentException if {@code input} is {@code null}.
	 * @throws RuntimeIOException if any {@code IOException} occurs during stream reading or closing.
	 */
	public static ByteContent read(final InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		final ByteContent result = new AggregatedByteContent();
		final byte buffer[] = new byte[CHUNK_SIZE];
		int bytesRead;
		try {
			while ((bytesRead = input.read(buffer)) > 0) {
				result.append(new AtomicByteContent(buffer, bytesRead));
			}
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			close(input);
		}
		return result;
	}
	
}
