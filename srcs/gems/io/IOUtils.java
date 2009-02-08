package gems.io;

import gems.ExceptionHandler;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Useful IO gems.
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
		close(c, IOExceptionWrapper.INSTANCE);
	}

	/**
	 * Closes a given {@code Closeable} object and handle occasional {@code IOException} by a given handler.
	 *
	 * @param c an object to be closed; it should not be {@code null}.
	 * @param h an exception handler; it shoutd not be {@code null}.
	 */
	private static void close(final Closeable c, final ExceptionHandler<? super IOException> h) {
		assert c != null;
		assert h != null;
		try {
			c.close();
		} catch (final IOException e) {
			h.handle(e);
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
		boolean exceptionThrown = true;
		try {
			final ExpandableByteContent result = new AggregatedByteContent();
			final byte buffer[] = new byte[CHUNK_SIZE];
			int bytesRead;
			while ((bytesRead = input.read(buffer)) > 0) {
				result.append(new AtomicByteContent(buffer, bytesRead));
			}
			exceptionThrown = false;
			return result;
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			/*
			 * If exeception was thrown in 'try' block, avoid throwing exception
			 * from this 'finally' block to prevent possible swallowed exception.
			 */
			if (exceptionThrown) {
				close(input, ExceptionHandler.NULL_HANDLER);
			} else {
				close(input, IOExceptionWrapper.INSTANCE);
			}
		}
	}

	/**
	 * Exception handler wrapping passed {@code IOException} by {@code RuntimeIOException}.
	 */
	private static final class IOExceptionWrapper implements ExceptionHandler<IOException> {

		/**
		 * A (lazy initialized) singleton instance of the wrapper.
		 */
		private static final IOExceptionWrapper INSTANCE = new IOExceptionWrapper();

		/**
		 * Rethrows a given {@code IOException} encabsulated by {@code RuntimeIOException).
		 *
		 * @param e an IO Exception.
		 *
		 * @throws a new {@code RuntimeIOException} encapsulating a given {@code IOException}; always.
		 */
		public void handle(final IOException e) {
			throw new RuntimeIOException(e);
		}

	}

}
