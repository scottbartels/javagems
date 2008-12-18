package gems;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Collections;

/**
 * Unit tests for the {@code ComposedComparator} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@SuppressWarnings({"unchecked"}) public final class JUnitComposedComparator {


	/**
	 * Checks whether a {@code null} value as a collection of comparators is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullCollectionIsForbidden() {
		new ComposedComparator(null);
	}

	/**
	 * Checks whether a {@code null} value as one of wrapped comparators is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullCollectionElementIsForbidden() {
		final Collection c = new HashSet();
		c.add(null);
		new ComposedComparator(c);
	}

	/**
	 * Checks whether an empty collection of comparators is accepted.
	 */
	@Test public void emptyCollectionIsAccepted() {
		final ComposedComparator fixture = new ComposedComparator(Collections.EMPTY_SET);
		Assert.assertEquals(0, fixture.compare(new Object(), new Object()));
	}

	/**
	 * Checks whether the first compared object cannot be {@code null}.
	 */
	@Test(expected = IllegalArgumentException.class) public void fistOperandCannotBeNull() {
		new ComposedComparator(Collections.EMPTY_SET).compare(null, new Object());
	}

	/**
	 * Checks whether the second compared object cannot be {@code null}. 
	 */
	@Test(expected = IllegalArgumentException.class) public void secondOperandCannotBeNull() {
		new ComposedComparator(Collections.EMPTY_SET).compare(new Object(), null);
	}

}
