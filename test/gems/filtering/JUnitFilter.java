package gems.filtering;

import org.junit.Test;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilter {

	@Test(expected = IllegalArgumentException.class) public void allowAllForbidsNull() {
		Filter.ALLOW_ALL.allows(null);
	}

	@Test(expected = IllegalArgumentException.class) public void denyAllForbidsNull() {
		Filter.DENY_ALL.allows(null);
	}

}
