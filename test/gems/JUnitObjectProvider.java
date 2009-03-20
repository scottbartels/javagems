package gems;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for the {@code ObjectProvider} null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitObjectProvider {

	/**
	 * Checks whether null-implementation of the interface forbids a {@code null} key.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullImplementationForbidsNullKey() {
		ObjectProvider.NULL_PROVIDER.get(null);
	}

	/**
	 * Checks whether null-implementation of the interface returns an empty {@code Option}.
	 */
	@Test public void nullImplementationReturnsEmptyOption() {
		Assert.assertFalse(ObjectProvider.NULL_PROVIDER.get(new Object()).hasValue());
	}

}
