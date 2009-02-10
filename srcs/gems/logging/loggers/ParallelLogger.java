package gems.logging.loggers;

import gems.filtering.Filter;
import gems.logging.Logger;
import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;
import gems.logging.handlers.LoggerDelegatingLoggingHander;

/**
 * Logging handler processing logging records in a separate thread
 * for each logging handler.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ParallelLogger implements Logger {

	/**
	 * An internal logger.
	 */
	private final Logger logger;

	/**
	 * Creates a new parallel logger.
	 */
	public ParallelLogger() {
		this(Filter.ALLOW_ALL);
	}

	/**
	 * Creates a new parallel logger using a given filter. A filtering
	 * functionality is delegated to an internal asynchronous logger.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	public ParallelLogger(final Filter<? super LoggingRecord> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		logger = new AsynchronousLogger(filter);
	}


	/**
	 * {@inheritDoc} All functionality is delegaded to an internal asynchronous logger.
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	@Override public void log(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		logger.log(record);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code logger} is {@code null}.
	 */
	@Override public void addHandler(final LoggingHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		final Logger sublogger = new AsynchronousLogger();
		sublogger.addHandler(handler);
		logger.addHandler(new LoggerDelegatingLoggingHander(sublogger));
	}

}
