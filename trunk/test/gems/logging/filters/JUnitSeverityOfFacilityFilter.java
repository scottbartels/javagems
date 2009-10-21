package gems.logging.filters;

import gems.UnexpectedNullException;
import gems.filtering.Filter;
import gems.logging.LoggingFacility;
import static gems.logging.LoggingFacility.NULL_FACILITY;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code SeverityOfFacilityFilter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitSeverityOfFacilityFilter {

	/**
	 * Security facility.
	 */
	private static final LoggingFacility SECURITY = LoggingFacility.getFacility("SECURITY");

	/**
	 * Performance facility.
	 */
	private static final LoggingFacility PERFORMANCE = LoggingFacility.getFacility("PERFORMANCE");

	/**
	 * A tested fixture.
	 */
	private Filter<LoggingRecord> filter;

	/**
	 * Creates a new tested filter.
	 */
	@Before public void setUp() {
		filter = new SeverityOfFacilityFilter(LoggingSeverity.INFO, SECURITY);
	}

	/**
	 * Checks whether a {@code null} severity is forbidden by a constructor.
	 */
	@Test(expected = UnexpectedNullException.class) public void constructorForbidsNullSeverity() {
		new SeverityOfFacilityFilter(null, NULL_FACILITY);
	}

	/**
	 * Checks whether a {@code null} facility is forbidden by a constructor.
	 */
	@Test(expected = UnexpectedNullException.class) public void constructorForbidsNullFacility() {
		new SeverityOfFacilityFilter(LoggingSeverity.INFO, null);
	}

	/**
	 * Checks whether a {@code null} value is forbidden by a filter.
	 */
	@Test(expected = UnexpectedNullException.class) public void filterForbidsNull() {
		filter.allows(null);
	}

	/**
	 * Checks filtering capabilities.
	 */
	@Test public void checkFiltering() {
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.FATAL))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.CRITICAL))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.ALERT))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.WARNING))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.NOTICE))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.INFO))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.VERBOSE))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.DEBUG))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.TRACE))));

		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.FATAL))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.CRITICAL))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.ALERT))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.WARNING))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.NOTICE))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.INFO))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.VERBOSE))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.DEBUG))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(SECURITY, LoggingSeverity.TRACE))));

		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.FATAL))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.CRITICAL))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.ALERT))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.WARNING))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.NOTICE))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.INFO))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.VERBOSE))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.DEBUG))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(PERFORMANCE, LoggingSeverity.TRACE))));

	}


}
