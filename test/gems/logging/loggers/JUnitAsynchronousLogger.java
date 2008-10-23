package gems.logging.loggers;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code AsynchronousLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAsynchronousLogger extends JUnitLoggersImplementations {

	/**
	 * Creates a tested fixture.
	 */
	@Before public void setUp() {
		setUp(new AsynchronousLogger());
	}

	/**
	 * Runs all tests.
	 */
	@Test public void test() {
		runAllTests();
	}

}
