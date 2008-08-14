package gems.logging;

import gems.filtering.Filter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * An asynchronous <em>logger</em>. Logging, i.e. passing <em>logging records</em>
 * to underlaying handlers is performed asynchronously by a background thread.
 * Logging procedure invoked by the caller is designed to return as soon as possible,
 * so a caller thread is not blocked by logging process even if very slow logging
 * backends are used.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class AsynchronousLogger extends AbstractFilteringLogger {

	/**
	 * An internal asynchronous processor of logging records.
	 */
	private final Executor executor = Executors.newSingleThreadExecutor();

	/**
	 * Creates a new asynchronous logger.
	 */
	@SuppressWarnings({"unchecked"})
	public AsynchronousLogger() {
		this(Filter.ALLOW_ALL);
	}

	/**
	 * Creates a new filtering asynchronous logger with a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	public AsynchronousLogger(final Filter<LoggingRecord> filter) {
		super(filter);
	}

	/**
	 * Submits a given <em>logging record</em> for a background processing and returns.
	 * Processing of a given record is performed by a background thread.
	 *
	 * @param record a logging record.
	 */
	@Override protected void doLog(final LoggingRecord record) {
		assert record != null;
		executor.execute(new Processor(record));
	}

	/**
	 * Processes a logging record asynchronously.
	 */
	private final class Processor implements Runnable {

		/**
		 * A processed record.
		 */
		private final LoggingRecord record;

		/**
		 * Creates a new asynchronous processor for a given record.
		 *
		 * @param record a processed record.
		 */
		private Processor(final LoggingRecord record) {
			this.record = record;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			for (final LoggingHandler handler : getHandlers()) {
				handler.handle(record);
			}
		}

	}

}
