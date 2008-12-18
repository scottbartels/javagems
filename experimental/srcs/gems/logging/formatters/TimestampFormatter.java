package gems.logging.formatters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulates a timestamp formatting functionality.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class TimestampFormatter {

	/**
	 * A formatting pattern.
	 */
	private final String pattern;

	/**
	 * Creates a new timestamp formatter with a given pattern for {@code java.text.SimpleDateFormat}.
	 *
	 * @param pattern a pattern.
	 *
	 * @throws IllegalArgumentException if {@code pattern} is {@code null}.
	 */
	TimestampFormatter(final String pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException();
		}
		this.pattern = pattern;
	}

	/**
	 * Formats a given timestamp.
	 *
	 * @param timestamp a timestamp.
	 *
	 * @return a formatted timestamp.
	 */
	String format(final long timestamp) {
		return new SimpleDateFormat(pattern).format(new Date(timestamp));
	}

}
