package gems.filtering;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitNegatingFilter {

	private final Filter<Object> filter = new NegatingFilter<Object>(Filter.ALLOW_ALL);

	@Test(expected = IllegalArgumentException.class) public void refusesNull() {
		new NegatingFilter<Object>(null);
	}

	@Test public void negates() {
		Assert.assertFalse(filter.allows(new Object()));
	}
	
}
