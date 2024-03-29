package gems.logging.loggers;

import gems.UnexpectedNullException;
import gems.filtering.Filter;
import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

/**
 * A straightforward implementation of <em>logger</em>. It simply pass
 * a given record to all underalying <em>logging handlers</em>. It operates
 * in a thread of the caller, i.e. caller thread needs to wait until
 * logging operation finishes.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SynchronousLogger extends AbstractFilteringLogger {

	/**
	 * Creates a new synchronous logger.
	 */
	public SynchronousLogger() {
		this(Filter.ALLOW_ALL);
	}

	/**
	 * Creates a new filtering synchronous logger with a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws UnexpectedNullException if {@code filter} is {@code null}.
	 */
	public SynchronousLogger(final Filter<? super LoggingRecord> filter) {
		super(filter);
	}

	/**
	 * Symply pass a given <em>logging record</em> to all underlaying <em>logging handlers</em>.
	 *
	 * @param record a logging record.
	 */
	@Override protected void doLog(final LoggingRecord record) {
		assert record != null;
		for (final LoggingHandler handler : getHandlers()) {
			handler.handle(record);
		}
	}

}
