package gems;

import static gems.ObjectProvider.NULL_PROVIDER;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code ObjectProvider} null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitObjectProvider {

	/**
	 * Checks whether null-implementation of the interface forbids a {@code null} key.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullImplementationForbidsNullKey() {
		NULL_PROVIDER.provide(null);
	}

	/**
	 * Checks whether null-implementation of the interface returns an empty {@code Option}.
	 */
	@Test public void nullImplementationReturnsEmptyOption() {
		Assert.assertFalse(NULL_PROVIDER.provide(new Option<Object>(null)).hasValue());
	}

	/**
	 * Checks whether independ objects are returned by each call.
	 */
	@Test(expected = IllegalStateException.class) public void nullImplementationProvidesNewObjects() {
		NULL_PROVIDER.provide(new Option<Object>(null)).hasValue();
		NULL_PROVIDER.provide(new Option<Object>(null)).getValue();
	}

}
