package gems.logging.loggers;

import gems.filtering.Filter;
import gems.logging.Logger;
import static gems.logging.LoggingFacility.NULL_FACILITY;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import gems.logging.filters.SeverityFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code AbstractFilteringLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractFilteringLogger {

	/**
	 * A counter of logged records.
	 */
	private int counter;

	/**
	 * A tested fixture.
	 */
	private Logger fixture;

	@Before public void setUp() {
		fixture = new JUnitAbsractFilteringLoggerMock(new SeverityFilter(LoggingSeverity.NOTICE));
		counter = 0;
	}

	/**
	 * Checks whether a {@code null} filter is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullFilterIsForbidden() {
		new JUnitAbsractFilteringLoggerMock(null);
	}

	/**
	 * Checks whether a {@code null} logging record is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullLoggingRecordIsForbidden() {
		fixture.log(null);
	}

	/**
	 * Checks whether a filtering works as expected.
	 */
	@Test public void filteringWorks() {
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.TRACE)));
		Assert.assertEquals(0, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.DEBUG)));
		Assert.assertEquals(0, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.VERBOSE)));
		Assert.assertEquals(0, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.INFO)));
		Assert.assertEquals(0, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.NOTICE)));
		Assert.assertEquals(1, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.WARNING)));
		Assert.assertEquals(2, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.ALERT)));
		Assert.assertEquals(3, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.CRITICAL)));
		Assert.assertEquals(4, counter);
		fixture.log(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.FATAL)));
		Assert.assertEquals(5, counter);
	}

	/**
	 * A mock implementation of {@code AbstractFilteringLogger}. Instead of real logging,
	 * it counts logged records.
	 */
	private final class JUnitAbsractFilteringLoggerMock extends AbstractFilteringLogger {

		/**
		 * Creates a new mock logger with a given filter.
		 *
		 * @param filter a filter.
		 */
		protected JUnitAbsractFilteringLoggerMock(final Filter<LoggingRecord> filter) {
			super(filter);
		}

		/**
		 * Increases a counter in the enclosing class by one for each invocation.
		 *
		 * @param record a logged record; ignored.
		 */
		protected void doLog(final LoggingRecord record) {
			counter++;
		}

	}

}
