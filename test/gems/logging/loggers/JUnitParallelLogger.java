package gems.logging.loggers;

import gems.UnexpectedNullException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code ParallelLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitParallelLogger extends JUnitLoggersImplementations {

	/**
	 * Creates a tested fixture.
	 */
	@Before public void setUp() {
		setUp(new ParallelLogger());
	}

	/**
	 * Runs all common tests.
	 */
	@Test public void runCommonTests() {
		runAllTests();
	}

	/**
	 * Checks whether a {@code null} filter is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullFilterIsForbidden() {
		new ParallelLogger(null);
	}

	/**
	 * Checks whether a {@code null} logging handler is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullLoggingHandlerIsForbidden() {
		getFixture().addHandler(null);
	}

	/**
	 * Checks whether a {@code null} logging record is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullLoggingRecordIsForbidden() {
		getFixture().log(null);
	}

}
