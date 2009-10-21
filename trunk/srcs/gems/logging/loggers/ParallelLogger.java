package gems.logging.loggers;

import gems.Checks;
import gems.UnexpectedNullException;
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
	 * @throws UnexpectedNullException if {@code filter} is {@code null}.
	 */
	public ParallelLogger(final Filter<? super LoggingRecord> filter) {
		logger = new AsynchronousLogger(Checks.ensureNotNull(filter));
	}


	/**
	 * {@inheritDoc} All functionality is delegaded to an internal asynchronous logger.
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	@Override public void log(final LoggingRecord record) {
		logger.log(Checks.ensureNotNull(record));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UnexpectedNullException if {@code logger} is {@code null}.
	 */
	@Override public void addHandler(final LoggingHandler handler) {
		final Logger sublogger = new AsynchronousLogger();
		sublogger.addHandler(Checks.ensureNotNull(handler));
		logger.addHandler(new LoggerDelegatingLoggingHander(sublogger));
	}

}
