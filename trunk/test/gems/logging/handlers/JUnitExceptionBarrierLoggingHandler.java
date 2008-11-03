package gems.logging.handlers;

import static gems.logging.LoggingFacility.NULL_FACILITY;
import gems.logging.LoggingHandler;
import static gems.logging.LoggingHandler.NULL_HANDLER;
import gems.logging.LoggingRecord;
import static gems.logging.LoggingSeverity.INFO;
import gems.logging.LoggingTag;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@code ExceptionBarrierLoggingHandler} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitExceptionBarrierLoggingHandler {

	/**
	 * A logging record.
	 */
	private static final LoggingRecord RECORD = new LoggingRecord(null, new LoggingTag(NULL_FACILITY, INFO));

	/**
	 * Checks whether a {@code null} handler is forbidden to be wrapped.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullHandlerIsForbidden() {
		new ExceptionBarrierLoggingHandler(null);
	}

	/**
	 * Checks whether a negative reopening delay is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDelayIsForbidden() {
		new ExceptionBarrierLoggingHandler(NULL_HANDLER, -1);
	}

	/**
	 * Checks whether a {@code null} stopping even handler is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullStoppingEventHandlerIsForbidden() {
		new ExceptionBarrierLoggingHandler(NULL_HANDLER, null);
	}

	/**
	 * Checks whether a {@code null} logging record passing is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullRecordIsForbidden() {
		new ExceptionBarrierLoggingHandler(NULL_HANDLER).handle(null);
	}

	/**
	 * Checks expected work, ie. stopping and reopening.
	 *
	 * @throws InterruptedException hopefully never.
	 */
	@Test public void testStoppingByExceptionAndReopening() throws InterruptedException {
		final LastRecordStoppedEventHander eventHandler = new LastRecordStoppedEventHander();
		final LoggingHandler handler = new ExceptionBarrierLoggingHandler(new MockLoggingHandler(), eventHandler, 1);
		handler.handle(RECORD); // is handled successfully
		Assert.assertFalse(eventHandler.isLastStoppedDetected());
		handler.handle(RECORD); // is passed and causes stopping
		Assert.assertTrue(eventHandler.isLastStoppedDetected());
		Thread.sleep(900); // wait less than reopen delay
		handler.handle(RECORD); // should not passed yet
		Assert.assertFalse(eventHandler.isLastStoppedDetected()); // is false, because not passed
		Thread.sleep(200); // should be reopened now
		handler.handle(RECORD); // should be passed again
		Assert.assertTrue(eventHandler.isLastStoppedDetected()); // is true, because was passed
	}

	/**
	 * Mock logging handler for a testing. It counts passed logging records.
	 * The first one is silently consumed, but each subsequent logging records
	 * leads to exception throwing.
	 */
	private static final class MockLoggingHandler implements LoggingHandler {

		/**
		 * An internal counter of passed logging records.
		 */
		private int counter;

		/**
		 * Silently consumes the first logging record and throws
		 * a runtime exception for each subsequently passed record.
		 *
		 * @param record ignored.
		 */
		public void handle(final LoggingRecord record) {
			if (++counter >= 2) {
				throw new RuntimeException();
			}
		}

	}

	/**
	 * A stopping event handler detecting whether the last logging
	 * record closed an exception barrier logging handler.
	 */
	private static final class LastRecordStoppedEventHander implements ExceptionBarrierLoggingHandler.StoppingEventHandler {

		/**
		 * A flag that the last record closed barrier.
		 */
		private boolean lastStoppedDetected;

		/**
		 * Sets a flag that the last logging record closed barrier.
		 *
		 * @param t ignored.
		 */
		public void handleStopEvent(final Throwable t) {
			lastStoppedDetected = true;
		}

		/**
		 * Returns {@code true} if the last logging record closed the barrier.
		 * Calling this method has a side effect of resetting the flag indicating
		 * the stopping event, so the check can be done only once.
		 *
		 * @return {@code true} if the last logging record closed the barrier, {@code false} otherwise.
		 */
		public boolean isLastStoppedDetected() {
			try {
				return lastStoppedDetected;
			} finally {
				lastStoppedDetected = false;
			}
		}

	}

}
