package gems.logging.formatters;

import gems.UnexpectedNullException;
import gems.logging.LoggingRecord;
import gems.logging.LoggingRecordFormatter;

/**
 * Formatting logging records to plain text using only basic information from logging record.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SimpleLoggingRecordFormatter implements LoggingRecordFormatter {

	/**
	 * A timestamp formatter.
	 */
	private static final TimestampFormatter TS_FORMATTER = new TimestampFormatter("yyyy-MM-dd HH:mm:ss");

	/**
	 * {@inheritDoc}
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	@Override public String format(final LoggingRecord record) {
		if (record == null) {
			throw new UnexpectedNullException();
		}
		// timestamp
		final StringBuilder result = new StringBuilder().append(TS_FORMATTER.format(record.getTimestamp()));
		// maximal severity
		result.append(" {").append(record.getTags().getMaximalSeverity()).append("}");
		// message
		result.append("\t'").append(record.getMessage()).append("'");
		return result.toString();
	}

}
