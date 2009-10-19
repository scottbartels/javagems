package gems.logging;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code LoggingFacility} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLoggingFacility {

	/**
	 * Checks whether a factory forbids a {@code null} name.
	 */
	@Test(expected = IllegalArgumentException.class) public void factoryForbidsNullName() {
		LoggingFacility.getFacility(null);
	}

	/**
	 * Checks whether an empty name leads to the null-facility return.
	 */
	@Test public void emptyNameLeadsToNullFacility() {
		Assert.assertEquals(LoggingFacility.NULL_FACILITY, LoggingFacility.getFacility(""));
	}

	/**
	 * Checks whether the {@code toString()} method returns the facility name.
	 */
	@Test public void toStringReturnsName() {
		Assert.assertEquals("name", LoggingFacility.getFacility("name").toString());
	}

}
