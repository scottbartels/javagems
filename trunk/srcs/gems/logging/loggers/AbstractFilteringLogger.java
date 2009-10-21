package gems.logging.loggers;

import gems.Checks;
import gems.UnexpectedNullException;
import gems.filtering.Filter;
import gems.logging.LoggingRecord;

/**
 * Adds an ability to filter logging records.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractFilteringLogger extends AbstractLogger {

	/**
	 * A filter of logging records.
	 */
	private final Filter<? super LoggingRecord> filter;

	/**
	 * Creates a new filtering logger with a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws UnexpectedNullException if {@code filter} is {@code null}.
	 */
	protected AbstractFilteringLogger(final Filter<? super LoggingRecord> filter) {
		this.filter = Checks.ensureNotNull(filter);
	}

	/**
	 * {@inheritDoc} Only logging records allowed by an underlaying filter
	 * are passed to handlers. A decision what it really means "log the record"
	 * is delegated to subclasses; only filtering is done here.
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	public final void log(final LoggingRecord record) {
		if (record == null) {
			throw new UnexpectedNullException();
		}
		if (filter.allows(record)) {
			doLog(record);
		}
	}

	/**
	 * Logs a given record. This method is invoked for each logging record,
	 * which was allowed to be logged by the underlaying filter. It is up to
	 * subclass implementation what to do with a given logging record, i.e.
	 * likely how to pass it to handlers.
	 *
	 * @param record a logged record.
	 */
	protected abstract void doLog(LoggingRecord record);

}
