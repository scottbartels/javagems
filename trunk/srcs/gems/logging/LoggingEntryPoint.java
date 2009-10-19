package gems.logging;

import static gems.logging.LoggingFacility.NULL_FACILITY;

/**
 * A convenient entry point to the logging subsystem. It encapsulates
 * a logger, but provides a facade for creation of logging records and
 * logging tags. It provides simplified ways how to create logging
 * records, especially when logging facility or logging severity is
 * not required.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingEntryPoint {

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

	/**
	 * Logs a given object using an empty facility and a default severity.
	 *
	 * @param object a logged object.
	 */
	public void log(final Object object) {
		logger.log(new LoggingRecord(object, new LoggingTag(NULL_FACILITY, defaultSeverity)));
	}

	/**
	 * Logs a given object using an empty facility and a given severity.
	 *
	 * @param object a logged object.
	 * @param severity a severity.
	 *
	 * @throws IllegalArgumentException if {@code severity} is {@code null}.
	 */
	public void log(final Object object, final LoggingSeverity severity) {
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(NULL_FACILITY, severity)));
	}

	/**
	 * Logs a given object using a given facility and a default severity.
	 *
	 * @param object a logged object.
	 * @param facility a logging facility.
	 *
	 * @throws IllegalArgumentException if {@code facility} is {@code null}.
	 */
	public void log(final Object object, final LoggingFacility facility) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(facility, defaultSeverity)));
	}

	/**
	 * Logs a given object with given facility and severity.
	 *
	 * @param object a logged object.
	 * @param facility a logging facility.
	 * @param severity a logging severity.
	 *
	 * @throws IllegalArgumentException if any of {@code facility} or {@code severity} is {@code null}.
	 */
	public void log(final Object object, final LoggingFacility facility, final LoggingSeverity severity) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		logger.log(new LoggingRecord(object, new LoggingTag(facility, severity)));
	}

	/**
	 * Logs a given object with additional logging tags metadata.
	 *
	 * @param object a logged object.
	 * @param tags logging tags metadata.
	 */
	public void log(final Object object, final LoggingTag... tags) {
		logger.log(new LoggingRecord(object, tags));
	}

}
