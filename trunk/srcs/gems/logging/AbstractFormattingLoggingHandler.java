package gems.logging;

import gems.filtering.Filter;

/**
 * A skeleton implementation of <em>logger</em> dedicated to format <em>logging records</em>.
 * A formatting is performed by a <em>logging record formatter</em>. This class only holds
 * a formatter instance, but its usage is up to an implementing subclass.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public abstract class AbstractFormattingLoggingHandler extends AbstractFilteringLoggingHandler {

	/**
	 * A logging record formatter.
	 */
	private final LoggingRecordFormatter formatter;

	/**
	 * Creates a new filtering logging handler with given filter and formatter.
	 *
	 * @param formatter a formatter.
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}. If you
	 * don't need any filtering and you want to handle all logging records, use
	 * <em>null implementation</em> {@code gems.filtering.Filter.ALLOW_ALL}.
	 */
	protected AbstractFormattingLoggingHandler(final LoggingRecordFormatter formatter,
											   final Filter<LoggingRecord> filter) {
		super(filter);
		if (formatter == null) {
			throw new IllegalArgumentException();
		}
		this.formatter = formatter;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override protected final void doHandle(final LoggingRecord record) {
		assert record != null;
		handleFormattedRecord(formatter.format(record));
	}

	/**
	 * Handles a given string representation of a logging record. An implementation
	 * of this mehod should be synchronized if necessary or implemented to be re-entrant.
	 *
	 * @param record a handled string reprezentation of a logging record.
	 */
	protected abstract void handleFormattedRecord(String record);

}
