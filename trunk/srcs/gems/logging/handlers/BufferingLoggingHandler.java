package gems.logging.handlers;

import gems.logging.LoggingHandler;
import gems.logging.LoggingRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * Buffering wrapper for another handler. It collects logging records in an internal
 * buffer and flushes them into an underlaying handler. Buffered handler runs an internal
 * thread ensuring that a buffer is flushed at least once per specified timeout (a default
 * timeout is 4 seconds). If a number of logging records in an internal buffer reaches
 * a specified size limit (a default size limit is 8 records), a buffer is flushed
 * immediately. <em>Due to necessity to maintain a consistency of an internal buffer,
 * a handling of logging records and flushing operations are synchronized.</em>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class BufferingLoggingHandler implements LoggingHandler {

	/**
	 * A default buffer size.
	 */
	private static final int DEFAULT_SIZE = 8; // If you changed this, fix also a class javadoc.

	/**
	 * A default timeout.
	 */
	private static final int DEFAULT_TIMEOUT = 4; // If you changed this, fix also a class javadoc.

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
	 * Creates a new buffering wrapper for a given handler using default size limit and timeout.
	 *
	 * @param handler a wrapped handler.
	 * @throws IllegalArgumentException if {@code handler} is {@code null}.
	 */
	public BufferingLoggingHandler(final LoggingHandler handler) {
		this(handler, DEFAULT_SIZE, DEFAULT_TIMEOUT);
	}

	/**
	 * Creates a new buffering wrapper for a given handler using a given size limit and a default timeout.
	 *
	 * @param hander a wrapped handler.
	 * @param size a maximal number of logging records held in a buffer.
	 * @throws IllegalArgumentException if {@code hander} is {@code null} or if {@code size} is less than 2.
	 */
	public BufferingLoggingHandler(final LoggingHandler hander, final int size) {
		this(hander, size, DEFAULT_TIMEOUT);
	}

	/**
	 * Creates a new buffering wrapper of a given handler using specified settings.
	 *
	 * @param handler a wrapped handler.
	 * @param size a maximal number of logging records held in a buffer.
	 * @param timeout a maximal time (in seconds) between two subsequent buffer flushes.
	 * @throws IllegalArgumentException if {@code hander} is {@code null} or
	 * if {@code size} is less than 2 or if {@code timeout} is less than 1.
	 */
	public BufferingLoggingHandler(final LoggingHandler handler, final int size, final int timeout) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		if (size < 2) {
			throw new IllegalArgumentException("Illegal size: " + size);
		}
		if (timeout < 1) {
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
	 * @return a background flusher.
	 */
	private Flusher initFlusher(final int timeout) {
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
		if (buffer.size() >= size) {
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
		flusher.stop();
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
		public Flusher(final int delay) {
			if (delay <= 0) {
				throw new IllegalArgumentException();
			}
			this.delay = delay * MILLISECONDS_PER_SECOND;
		}

		/**
		 * Marks the flusher for stopping.
		 */
		public void stop() {
			stopped = true;
		}

		/**
		 * Flushes a buffer of the wrapping class instance periodically.
		 */
		@Override public void run() {
			try {
				while (!stopped) {
					flush();
					Thread.sleep(delay);
				}
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}

