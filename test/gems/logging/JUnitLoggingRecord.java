package gems.logging;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code LoggingRecord} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLoggingRecord {

	/**
	 * Checks a logging record timestamp presence and initialization.
	 *
	 * @throws InterruptedException hopefully never.
	 */
	@Test public void timestampCheck() throws InterruptedException {
		final long before = System.currentTimeMillis();
		Thread.sleep(100);
		final long ts = createRecord(null).getTimestamp();
		Thread.sleep(100);
		final long after = System.currentTimeMillis();
		Assert.assertTrue((ts - before) > 0); // Checks that 'ts' is after 'before' ...
		Assert.assertTrue((ts - before) < 200); // ... but still quite close.
		Assert.assertTrue((after - ts) > 0); // Checks that 'ts' is before 'after' ...
		Assert.assertTrue((after - ts) < 200); // ... but still quite close.
	}

	/**
	 * Checks whether a creator info object is initialized.
	 */
	@Test public void creatorInfoObjectIsNotNull() {
		Assert.assertNotNull(createRecord(null).getCreatorInfo());
	}

	/**
	 * Checks whether a thread identity object is initialized.
	 */
	@Test public void threadIdentityObjectIsNotNull() {
		Assert.assertNotNull(createRecord(null).getThreadIdentity());
	}

	/**
	 * Checks a {@code null} value representation.
	 */
	@Test public void testNullMessageRepresentation() {
		Assert.assertEquals("null", createRecord(null).getMessage());
	}

	/**
	 * Checks a message if {@code toString()} method of logged object returns a {@code null} value.
	 */
	@Test public void testMessageIfToStringReturnsNull() {
		Assert.assertEquals("null", createRecord(new Object() {
			@Override public String toString() {
				return null;
			}
		}).getMessage());
	}

	/**
	 * Checks whether a message equals to {@code toString()} result for an arbitrary object.
	 */
	@Test public void testMessageEqualsToToStringResult() {
		Assert.assertEquals("hello", createRecord(new Object() {
			@Override public String toString() {
				return "Hello, world".substring(0, 5).toLowerCase();
			}
		}).getMessage());
	}

	/**
	 * Slightly checks a message for an exception.
	 */
	@Test public void testExceptionMessage() {
		final String msg = createRecord(new RuntimeException(new IllegalArgumentException("hello"))).getMessage();
		Assert.assertFalse(msg.indexOf("RuntimeException") == -1);
		Assert.assertFalse(msg.indexOf("Caused by:") == -1);
		Assert.assertFalse(msg.indexOf("IllegalArgumentException") == -1);
		Assert.assertFalse(msg.indexOf("hello") == -1);
	}

	/**
	 * Creates a new logging record for a given logged object.
	 *
	 * @param obj a logged object.
	 *
	 * @return a logging record for a given logged object.
	 */
	private static LoggingRecord createRecord(final Object obj) {
		return new LoggingRecord(obj, new LoggingTag(LoggingFacility.NULL_FACILITY, LoggingSeverity.INFO));
	}

}
