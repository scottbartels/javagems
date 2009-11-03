package gems;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@code Bounds} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitBounds {

	/**
	 * Checks whether a negative value for a beginning is forbidden.
	 */
	@Test(expected = NumericValueOutOfRangeException.class) public void negativeBeginIsForbidden() {
		new Bounds(-1, 0);
	}

	/**
	 * Checks whether a negative value for an ending is forbidden.
	 */
	@Test(expected = NumericValueOutOfRangeException.class) public void negativeEndIsForbidden() {
		new Bounds(0, -1);
	}

	/**
	 * Checks whether an incosistent bounds are forbidden, variant I.
	 */
	@Test(expected = IllegalArgumentException.class) public void inconsistentBoundsAreForbidden1() {
		new Bounds(1, 0);
	}

	/**
	 * Checks whether an incosistent bounds are forbidden, variant II.
	 */
	@Test(expected = IllegalArgumentException.class) public void inconsistentBoundsAreForbidden2() {
		new Bounds(5, 4);
	}

	/**
	 * Checks whether an incosistent bounds are forbidden, variant III.
	 */
	@Test(expected = IllegalArgumentException.class) public void inconsistentBoundsAreForbidden3() {
		new Bounds(Integer.MAX_VALUE, Integer.MAX_VALUE - 1);
	}

	/**
	 * Checks whether bounds work as expected, variant I, the trivial case.
	 */
	@Test public void checkConsistentBounds1() {
		final Bounds fixture = new Bounds(0, 0);
		Assert.assertEquals(0, fixture.begin());
		Assert.assertEquals(0, fixture.end());
		Assert.assertEquals(0, fixture.range());
	}

	/**
	 * Checks whether bounds work as expected, variant II, the non-trivial zero-range case.
	 */
	@Test public void checkConsistentBounds2() {
		final Bounds fixture = new Bounds(3, 3);
		Assert.assertEquals(3, fixture.begin());
		Assert.assertEquals(3, fixture.end());
		Assert.assertEquals(0, fixture.range());
	}

	/**
	 * Checks whether bounds work as expected, variant III, the non-zero-range case.
	 */
	@Test public void checkConsistentBounds3() {
		final Bounds fixture = new Bounds(3, 5);
		Assert.assertEquals(3, fixture.begin());
		Assert.assertEquals(5, fixture.end());
		Assert.assertEquals(2, fixture.range());
	}

}
