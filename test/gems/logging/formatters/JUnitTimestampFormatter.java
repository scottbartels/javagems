package gems.logging.formatters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Unit tests for the {@code TimestampFormatter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitTimestampFormatter {

	private static final int CHECKERS = 4;
	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Checks whether a {@code null} pattern is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullPatternIsForbidden() {
		new TimestampFormatter(null);
	}

	/**
	 * Checks whether a formatter does not return {@code null} value.
	 */
	@Test public void returnsNotNull() {
		Assert.assertNotNull(new TimestampFormatter(PATTERN).format(System.currentTimeMillis()));
	}

	@Test public void checkThreadSafeness() {
		final Checker[] checkers = new Checker[CHECKERS];

		final TimestampFormatter fixture = new TimestampFormatter(PATTERN);

		for (int i = 0; i < CHECKERS; i++) {
			checkers[i] = new Checker(fixture, i + 1);
			checkers[i].start();
		}

		for (final Checker checker : checkers) {
			try {
				checker.join();
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
			Assert.assertTrue(checker.getResult());
		}
	}

	private static final class Checker extends Thread {

		private final TimestampFormatter fixture;

		private final long time;

		private final String expectedResult;

		private volatile boolean result;

		private Checker(final TimestampFormatter fixture, final int div) {
			this.fixture = fixture;
			this.time = System.currentTimeMillis() / div;
			this.expectedResult = new SimpleDateFormat(PATTERN).format(new Date(time));
		}


		@Override public void run() {
			for (int i = 0; i < 100000; i++) {
				if (!expectedResult.equals(fixture.format(time))) {
					return;
				}
			}
			result = true;
		}

		private boolean getResult() {
			return result;
		}
	}

}
