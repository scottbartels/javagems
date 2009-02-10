package gems.logging.loggers;

import gems.logging.Logger;
import static gems.logging.LoggingFacility.NULL_FACILITY;
import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import org.junit.Assert;

/**
 * Unit tests for various loggers implementations.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class JUnitLoggersImplementations {

	/**
	 * How long time to wait for asynchronous operations finishing.
	 */
	private static final long DELAY = 100L;

	/**
	 * A tested fixture.
	 */
	private Logger fixture;

	/**
	 * A counter of logged messages.
	 */
	private int counter;

	/**
	 * Sets a tested fixture up.
	 *
	 * @param fixture a fixture.
	 *
	 * @throws IllegalArgumentException if {@code fixture} is {@code null}.
	 */
	protected final void setUp(final Logger fixture) {
		if (fixture == null) {
			throw new IllegalArgumentException();
		}
		this.fixture = fixture;
		this.fixture. addHandler(new JUnitLoggingHandlerMock());
		counter = 0;
	}

	/**
	 * Runs all tests.
	 */
	protected void runAllTests() {
		recordIsPassedToHandlers();
	}

	/**
	 * Checks whether a logged record is passed to handlers.
	 */
	private void recordIsPassedToHandlers() {
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.INFO)));
		try {
			Thread.sleep(DELAY);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals(1, counter);
	}

	/**
	 * Makes a tested fixture accessible in subclasses.
	 *
	 * @return a tested fixture.
	 */
	protected Logger getFixture() {
		return fixture;
	}

	/**
	 * A mock logging hander. Instead of a real handling, it counts a passed logging records.
	 */
	private final class JUnitLoggingHandlerMock implements LoggingHandler {

		/**
		 * Increases a counter in the enclosing class by one for each logged record.
		 *
		 * @param record a logging record, ignored.
		 */
		@Override public void handle(final LoggingRecord record) {
			counter++;
		}
		
	}

}
