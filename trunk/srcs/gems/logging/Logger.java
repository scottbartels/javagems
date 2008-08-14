package gems.logging;

import java.util.Collections;
import java.util.List;

/**
 * An entry point to the logging subsystem. A <em>logger</em> is able
 * to receive <em>logging records</em> and pass them to underlaying
 * handlers.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface Logger {

	/**
	 * A null-implementation of the interface. It completelly ignores all logging records
	 * and logging handlers, but it still checks method arguments for {@code null} values,
	 * which are forbidden by throwing {@code java.lang.IllegalArgumentException}.
	 * In another words, this null-implementation provides very simple mock object for logger,
	 * ensuring logging subsytem functionality from the language point of view, i.e. compilation,
	 * type safety and interface usage.
	 */
	Logger NULL_IMPLEMENTATION = new Logger() {

		/**
		 * Does nothing.
		 *
		 * @param record ignored, but a sanity check for a {@code null} argument is still performed.
		 *
		 * @throws IllegalArgumentException if {@code record} is {@code null}.
		 */
		public void log(final LoggingRecord record) {
			if (record == null) {
				throw new IllegalArgumentException();
			}
		}

		/**
		 * Does nothing.
		 *
		 * @param handler ignored, but a sanity check for a {@code null} argument is still performed.
		 *
		 * @throws IllegalArgumentException if {@code handler} is {@code null}.
		 */
		public void addHandler(final LoggingHandler handler) {
			if (handler == null) {
				throw new IllegalArgumentException();
			}
		}

		/**
		 * Always returns an immutable empty list.
		 *
		 * @return an immutable empty list.
		 */
		public List<LoggingHandler> getHandlers() {
			return Collections.emptyList();
		}

	};

	/**
	 * Logs a given record.
	 *
	 * @param record a logged record.
	 */
	void log(LoggingRecord record);

	/**
	 * Adds a new logging handler.
	 *
	 * @param handler a new handler.
	 */
	void addHandler(LoggingHandler handler);

	/**
	 * Returns handlers of the logger.
	 *
	 * @return handlers of the logger.
	 */
	List<LoggingHandler> getHandlers();

}
