package gems.logging.handlers;

import gems.filtering.Filter;
import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

/**
 * A skeleton implementation of a <em>logging handlers</em>. It adds an ability
 * to filter handled <em>logging records</em>. Only <em>logging records</em>
 * allowed for handling by an underlaying filter are handled. A real handling
 * of <em>logging records</em> is still delegated to subclasses.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractFilteringLoggingHandler implements LoggingHandler {

	/**
	 * A filter of logging records.
	 */
	private final Filter<? super LoggingRecord> filter;

	/**
	 * Creates a new filtering logging handler with a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	protected AbstractFilteringLoggingHandler(final Filter<? super LoggingRecord> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		this.filter = filter;
	}

	/**
	 * {@inheritDoc} Only <em>logging records</em> allowed by the underlaying filter
	 * are handled.  A decision what it really means "handle the record"
	 * is delegated to subclasses; only filtering is done here.
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	public final void handle(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		if (filter.allows(record)) {
			doHandle(record);
		}
	}

	/**
	 * Handles a given record. This method is invoked for each logging record,
	 * which was allowed to be handled by the underlaying filter. It is up to
	 * subclass implementation what to do with a given logging record, i.e.
	 * how to handle it. An implementation of this mehod should
	 * be synchronized if necessary or implemented to be re-entrant.
	 *
	 * @param record a logged record.
	 */
	protected abstract void doHandle(LoggingRecord record);

}
