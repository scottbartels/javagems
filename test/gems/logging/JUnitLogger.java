package gems.logging;

import static gems.logging.Logger.NULL_LOGGER;
import static gems.logging.LoggingFacility.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@code Logger} interface null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLogger {

	/**
	 * Checks whether the null-implemnetation returns an empty collection of handlers.
	 */
	@Test public void nullImplementationReturnsEmptyCollectionOfHandlers() {
		Assert.assertTrue(NULL_LOGGER.getHandlers().isEmpty());
	}

	/**
	 * Checks whether the null-implementation returns an immutable collection of handlers.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void nullImplementationRetunrsImmutableCollectionOfHandlers() {
		NULL_LOGGER.getHandlers().add(null);
	}

	/**
	 * Checks whether the null-implementation forbids a {@code null} value as a logging handler.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullImplementationForbidsNullHandler() {
		NULL_LOGGER.addHandler(null);
	}

	/**
	 * Checks whether the null-implementation ignores an added handler.
	 */
	@Test public void nullImplementationIgnoresHandlers() {
		NULL_LOGGER.addHandler(new ActivityIndicatingHandler());
		Assert.assertTrue(NULL_LOGGER.getHandlers().isEmpty());
		NULL_LOGGER.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.INFO)));
	}

	/**
	 * Checks whether the null implemantation forbdis a {@code null} value as a logging record.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullImplementationForbidsNullLoggingRecord() {
		NULL_LOGGER.log(null);
	}

	/**
	 * A logging handler implementation indicating own activity by a test failure.
	 */
	private static final class ActivityIndicatingHandler implements LoggingHandler {

		/**
		 * Causes a test failure if inwoked.
		 *
		 * @param record ignored.
		 */
		public void handle(final LoggingRecord record) {
			Assert.fail();
		}

	}

}
