package gems.logging.formatters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code TimestampFormatter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitTimestampFormatter {

	/**
	 * Checks whether a {@code null} pattern is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullPatternIsForbidden() {
		new TimestampFormatter(null);
	}

	/**
	 * Checks whether a formatter does not return {@code null} value.
	 */
	@Test public void returnsNotNull() {
		Assert.assertNotNull(new TimestampFormatter("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
	}

}
