package gems.logging;

/**
 * An exception catching barrier for a logging hander. All logging records
 * are passed to the wrapped logging handler. Exceptions thrown by the
 * wrapped handler are caught and catching the exception leads to immediate
 * stopping of the handler. All subsequently logged records are simply ignored.
 * This allows to prevent application shutdown caused by poorly written handlers,
 * as well as passing logging records to a handler unable to process them.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ExceptionBarrierLoggingHandler implements LoggingHandler {

	/**
	 * A wrapped handler.
	 */
	private final LoggingHandler handler;

	/**
	 * A stopping flag.
	 */
	private volatile boolean stopped;

	/**
	 * Creates a new exception barrier handler around a given handler.
	 *
	 * @param handler a wrapped handler.
	 *
	 * @throws IllegalArgumentException if {@code handler} is {@code null}.
	 */
	public ExceptionBarrierLoggingHandler(final LoggingHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		this.handler = handler;
	}

	/**
	 * Passes a given logging record to the wrapped logging handler, if the
	 * handler was not stopped yet by a catching an exception from the wrapped
	 * handler. If the handler was already stopped, it simply returns.
	 *
	 * @param record a logging record.
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	public void handle(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		if (stopped) {
			return;
		}
		try {
			handler.handle(record);
		} catch (final Throwable t) {
			stopped = true;
		}
	}

}
