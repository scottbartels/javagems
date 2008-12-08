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


	@Test(expected = IllegalArgumentException.class) public void nullCollectionIsForbidden() {
		new ComposedComparator(null);
	}

	@Test(expected = IllegalArgumentException.class) public void nullCollectionElementIsForbidden() {
		final Collection c = new HashSet();
		c.add(null);
		new ComposedComparator(c);
	}

	@Test public void emptyCollectionIsAccepted() {
		final ComposedComparator fixture = new ComposedComparator(Collections.EMPTY_SET);
		Assert.assertEquals(0, fixture.compare(new Object(), new Object()));
	}
	
}
