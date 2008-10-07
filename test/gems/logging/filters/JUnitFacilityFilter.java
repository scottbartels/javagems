package gems.logging.filters;

import gems.filtering.Filter;
import gems.logging.LoggingFacility;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests of {@code FacilityFilter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFacilityFilter {

	/**
	 * A tested fixture.
	 */
	private Filter<LoggingRecord> filter;

	/**
	 * Creates a new tested filter.
	 */
	@Before public void setUp() {
		filter = new FacilityFilter(LoggingFacility.getFacility("SECURITY"));
	}

	/**
	 * Disposes a fixture.
	 */
	@After public void tearDown() {
		filter = null;
	}

	/**
	 * Checks whether a {@code null} value is forbidden by a constructor.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNull() {
		new FacilityFilter(null);
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
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.NULL_FACILITY, LoggingSeverity.INFO))));
		Assert.assertFalse(filter.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO))));
		Assert.assertTrue(filter.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.getFacility("SECURITY"), LoggingSeverity.INFO))));
	}

}
