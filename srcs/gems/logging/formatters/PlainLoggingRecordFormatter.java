package gems.logging.formatters;

import gems.logging.CreatorInfo;
import gems.logging.LoggingRecord;
import gems.logging.LoggingRecordFormatter;
import gems.logging.LoggingTag;

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
		// timestamp
		final StringBuilder result = new StringBuilder().append(new Date(record.getTimestamp()));
		// tags
		result.append(" {");
		for (final LoggingTag tag : record.getTags()) {
			result.append(tag);
		}
		result.append("}");
		// creator and thread info
		result.append(" [");
		final CreatorInfo creator = record.getCreatorInfo();
		// TODO: TU SOM SKONCIL
		// message
		result.append(" '").append(record.getMessage()).append("'");
		return result.toString();
	}

}
