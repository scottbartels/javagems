package gems.logging.handlers;

import gems.UnexpectedNullException;
import static gems.logging.LoggingFacility.NULL_FACILITY;
import gems.logging.LoggingHandler;
import static gems.logging.LoggingHandler.NULL_HANDLER;
import gems.logging.LoggingRecord;
import static gems.logging.LoggingSeverity.INFO;
import gems.logging.LoggingTag;
import static gems.logging.handlers.BufferingLoggingHandler.DEFAULT_BUFFER_SIZE;
import static gems.logging.handlers.BufferingLoggingHandler.DEFAULT_FLUSHING_TIMEOUT;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code BufferingLoggingHandler} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitBufferingLoggingHandler {

	/**
	 * A logging record.
	 */
	private static final LoggingRecord RECORD = new LoggingRecord(null, new LoggingTag(NULL_FACILITY, INFO));

	/**
	 * Checks whether a wrapping of a {@code null} logging handler is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullHandlerIsForbidden() {
		new BufferingLoggingHandler(null);
	}

	/**
	 * Checks whether a negative value of the buffer size is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void negativeSizeIsForbidden() {
		new BufferingLoggingHandler(NULL_HANDLER, -1, DEFAULT_FLUSHING_TIMEOUT);
	}

	/**
	 * Checks whether a zero value of the buffer size is forbidden.
	 */
	@Test public void zeroSizeIsAllowed() {
		new BufferingLoggingHandler(NULL_HANDLER, 0, DEFAULT_FLUSHING_TIMEOUT);
	}

	/**
	 * Checks whether a positive value of the buffer size is allowed.
	 */
	@Test public void positiveSizeIsAllowed() {
		Assert.assertNotNull(new BufferingLoggingHandler(NULL_HANDLER, 1, DEFAULT_FLUSHING_TIMEOUT));
	}

	/**
	 * Checks whether a negative value of the timeout is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void negativeFlushingTimeoutIsForbidden() {
		new BufferingLoggingHandler(NULL_HANDLER, DEFAULT_BUFFER_SIZE, -1);
	}

	/**
	 * Checks whether a zero value of the timeout is forbidden.
	 */
	@Test public void zeroFlushingTimeoutIsAllowed() {
		new BufferingLoggingHandler(NULL_HANDLER, DEFAULT_BUFFER_SIZE, 0);
	}

	/**
	 * Checks whether a positive value of the timeout is allowed.
	 */
	@Test public void positiveFlushingTimeoutIsAllowed() {
		new BufferingLoggingHandler(NULL_HANDLER, DEFAULT_BUFFER_SIZE, 1);
	}

	/**
	 * Checks whether a {@code null} logging record is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullLoggingRecordIsForbidden() {
		new BufferingLoggingHandler(NULL_HANDLER).handle(null);
	}

	/**
	 * Checks flushing by size treshold.
	 */
	@Test public void testFlushingBySizeTreshold() {
		final LoggingRecordsCountingLoggingHandler counter = new LoggingRecordsCountingLoggingHandler();
		final LoggingHandler handler = new BufferingLoggingHandler(counter);
		for (int i = 1; i < DEFAULT_BUFFER_SIZE; i++) {
			handler.handle(RECORD);
		}
		Assert.assertEquals(0, counter.getCounter());
		handler.handle(RECORD);
		Assert.assertEquals(DEFAULT_BUFFER_SIZE, counter.getCounter());
	}

	/**
	 * Checks flushing by timeout.
	 *
	 * @throws InterruptedException hopefully never.
	 */
	@Test public void testFlushingByTimeout() throws InterruptedException {
		final LoggingRecordsCountingLoggingHandler counter = new LoggingRecordsCountingLoggingHandler();
		final LoggingHandler handler = new BufferingLoggingHandler(counter, DEFAULT_BUFFER_SIZE, 1);
		handler.handle(RECORD);
		Thread.sleep(900);
		Assert.assertEquals(0, counter.getCounter());
		Thread.sleep(200);
		Assert.assertEquals(1, counter.getCounter());
	}

	/**
	 * A mock logging handler counting logged records.
	 */
	private static final class LoggingRecordsCountingLoggingHandler implements LoggingHandler {

		/**
		 * A counter.
		 */
		private int counter;

		/**
		 * Increases an internal counter for each passed logging record.
		 *
		 * @param record ignored.
		 */
		@Override public void handle(final LoggingRecord record) {
			assert record != null;
			counter++;
		}

		/**
		 * Returns an internal counter value.
		 *
		 * @return an internal counter value.
		 */
		private int getCounter() {
			return counter;
		}

	}

}
