package gems.logging.formatters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulates a timestamp formatting functionality. The implementation
 * is thread-safe and scalable for usage by more threads simultaneously.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class TimestampFormatter { // ToDo: Maybe this can be generalized to something like ThreadSafeSimpleDateFormat. 

	/**
	 * A thread local map of date formatters.
	 */
	private final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {

		/**
		 * Creates a new date formatter.
		 *
		 * @return a new date formatter.
		 */
		protected DateFormat initialValue() {
			return new SimpleDateFormat(pattern);
		}

	};

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
		return formatters.get().format(new Date(timestamp));
	}

}
