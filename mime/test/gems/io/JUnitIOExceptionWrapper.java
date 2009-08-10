package gems.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit tests for {@code IOExceptionWrapper}.
 *
 * @author <a href="mailto:jozef.babjak@gmai.com">Jozef BABJAK</a>
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"}) public final class JUnitIOExceptionWrapper {

	/**
	 * A tested fixture.
	 */
	private IOUtils.IOExceptionWrapper fixture;

	/**
	 * Creates a new tested fixture.
	 */
	@Before public void setUp() {
		fixture = new IOUtils.IOExceptionWrapper();
	}

	/**
	 * Checks whether a {@code null} value is accepted.
	 */
	@Test(expected = RuntimeIOException.class) public void acceptsNull() {
		fixture.handle(null);
	}

	/**
	 * Checks whether a {@code RuntimeIOException} is thrown when an {@code IOException} is passed.
	 */
	@Test(expected = RuntimeIOException.class) public void wrapsException() {
		fixture.handle(new IOException());
	}

	/**
	 * Checks whether a wrapped exception is set as a cause of thrown exception.
	 */
	@Test public void setsCause() {
		final IOException cause = new IOException();
		try {
			fixture.handle(cause);
		} catch (final RuntimeIOException e) {
			Assert.assertSame(cause, e.getCause());
		}
	}

}
