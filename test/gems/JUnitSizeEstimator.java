package gems;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for the {@code SizeEstimator} null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitSizeEstimator {

	/**
	 * Checks whether null-implementation of the interface forbids a {@code null} object.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullImplementationForbidsNullObject() {
		SizeEstimator.ZERO_ESTIMATOR.estimate(null);
	}

	/**
	 * Checks whether null-implementation of the interface returns zero.
	 */
	@Test public void nullImplementationReturnsZero() {
		Assert.assertEquals(0L, SizeEstimator.ZERO_ESTIMATOR.estimate(new Object()));
		Assert.assertEquals(0L, SizeEstimator.ZERO_ESTIMATOR.estimate(new String[] {"a", "b", "c"}));
	}

}
