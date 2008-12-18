package gems.logging.handlers;

import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * Buffering wrapper for another handler. It collects logging records in an internal
 * buffer and flushes them into an underlaying handler. Buffered handler runs an internal
 * thread ensuring that a buffer is flushed at least once per specified timeout (a default
 * timeout is 5 seconds). If a number of logging records in an internal buffer reaches
 * a specified size limit (a default size limit is 10 records), a buffer is flushed
 * immediately. <em>Due to necessity to maintain a consistency of an internal buffer,
 * a handling of logging records and flushing operations are synchronized.</em>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class BufferingLoggingHandler implements LoggingHandler {

	/**
	 * A default buffer size.
	 */
	public static final int DEFAULT_BUFFER_SIZE = 10; // If you changed this, fix also a class javadoc.

	/**
	 * A default flushing timeout (in seconds).
	 */
	public static final int DEFAULT_FLUSHING_TIMEOUT = 5; // If you changed this, fix also a class javadoc.

	/**
	 * An internal buffer of logging records.
	 */
	private final List<LoggingRecord> buffer = new LinkedList<LoggingRecord>();

	/**
	 * An internal background flushing thread.
	 */
	private final Flusher flusher;

	/**
	 * An underlaying handler.
	 */
	private final LoggingHandler handler;

	/**
	 * An internal buffer size limit.
	 */
	private final int size;

	/**
	 * Creates a new buffering wrapper for a given handler using default size limit and flushing timeout.
	 *
	 * @param handler a wrapped handler.
	 *
	 * @throws IllegalArgumentException if {@code handler} is {@code null}.
	 */
	public BufferingLoggingHandler(final LoggingHandler handler) {
		this(handler, DEFAULT_BUFFER_SIZE, DEFAULT_FLUSHING_TIMEOUT);
	}

	/**
	 * Creates a new buffering wrapper of a given handler using specified settings.
	 * Setting buffer size to 0 means unlimited buffer size. Setting buffer flushing
	 * timeout to zero means no time-based flushing. 
	 *
	 * @param handler a wrapped handler.
	 * @param size a maximal number of logging records held in a buffer.
	 * @param timeout time (in seconds) between two subsequent buffer flushes.
	 *
	 * @throws IllegalArgumentException if {@code hander} is {@code null}
	 * or if {@code size} or {@code timeout} is negative.
	 */
	public BufferingLoggingHandler(final LoggingHandler handler, final int size, final int timeout) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		if (size < 0) {
			throw new IllegalArgumentException("Illegal size: " + size);
		}
		if (timeout < 0) {
			throw new IllegalArgumentException("Illegal timeout: " + timeout);
		}
		this.handler = handler;
		this.size = size;
		flusher = initFlusher(timeout);
	}

	/**
	 * Creates, starts and returns a background flusher thread.
	 *
	 * @param timeout a delay for a flusher.
	 *
	 * @return a background flusher.
	 */
	private Flusher initFlusher(final int timeout) {
		if (timeout == 0) {
			return null;
		}
		final Flusher result = new Flusher(timeout);
		final Thread thr = new Thread(result);
		thr.setDaemon(true);
		thr.start();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void handle(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		buffer.add(record);
		if (size != 0 && buffer.size() >= size) {
			flush();
		}
	}

	/**
	 * Flushes all logging records stored in an internal buffer to
	 * underlaying handler and clears a buffer. If a buffer is empty,
	 * it simply returns.
	 */
	private synchronized void flush() {
		if (!buffer.isEmpty()) {
			for (final LoggingRecord record : buffer) {
				handler.handle(record);
			}
			buffer.clear();
		}
	}

	/**
	 * Stops an internal flushing thread and flushes an internal buffer.
	 *
	 * @throws Throwable hopefully never.
	 */
	@Override protected void finalize() throws Throwable {
		super.finalize();
		if (flusher != null) {
			flusher.stop();
		}
		flush();
	}

	/**
	 * Backgound task ensuring that a buffer in wrapping
	 * class instance is flushed periodically.
	 */
	private final class Flusher implements Runnable {

		/**
		 * Number of milliseconds per second.
		 */
		private static final long MILLISECONDS_PER_SECOND = 1000L;

		/**
		 * A flushing period.
		 */
		private final long delay;

		/**
		 * A flag indicating that the flusher should stop.
		 */
		private volatile boolean stopped;

		/**
		 * Creates a new flusher with a given flushing period.
		 *
		 * @param delay a flushing period (in seconds).
		 */
		private Flusher(final int delay) {
			assert delay > 0;
			this.delay = delay * MILLISECONDS_PER_SECOND;
		}

		/**
		 * Marks the flusher for stopping.
		 */
		private void stop() {
			stopped = true;
		}

		/**
		 * Flushes a buffer of the wrapping class instance periodically.
		 */
		@Override public void run() {
			try {
				while (!stopped) {
					Thread.sleep(delay);
					if (!stopped) {
						flush();
					}
				}
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}

