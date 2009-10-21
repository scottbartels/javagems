package gems.io;

import gems.UnexpectedNullException;
import org.junit.Assert;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

/**
 * Unit tests for {@code IOUtils.close()} method.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"}) public final class JUnitIOUtils_close {

	/**
	 * Checks whether a {@code null} argument is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void closeRefusesNull() {
		IOUtils.close(null);
	}

	/**
	 * Checks whether the {@code close{}} method is called.
	 */
	@Test public void closableIsClosed() {
		final ClosingSuccessTester tester = new ClosingSuccessTester();
		Assert.assertFalse(tester.isClosed());
		IOUtils.close(tester);
		Assert.assertTrue(tester.isClosed());
	}

	/**
	 * Checks whether thrown {@code IOException} is translated to {@code RuntimeIOException}.
	 */
	@Test(expected = RuntimeIOException.class) public void exceptionIsTranslated() {
		IOUtils.close(new ClosingFailureTester());
	}

	/**
	 * Checks whether thrown {@code IOException} is set as a cause of {@code RuntimeIOException}.
	 */
	@Test public void exceptionIsWrapped() {
		final IOException exception = new IOException();
		try {
			IOUtils.close(new ClosingFailureTester(exception));
		} catch (final RuntimeIOException e) {
			Assert.assertSame(exception, e.getCause());
		}
	}

	/**
	 * A fixture for testing a successfull closing of {@code Closeable} object.
	 */
	private static final class ClosingSuccessTester implements Closeable {

		/**
		 * Closed status flag.
		 */
		private volatile boolean closed;

		/**
		 * Marks the object as closed.
		 *
		 * @throws IOException never.
		 */
		@Override public void close() throws IOException {
			closed = true;
		}

		/**
		 * Returns {@code true} if the object is closed, {@code false} otherwise.
		 *
		 * @return {@code true} if the object is closed, {@code false} otherwise.
		 */
		public boolean isClosed() {
			return closed;
		}
	}

	/**
	 * A fixture for testing an unsuccessfull closing of {@code Closeable} object.
	 * It throws {@code IOException} when closed.
	 */
	private static final class ClosingFailureTester implements Closeable {

		/**
		 * The exception to be thrown when closed.
		 */
		private final IOException exception;

		/**
		 * Creates a new fixture.
		 */
		private ClosingFailureTester() {
			this(new IOException());
		}

		/**
		 * Creates a new fixture which will throw a given exception when closed.
		 *
		 * @param e an exception to be thrown when the object is closed.
		 */
		private ClosingFailureTester(final IOException e) {
			assert e != null;
			exception = e;
		}

		/**
		 * Alwasy throws an {@code IOException}.
		 *
		 * @throws IOException always.
		 */
		@Override public void close() throws IOException {
			throw exception;
		}

	}

}
