package gems;

import static gems.ExceptionHandler.NULL_HANDLER;
import org.junit.Test;

/**
 * Unit tests for the null-implementation of {@code ExceptionHandler} interface.
 *
 * @author <a href="mailto:jozef.babjak@gmai.com">Jozef BABJAK</a>
 */
public final class JUnitExceptionHandler {

	/**
	 * Checks whether the null implementation accepts {@code null} argument.
	 */
	@Test public void nullImplementationAcceptsNullArgument() {
		NULL_HANDLER.handle(null);
	}

	/**
	 * Checks whether the null implementation silently discards any given {@code Throwable} object.
	 */
	@Test @SuppressWarnings({"ThrowableInstanceNeverThrown"}) public void nullImplementationDiscardsException() {
		NULL_HANDLER.handle(new Throwable());
		NULL_HANDLER.handle(new Exception());
		NULL_HANDLER.handle(new RuntimeException());
		NULL_HANDLER.handle(new Error());
	}

}
