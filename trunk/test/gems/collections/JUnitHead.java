package gems.collections;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * Unit tests for {@code Head} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitHead {

	/**
	 * Checks whether constructor refuses {@code null}.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorRefusesNull() {
		new Head<Serializable>(null);
	}

	/**
	 * Checks whether correct value is returned back.
	 */
	@Test public void valueStoring() {
		final Integer x = 42;
		final Head<Integer> fixture = new Head<Integer>(x);
		final Integer y = fixture.getData();
		Assert.assertNotSame(x, y);
		Assert.assertEquals(x, y);
		Assert.assertTrue(y == 42);
	}

}
