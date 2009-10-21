package gems.io;

import gems.Checks;
import gems.ExceptionHandler;
import gems.UnexpectedNullException;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	 * @throws UnexpectedNullException if {@code c} is {@code null}.
	 * @throws RuntimeIOException if any {@code IOException} occurs during an object closing.
	 */
	public static void close(final Closeable c) {
		close(Checks.ensureNotNull(c), IOExceptionWrapper.INSTANCE);
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
	 * Gives an input stream for a given file.
	 *
	 * @param file a file.
	 *
	 * @return an input stream for a given file.
	 *
	 * @throws UnexpectedNullException if {@code file} is {@code null}.
	 * @throws RuntimeIOException if any {@code IOException} occurs during stream opening.
	 */
	public static InputStream asInputStream(final File file) {
		try {
			return new FileInputStream(Checks.ensureNotNull(file));
		} catch (final FileNotFoundException e) {
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
	 * @throws UnexpectedNullException if {@code input} is {@code null}.
	 * @throws RuntimeIOException if any {@code IOException} occurs during stream reading or closing.
	 */
	public static ByteContent read(final InputStream input) {
		if (input == null) {
			throw new UnexpectedNullException();
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
			 * If exception was thrown in 'try' block, avoid throwing exception
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
	 * Creates an empty file in the default temporary-file directory.
	 *
	 * @param deleteOnExit a flag specifying whether the created file is registered for JVM exit deletion.
	 *
	 * @return a {@code File} reference to an empty file.
	 *
	 * @throws gems.io.RuntimeIOException if file could not be created.
	 */
	public static File createTemporaryFile(final boolean deleteOnExit) {
		try {
			final File result = File.createTempFile("gem", null);
			if (deleteOnExit) {
				result.deleteOnExit();
			}
			return result;
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	/**
	 * Acts exactly as {@code skipSafely(InputStream,long)}, but does not throw any
	 * checked exception. Any {@code IOException} thrown during the operation is
	 * cauhgth and translated to {@code RuntimeIOException}, which is subsequently
	 * thrown.
	 *
	 * @param stream an input stream.
	 * @param bytes number of bytes to be skipped.
	 *
	 * @since CURRENT
	 */
	public static void skipSafelyAndQuietly(final InputStream stream, final long bytes) {
		try {
			skipSafely(stream, bytes);
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	/**
	 * Skips given number of bytes from the given input stream. The difference from
	 * {@code InputStream.read()} is that this implementation ensures that given
	 * number of bytes is really skipped if possible. Another subtle difference is
	 * that this method refuses negative number of bytes by throwing a runtime
	 * excetption. If handling of {@code IOException} does not make sense for you,
	 * you can use {@code skipSafelyAndQuietly(InputStream,long)} instead.
	 *
	 * @param stream an input stream.
	 * @param bytes number of bytes to be skipped.
	 *
	 * @throws IOException if any error occurs during the operation.
	 * @throws UnexpectedNullException if {@code stream} is {@code null}.
	 * @throws IllegalArgumentException if number of bytes to be skipped is negative.
	 * @since CURRENT
	 */
	public static void skipSafely(final InputStream stream, final long bytes) throws IOException {
		if (stream == null) {
			throw new UnexpectedNullException();
		}
		if (bytes < 0L) {
			throw new IllegalArgumentException(String.valueOf(bytes));
		}
		long remaining = bytes;
		while (remaining != 0) {
			final long skipped = stream.skip(remaining);
			if (skipped == 0) {
				throw new EOFException();
			}
			remaining -= bytes;
			assert remaining >= 0; // todo: Checks.assertThat() (?)
		}
	}

	/**
	 * Exception handler wrapping passed {@code IOException} by {@code RuntimeIOException}.
	 */
	static final class IOExceptionWrapper implements ExceptionHandler<IOException> {

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
		@Override public void handle(final IOException e) {
			throw new RuntimeIOException(e);
		}

	}

}
