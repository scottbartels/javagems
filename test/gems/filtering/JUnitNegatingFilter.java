package gems.filtering;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitNegatingFilter {

    @Test(expected = IllegalArgumentException.class) public void refusesNull() {
		new NegatingFilter<Object>(null);
	}

	@Test public void negates() {
		Assert.assertFalse(new NegatingFilter<Object>(Filter.ALLOW_ALL).allows(new Object()));
		Assert.assertTrue(new NegatingFilter<Object>(Filter.DENY_ALL).allows(new Object()));
	}
	
}
