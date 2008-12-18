package gems.logging.handlers;

import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

/**
 * An exception catching barrier for a logging hander. All logging records
 * are passed to the wrapped logging handler. Exceptions thrown by the
 * wrapped handler are caught and catching the exception leads to immediate
 * stopping of the handler. All subsequently logged records are simply ignored.
 * This allows to prevent application shutdown caused by poorly written handlers,
 * as well as passing logging records to a handler unable to process them.
 * If a delay is specified during a wrapper creation, logging will be re-enabled
 * after this delay. Zero delay means that ones stopped handler will be never
 * restarted. Because this effectivelly hides errors occured in a wrapped
 * logging handler, it is possible to handle the stopping event by implementing
 * {@code StoppingEventHandler} interface. By default, there is not any stopping
 * event handling.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ExceptionBarrierLoggingHandler implements LoggingHandler {

	/**
	 * Number of milliseconds per second.
	 */
	private static final long MILLISECONDS_PER_SECOND = 1000L;

	/**
	 * Defines an action handling a stopping event,
	 * i.e. a {@code Throwable} object thrown by
	 * a wrapped logging handler.
	 */
	public interface StoppingEventHandler {

		/**
		 * Null-implementation of {@code StoppingEventHandler}
		 * interface. It effectivelly does nothing.
		 */
		StoppingEventHandler NULL_STOPPING_EVENT_HANDLER = new StoppingEventHandler() {

			/**
			 * Does nothing.
			 *
			 * @param t ignored.
			 */
			public void handleStopEvent(final Throwable t) {
				assert t != null;
				// really nothing here
			}

		};

		/**
		 * Allows to react on stopping event occured in underlaying logging handler.
		 *
		 * @param t an exception thrown by underlaying logging handler.
		 */
		void handleStopEvent(Throwable t);

	}

	/**
	 * A wrapped handler.
	 */
	private final LoggingHandler loggingHandler;

	/**
	 * Stopping event handler.
	 */
	private final StoppingEventHandler stoppingEventHandler;

	/**
	 * Restarting delay.
	 */
	private final long delay;

	/**
	 * A stopping flag.
	 */
	private volatile boolean stopped;

	/**
	 * Creates a new exception barrier handler around a given logging handler.
	 * The exception barrier will not be re-opened.
	 *
	 * @param loggingHandler a wrapped handler.
	 * @throws IllegalArgumentException if {@code loggingHandler} is {@code null}. 
	 */
	public ExceptionBarrierLoggingHandler(final LoggingHandler loggingHandler) {
		this(loggingHandler, StoppingEventHandler.NULL_STOPPING_EVENT_HANDLER, 0);
	}

	/**
	 * Creates a new exception barrier handler around a given logging handler.
	 * If a delay is greater then zero, the barrier will be re-opened after this
	 * number of seconds.
	 *
	 * @param loggingHandler a wrapped logging handler.
	 * @param delay a re-opening delay, in seconds.
	 *
	 * @throws IllegalArgumentException if {@code loggingHandler} is {@code null} or if {@code delay} is negative.
	 */
	public ExceptionBarrierLoggingHandler(final LoggingHandler loggingHandler, final int delay) {
		this(loggingHandler, StoppingEventHandler.NULL_STOPPING_EVENT_HANDLER, delay);
	}

	/**
	 * Creates a new exception barrier handler arround a given logging handler. Given
	 * stopping event handler is used for handling an exception thrown by a wrapped
	 * logging handler. The exception barrier will not be re-opened.
	 *
	 * @param loggingHandler a wrapped logging handler.
	 * @param stoppingEventHandler a stopping event handler.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}. 
	 */
	public ExceptionBarrierLoggingHandler(final LoggingHandler loggingHandler,
										  final StoppingEventHandler stoppingEventHandler) {
		this(loggingHandler, stoppingEventHandler, 0);
	}

	/**
	 * Creates a new exception barrier handler around a given logging handler. Given
	 * stopping event handler is used for handling an exception thrown by a wrapped
	 * logging handler. If a delay is greater than zero, the barrier will be re-opened
	 * after this number of seconds. 
	 *
	 * @param loggingHandler a wrapped logging handler.
	 * @param stoppingEventHandler a stopping event handler.
	 * @param delay a re-opening delay, in seconds.
	 *
	 * @throws IllegalArgumentException if any of handlers is {@code null} or if {@code delay} is negative.
	 */
	public ExceptionBarrierLoggingHandler(final LoggingHandler loggingHandler,
										  final StoppingEventHandler stoppingEventHandler,
										  final int delay) {
		if (loggingHandler == null) {
			throw new IllegalArgumentException();
		}
		if (stoppingEventHandler == null) {
			throw new IllegalArgumentException();
		}
		if (delay < 0) {
			throw new IllegalArgumentException();
		}
		this.loggingHandler = loggingHandler;
		this.stoppingEventHandler = stoppingEventHandler;
		this.delay = delay * MILLISECONDS_PER_SECOND;
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
			loggingHandler.handle(record);
		} catch (final Throwable t) {
			stopped = true;
			if (delay > 0) {
				new BarrierRestarterDaemon().start();
			}
			stoppingEventHandler.handleStopEvent(t);
		}
	}

	/**
	 * A barrier restarter. When started, it re-opens
	 * the encosing barrier after specified delay.
	 */
	private final class BarrierRestarterDaemon extends Thread {

		/**
		 * Creates a new barrier restarter daemon.
		 */
		private BarrierRestarterDaemon() {
			assert delay > 0;
			setDaemon(true);
		}

		/**
		 * Sleeps for a specified delay and re-opens
		 * the enclosing barrier afterwards.
		 */
		@Override public void run() {
			try {
				Thread.sleep(delay);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				stopped = false;
			}
		}
		
	}

}
