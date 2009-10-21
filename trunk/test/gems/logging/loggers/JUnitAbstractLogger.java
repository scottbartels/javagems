package gems.logging.loggers;

import gems.UnexpectedNullException;
import gems.logging.Logger;
import gems.logging.LoggingRecord;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests of {@code AbstractLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractLogger {

	/**
	 * A tested fixture.
	 */
	private Logger fixture;

	/**
	 * Initializes a new fixture.
	 */
	@Before public void setUp() {
		fixture = new JUnbitAbstractLoggerMock();
	}

	/**
	 * Checks whether a {@code null} value is forbidden for a logging hander.
	 */
	@Test(expected = UnexpectedNullException.class) public void addingNullHandlerIsForbidden() {
		fixture.addHandler(null);
	}

	/**
	 * A mock implementation of {@code AbstractLogger}.
	 */
	private static final class JUnbitAbstractLoggerMock extends AbstractLogger {

		/**
		 * Does nothing.
		 *
		 * @param record ignored.
		 */
		public void log(final LoggingRecord record) {
			// really nothing here
		}

	}

}
