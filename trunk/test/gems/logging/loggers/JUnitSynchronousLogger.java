package gems.logging.loggers;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code SynchronousLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitSynchronousLogger extends JUnitLoggersImplementations {

	/**
	 * Creates a new fixture.
	 */
	@Before public void setUp() {
		setUp(new SynchronousLogger());
	}

	/**
	 * Runs all tests.
	 */
	@Test public void test() {
		runAllTests();	
	}

}
