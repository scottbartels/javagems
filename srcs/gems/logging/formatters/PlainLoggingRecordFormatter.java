package gems.logging.formatters;

import gems.logging.*;

/**
 * Formatting logging records to plain text using all available information from logging record.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class PlainLoggingRecordFormatter implements LoggingRecordFormatter {

    /**
     * A timestamp formatter.
     */
    private static final TimestampFormatter TIMESTAMP_FORMATTER = new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS");

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
		final StringBuilder result = new StringBuilder().append(TIMESTAMP_FORMATTER.format(record.getTimestamp()));
        // thread info
        final ThreadInfo thread = record.getThreadInfo();
        result.append(" <").append(thread.getName()).append("(").append(thread.getId()).append(")>");
        // tags
		result.append(" {");
		for (final LoggingTag tag : record.getTags()) {
			result.append("{");
			final LoggingFacility facility = tag.getFacility();
			if (!facility.equals(LoggingFacility.NULL_FACILITY)) {
				result.append(facility).append(":");
			}
			result.append(tag.getSeverity()).append("}");
		}
		result.append("}");
		// creator info
		result.append("\t[");
		final CreatorInfo creator = record.getCreatorInfo();
		result.append(creator.getClassName()).append(".").append(creator.getMethodName()).append("(").append(creator.getLineNumber()).append(")]");
		// message
		result.append("\t'").append(record.getMessage()).append("'");
		return result.toString();
	}

}
