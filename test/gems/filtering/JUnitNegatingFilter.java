package gems.filtering;

import gems.UnexpectedNullException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitNegatingFilter {

	/**
	 * Checks whether the constructor forbids a {@code null} argument.
	 */
	@Test(expected = UnexpectedNullException.class) public void constructorForbidsNull() {
		new NegatingFilter<Object>(null);
	}

	/**
	 * Checks whether the negating filter negates a positive decision of a wrapped filter.
	 */
	@Test public void negatesPositiveDecision() {
		Assert.assertFalse(new NegatingFilter<Object>(Filter.ALLOW_ALL).allows(new Object()));
	}

	/**
	 * Checks whether the negating filter negates a negative decision of a wrapped filter.
	 */
	@Test public void negatesNegativeDecision() {
		Assert.assertTrue(new NegatingFilter<Object>(Filter.DENY_ALL).allows(new Object()));
	}

}
