package gems.logging.formatters;

import gems.UnexpectedNullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit tests for the {@code TimestampFormatter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitTimestampFormatter {

	/**
	 * Number of concurrent threads for thread-safeness test.
	 */
	private static final int CHECKERS = 4;

	/**
	 * A date formatting pattern.
	 */
	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * A tested fixture.
	 */
	private TimestampFormatter fixture;

	/**
	 * Creates a new tested fixture.
	 */
	@Before public void setUp() {
		fixture = new TimestampFormatter(PATTERN);
	}

	/**
	 * Checks whether a {@code null} pattern is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullPatternIsForbidden() {
		new TimestampFormatter(null);
	}

	/**
	 * Checks whether a formatter does not return {@code null} value.
	 */
	@Test public void returnsNotNull() {
		Assert.assertNotNull(fixture.format(System.currentTimeMillis()));
	}

	/**
	 * Checks whether the implementation is thread-safe. This is not an ultimate proof
	 * of correctness, but it seems to catch thread-unsafe behavior very reliably.
	 */
	@Test public void checkThreadSafeness() {
		final Checker[] checkers = new Checker[CHECKERS];
		for (int i = 0; i < CHECKERS; i++) {
			/*
			 * XXX: Dividing is an important trick here ensuring that each
			 * checker has different data for processing by date formatter.
			 * When all checkers were using the same data, race-conditions
			 * were not observed for thread-unsafe implementations; likely
			 * all checkers were overwritting shared data structures with
			 * same data. 
			 */
			checkers[i] = new Checker(System.currentTimeMillis() / (i + 1));
		}
		for (final Checker checker : checkers) {
			checker.start();
		}
		for (final Checker checker : checkers) {
			try {
				checker.join();
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
			Assert.assertTrue(checker.passed());
		}
	}

	/**
	 * An asynchronous executor of thread-safeness test. Repeteadly
	 * uses shared fixture for date formatting and compares the result
	 * with the result acquired from own local formatter.
	 */
	private final class Checker extends Thread {

		/**
		 * Number of test repetitions.
		 */
		private static final int REPETITIONS = 100000;

		/**
		 * Formatted timestamp.
		 */
		private final long time;

		/**
		 * The expected result acquired from own local formatter.
		 */
		private final String expectedResult;

		/**
		 * A flag indicating whether test was passed successfully.
		 */
		private volatile boolean passed;

		/**
		 * Creates a new asynchronous checker.
		 *
		 * @param time formatted timestamp.
		 */
		private Checker(final long time) {
			this.time = time;
			this.expectedResult = new SimpleDateFormat(PATTERN).format(new Date(time));
		}


		@Override public void run() {
			for (int i = 0; i < REPETITIONS; i++) {
				if (!expectedResult.equals(fixture.format(time))) {
					return;
				}
			}
			passed = true;
		}

		/**
		 * Returns {@code true} if test was successful, {@code false} if race-condition was detected.
		 *
		 * @return {@code true} if test was successful, {@code false} if race-condition was detected.
		 */
		private boolean passed() {
			return passed;
		}

	}

}
