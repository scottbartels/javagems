package gems.logging;

import static gems.logging.LoggingFacility.NULL_FACILITY;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@Deprecated public final class LoggingEntryPoint {

	/**
	 * A wrapped logger.
	 */
	private final Logger logger;

	/**
	 * A default severity.
	 */
	private final LoggingSeverity defaultSeverity;

	/**
	 * Creates a new logging entry point around a given logger.
	 *
	 * @param logger a wrapped logger.
	 *
	 * @throws IllegalArgumentException if {@code logger} is {@code null}.
	 */
	public LoggingEntryPoint(final Logger logger) {
		this(logger, LoggingSeverity.NOTICE);
	}

	/**
	 * Creates a new logging entry point around a given logger using a given severity as a default severity.
	 *
	 * @param logger a wrapped logger.
	 * @param severity a default severity.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public LoggingEntryPoint(final Logger logger, final LoggingSeverity severity) {
		if (logger == null) {
			throw new IllegalArgumentException();
		}
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		this.logger = logger;
		defaultSeverity = severity;
	}

	/**
	 * Returns a wrapped logger. This method never returns {@code null}.
	 *
	 * @return a wrapped logger.
	 */
	public Logger getLogger() {
		return logger;
	}

	public void log(final Object object) {
		logger.log(new LoggingRecord(object, new LoggingTag(NULL_FACILITY, defaultSeverity)));
	}

	public void log(final Object object, final LoggingSeverity severity) {
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(NULL_FACILITY, severity)));
	}

	public void log(final Object object, final LoggingFacility facility) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(facility, defaultSeverity)));
	}

	public void log(final Object object, final LoggingFacility facility, final LoggingSeverity severity) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(facility, severity)));
	}

}
