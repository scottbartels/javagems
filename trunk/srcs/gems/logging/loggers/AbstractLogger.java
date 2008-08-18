package gems.logging.loggers;

import gems.filtering.Filter;
import gems.logging.Logger;
import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A skeleton implementation of <em>logger</em>. It ensures the common functionality
 * of adding and providing underlaying <em>logging handlers</em>. A real handling of
 * <em>logging records</em> is still delegated to subclasses. It also adds an ability
 * to filter logging records. 
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractLogger implements Logger {

	/**
	 * Handlers. Unmodifiable defense copy is hold here, so it is safe to provide it outside.
	 */
	private volatile List<LoggingHandler> handlers = Collections.emptyList();

	/**
	 * A filter of logging records.
	 */
	private final Filter<LoggingRecord> filter;

	/**
	 * Creates a new filtering logger with a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	protected AbstractLogger(final Filter<LoggingRecord> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		this.filter = filter;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code handler} is {@code null}.
	 */
	public final synchronized void addHandler(final LoggingHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		final List<LoggingHandler> modifiable = new LinkedList<LoggingHandler>(handlers);
		modifiable.add(handler);
		handlers = Collections.unmodifiableList(modifiable);
	}

	/**
	 * {@inheritDoc} Returned list is unmodifiable. This method never returns {@code null};
	 * at least an empty list returned, even if no handlers were added to the logger.
	 *
	 * @return handlers of the logger.
	 */
	public final List<LoggingHandler> getHandlers() {
		return handlers;
	}

	/**
	 * {@inheritDoc} Only logging records allowed by an underlaying filter
	 * are passed to handlers. A decision what it really means "log the record"
	 * is delegated to subclasses; only filtering is done here.
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	public final void log(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
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
