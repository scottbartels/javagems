package gems.logging.filters;

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
 * Unit tests for {@code SeverityFilter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitSeverityFilter {

	/**
	 * Security facility.
	 */
	private static final LoggingFacility SECURITY = LoggingFacility.getFacility("SECURITY");

	/**
	 * A tested fixture.
	 */
	private Filter<LoggingRecord> filter;

	/**
	 * Creates a new tested filter.
	 */
	@Before public void setUp() {
		filter = new SeverityFilter(LoggingSeverity.INFO);
	}

	/**
	 * Checks whether a {@code null} value is forbidden by a construcor.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNull() {
		new SeverityFilter(null);
	}

	/**
	 * Checks whether a {@code null} value is forbidden by a filter.
	 */
	@Test(expected = IllegalArgumentException.class) public void filterForbidsNull() {
		filter.allows(null);
	}

	/**
	 * Checks filtering capabilities.
	 */
	@Test public void testFiltering() {
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.FATAL))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.CRITICAL))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.ALERT))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.WARNING))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.NOTICE))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(NULL_FACILITY, LoggingSeverity.INFO))));
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
	}

}
