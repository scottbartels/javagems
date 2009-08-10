package gems.logging;

/**
 * Defines operation for a logging record handling.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface LoggingHandler {

	/**
	 * A null-implementation of a handler. It simply ignores all logging
	 * records, but a sanity check for {@code null} values is still pefrormed.
	 */
	LoggingHandler NULL_HANDLER = new LoggingHandler() {

		/**
		 * Does nothing.
		 *
		 * @param record ignored, but a sanity check for a {@code null} argument is still performed.
		 *
		 * @throws IllegalArgumentException if {@code record} is {@code null}.
		 */
		@Override public void handle(final LoggingRecord record) {
			if (record == null) {
				throw new IllegalArgumentException();
			}
			// really nothing here
		}

	};

	/**
	 * Handles a given logging record. An implementation of this mehod should
	 * be synchronized if necessary or implemented to be re-entrant.
	 *
	 * @param record a handled record.
	 */
	void handle(LoggingRecord record);

}
