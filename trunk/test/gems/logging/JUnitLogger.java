package gems.logging;

import static gems.logging.Logger.NULL_IMPLEMENTATION;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@code Logger} interface null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLogger {

	/**
	 * Checks whether the null-implemnetation returns an empty collection of handlers.
	 */
	@Test public void nullImplementationReturnsEmptyCollectionOfHandlers() {
		Assert.assertTrue(Logger.NULL_IMPLEMENTATION.getHandlers().isEmpty());
	}

	/**
	 * Checks whether the null-implementation returns an immutable collection of handlers.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void nullImplementationRetunrsImmutableCollectionOfHandlers() {
		Logger.NULL_IMPLEMENTATION.getHandlers().add(null);
	}

	/**
	 * Checks whether the null-implementation forbids a {@code null} value as a logging handler.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullImplementationForbidsNullHandler() {
		Logger.NULL_IMPLEMENTATION.addHandler(null);
	}

	/**
	 * Checks whether the null-implementation ignores an added handler.
	 */
	@Test public void nullImplementationIgnoresHandlers() {
		Logger.NULL_IMPLEMENTATION.addHandler(new LoggingHandler() {
			public void handle(final LoggingRecord record) {
				Assert.fail();
			}
		});
		Assert.assertTrue(Logger.NULL_IMPLEMENTATION.getHandlers().isEmpty());
		new LoggingEntryPoint(NULL_IMPLEMENTATION).log("This should fail if passed to that handler.");
	}

	/**
	 * Checks whether the null implemantation forbdis a {@code null} value as a logging record.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullImplementationForbidsNullLoggingRecord() {
		Logger.NULL_IMPLEMENTATION.log(null);
	}

}
