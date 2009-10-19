package gems.logging;

import gems.logging.loggers.AsynchronousLogger;
import gems.logging.loggers.ParallelLogger;
import gems.logging.loggers.SynchronousLogger;


public interface LoggerFactory {

	LoggerFactory NULL_LOGGER_FACTORY = new LoggerFactory() {

		public Logger get() {
			return Logger.NULL_LOGGER;
		}

	};

	LoggerFactory DEFAULT_LOGGER_FACTORY = new LoggerFactory() {

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

	Logger get();

}
