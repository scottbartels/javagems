package gems.filtering;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilter {

	private static final Object OBJ = new Object();

	@Test(expected = IllegalArgumentException.class) public void allowAllForbidsNull() {
		Filter.ALLOW_ALL.allows(null);
	}

	@Test(expected = IllegalArgumentException.class) public void denyAllForbidsNull() {
		Filter.DENY_ALL.allows(null);
	}

	@Test public void allowAllAllows() {
		Assert.assertTrue(Filter.ALLOW_ALL.allows(OBJ));
	}

	@Test public void denyAllDenies() {
		Assert.assertFalse(Filter.DENY_ALL.allows(OBJ));
	}

}
