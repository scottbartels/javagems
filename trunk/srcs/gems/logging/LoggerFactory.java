package gems.logging;

import gems.logging.loggers.AsynchronousLogger;
import gems.logging.loggers.ParallelLogger;
import gems.logging.loggers.SynchronousLogger;

/**
 * Defines a factory for loggers and provides basic implementations.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2009.10
 */
public interface LoggerFactory {

	/**
	 * A null implementation of the interface.
	 */
	LoggerFactory NULL_LOGGER_FACTORY = new LoggerFactory() {

		/**
		 * Always returns {@code Logger.NULL_LOGGER}.
		 *
		 * @return always {@code Logger.NULL_LOGGER}.
		 */
		public Logger get() {
			return Logger.NULL_LOGGER;
		}

	};

	/**
	 * A default implementation of the interface. It tries to select
	 * a proper implementation of {@code Logger} for the hardware.
	 */
	LoggerFactory DEFAULT_LOGGER_FACTORY = new LoggerFactory() {

		/**
		 * Returns a certain implementation of the {@code Logger} depending on current
		 * number of CPUs. If there is more than 4 CPUs available, {@code ParallelLogger()}
		 * instance is returned. If there is 2-4 CPUs available, {@code AsynchronousLogger}
		 * is returned. Otherwise {@code SynchronousLogger()} is returned. This method never
		 * returns {@code null}.
		 *
		 * @return a certain implementation of {@code Logger}.
		 */
		public Logger get() {
			final int cpus = Runtime.getRuntime().availableProcessors();
			if (cpus > 4) {
				return new ParallelLogger();
			}
			if (cpus > 1) {
				return new AsynchronousLogger();
			}
			return new SynchronousLogger();
		}

	};

	/**
	 * Returns a new instance of {@code Logger}.
	 *
	 * @return a new instance of {@code Logger}.
	 */
	Logger get();

}
