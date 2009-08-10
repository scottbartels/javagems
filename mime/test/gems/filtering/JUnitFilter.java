package gems.filtering;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@code Filter} interface and its null-implementations.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilter {

	/**
	 * A not-null object.
	 */
	private static final Object OBJECT = new Object();

	/**
	 * Checks whether the {@code ALLOW_ALL} null-implementaiton forbids a {@code null} argument.
	 */
	@Test(expected = IllegalArgumentException.class) public void allowAllForbidsNull() {
		Filter.ALLOW_ALL.allows(null);
	}

	/**
	 * Checks whether the {@code DENY_ALL} null-implementaiton forbids a {@code null} argument.
	 */
	@Test(expected = IllegalArgumentException.class) public void denyAllForbidsNull() {
		Filter.DENY_ALL.allows(null);
	}

	/**
	 * Checks whether the {@coe ALLOW_ALL} null-implementation allows non-null object.
	 */
	@Test public void allowAllAllows() {
		Assert.assertTrue(Filter.ALLOW_ALL.allows(OBJECT));
	}

	/**
	 * Checks whether the {@coe DENY_ALL} null-implementation denies non-null object.
	 */
	@Test public void denyAllDenies() {
		Assert.assertFalse(Filter.DENY_ALL.allows(OBJECT));
	}

}
