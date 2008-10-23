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
		Thread.yield();
		Assert.assertEquals(1, counter);
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
		public void handle(final LoggingRecord record) {
			counter++;
		}
		
	}

}
