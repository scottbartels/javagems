package gems.logging;

import gems.UnexpectedNullException;
import static gems.logging.Logger.NULL_LOGGER;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@code Logger} interface null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLogger {

	/**
	 * Checks whether the null-implementation forbids a {@code null} value as a logging handler.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullImplementationForbidsNullHandler() {
		NULL_LOGGER.addHandler(null);
	}

	/**
	 * Checks whether the null implemantation forbdis a {@code null} value as a logging record.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullImplementationForbidsNullLoggingRecord() {
		NULL_LOGGER.log(null);
	}

	/**
	 * A logging handler implementation indicating own activity by a test failure.
	 */
	private static final class ActivityIndicatingHandler implements LoggingHandler { // TODO: UNUSED?

		/**
		 * Causes a test failure if invoked.
		 *
		 * @param record ignored.
		 */
		@Override public void handle(final LoggingRecord record) {
			Assert.fail();
		}

	}

}
