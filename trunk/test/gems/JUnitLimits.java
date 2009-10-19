package gems;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code Limits} interface null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@SuppressWarnings({"unchecked"}) public final class JUnitLimits {

	/**
	 * Checks whether the null-implementation forbids {@code null} argument.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullImplementationRefusesNull() {
		Limits.ZERO_LIMITS.getLimit(null);
	}

	/**
	 * Checks whether the null-implementation returns zero for any non-null argument.
	 */
	@Test public void nullImplementationReturnsZero() {
		final Limits<Fruits> fixture = Limits.ZERO_LIMITS;
		Assert.assertEquals(0, fixture.getLimit(Fruits.APPLE));
		Assert.assertEquals(0, fixture.getLimit(Fruits.ORANGE));
	}

	/**
	 * A mock enumeration.
	 */
	private enum Fruits {
		ORANGE,
		APPLE
	}

}
