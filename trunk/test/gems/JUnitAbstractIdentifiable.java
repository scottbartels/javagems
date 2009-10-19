package gems;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code AbstractIdentifiable} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractIdentifiable {

	/**
	 * Checks whether a {@code null} ID is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullIdIsForbidden() {
		new A(null);
	}

	/**
	 * Checks whether the proper ID is returned.
	 */
	@Test public void testReturningId() {
		Assert.assertEquals("X", new A("XYZ".substring(0, 1)).getId());
	}

	/**
	 * Checks whether object is not equal to a {@code null} value.
	 */
	@Test public void notEqualToNull() {
		Assert.assertFalse(new A("X").equals(null));
	}

	/**
	 * Checks whether objects of different implementations are considered to be different regardles of their IDs.
	 */
	@Test public void notEqualIfImplementationsAreDifferent() {
		final A a = new A("X");
		final B b = new B("X");
		Assert.assertFalse(a.equals(b));
		Assert.assertFalse(b.equals(a));
	}

	/**
	 * Checks whether an object is equal to itself.
	 */
	@Test public void equalsToItself() {
		final A a = new A("X");
		Assert.assertTrue(a.equals(a));
	}

	/**
	 * Checks whether two different objects with logically different ids are considered to be different.
	 */
	@Test public void differsForDiffrentIds() {
		final A x = new A("x");
		final A y = new A("y");
		Assert.assertFalse(x.equals(y));
		Assert.assertFalse(y.equals(x));
	}

	/**
	 * Checks whether two different objects with logically same ids are considered to be equal.
	 */
	@Test public void equalsForEqualIds() {
		final String id1 = "xx".substring(0, 1);
		final String id2 = "xx".substring(1, 2);
		Assert.assertEquals(id1, id2); // this is only a precondition
		Assert.assertNotSame(id1, id2); // this is only a precondition
		final A a1 = new A(id1);
		final A a2 = new A(id2);
		Assert.assertEquals(a1, a2);
		Assert.assertEquals(a1.hashCode(), a2.hashCode());
	}

	/**
	 * A mock.
	 */
	private static class A extends AbstractIdentifiable<String> {

		private A(final String id) {
			super(id);
		}

	}

	/**
	 * An another mock.
	 */
	private static final class B extends A {

		private B(final String id) {
			super(id);
		}

	}

}
