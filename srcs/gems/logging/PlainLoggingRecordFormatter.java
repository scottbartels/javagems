package gems.logging;

import java.util.Date;

/**
 * Formatting logging records to plain text.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@Deprecated public final class PlainLoggingRecordFormatter implements LoggingRecordFormatter {

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	public String format(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}

		final StringBuilder result = new StringBuilder().append(new Date(record.getTimestamp())).append(" {");
		for (final LoggingTag tag : record.getTags()) {
			result.append(tag);
		}
		result.append("} '").append(record.getMessage()).append("'");
		return result.toString();
	}

}
