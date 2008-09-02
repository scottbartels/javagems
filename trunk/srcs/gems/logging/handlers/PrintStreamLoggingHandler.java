package gems.logging.handlers;

import gems.filtering.Filter;
import gems.logging.LoggingRecord;
import gems.logging.LoggingRecordFormatter;
import gems.logging.formatters.SimpleLoggingRecordFormatter;

import java.io.PrintStream;

/**
 * A <em>logging handler</em> writing formatted <em>logging records</em> into a print stream.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class PrintStreamLoggingHandler extends AbstractFormattingLoggingHandler {

	/**
	 * An underlaying print stream.
	 */
	private final PrintStream stream;

	/**
	 * Creates a new logging handler for a given stream.
	 *
	 * @param stream a stream.
	 *
	 * @throws IllegalArgumentException if {@code stream} is {@code null}.
	 */
	@SuppressWarnings({"unchecked"})
	public PrintStreamLoggingHandler(final PrintStream stream) {
		this(stream, Filter.ALLOW_ALL);
	}

	/**
	 * Creates a new filtering logging handler for a given print stream.
	 *
	 * @param stream a print stream.
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public PrintStreamLoggingHandler(final PrintStream stream, final Filter<LoggingRecord> filter) {
		this(stream, new SimpleLoggingRecordFormatter(), filter);
	}

	/**
	 * Creates a new logging handler with a given formatter and print stream.
	 *
	 * @param stream a print stream.
	 * @param formatter a formatter.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	@SuppressWarnings({"unchecked"})
	public PrintStreamLoggingHandler(final PrintStream stream, final LoggingRecordFormatter formatter) {
		this(stream, formatter, Filter.ALLOW_ALL);
	}

	/**
	 * Creates a new filtering logging handler with given filter, formatter and print stream.
	 *
	 * @param stream a print stream.
	 * @param formatter a formatter.
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public PrintStreamLoggingHandler(final PrintStream stream,
									 final LoggingRecordFormatter formatter,
									 final Filter<LoggingRecord> filter) {
		super(formatter, filter);
		if (stream == null) {
			throw new IllegalArgumentException();
		}
		this.stream = stream;
	}

	/**
	 * Writes a given string to the underlaying print stream, adding a newline character at the end.
	 *
	 * @param record a string to be written.
	 */
	@Override protected void handleFormattedRecord(final String record) {
		assert record != null;
		stream.println(record);
	}

	/**
	 * Closes a print stream.
	 *
	 * @throws Throwable hopefully never.
	 */
	@Override protected void finalize() throws Throwable {
		super.finalize();
		stream.close();
	}

}
