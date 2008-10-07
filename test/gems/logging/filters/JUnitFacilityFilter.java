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

	private Filter<LoggingRecord> fixture;

	/**
	 * Creates a new tested object.
	 */
	@Before public void setUp() {
		fixture = new FacilityFilter(LoggingFacility.getFacility("SECURITY"));
	}

	/**
	 * Disposes tested object.
	 */
	@After public void tearDown() {
		fixture = null;
	}

	/**
	 * Checks whether a {@code null} value is forbidden by the constructor.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNull() {
		new FacilityFilter(null);
	}


	/**
	 * Checks whether a {@code null} value is forbidden by the filter.
	 */
	@Test(expected = IllegalArgumentException.class) public void filterForbidsNull() {
		fixture.allows(null);
	}

	/**
	 * Checks filtering capabilities.
	 */
	@Test public void testFiltering() {
		Assert.assertFalse(fixture.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.NULL_FACILITY, LoggingSeverity.INFO))));
		Assert.assertFalse(fixture.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO))));
		Assert.assertTrue(fixture.allows(new LoggingRecord(null, new LoggingTag(LoggingFacility.getFacility("SECURITY"), LoggingSeverity.INFO))));
	}

}
