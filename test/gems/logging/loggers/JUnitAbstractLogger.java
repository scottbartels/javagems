package gems.logging.loggers;

import gems.logging.LoggingRecord;
import gems.logging.Logger;
import gems.logging.LoggingHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;

/**
 * Unit tests of {@code AbstractLogger} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractLogger {

	/**
	 * A tested fixture.
	 */
	private Logger fixture;

	/**
	 * Initializes a new fixture.
	 */
	@Before public void setUp() {
		fixture = new JUnbitAbstractLoggerMock();
	}

	/**
	 * Checks whether a logger returns an immutable collection
	 * of handlers even if no logging handers were added.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void handlersAreImmutableWhenEmpty() {
		final Collection<LoggingHandler> handlers = fixture.getHandlers();
		Assert.assertTrue(handlers.isEmpty());
		handlers.add(LoggingHandler.NULL_IMPLEMENTATION);
	}

	/**
	 * Checks whether a {@code null} value is forbidden for a logging hander.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void addingNullHandlerIsForbidden() {
		fixture.addHandler(null);
	}

	/**
	 * Checks whether a logger returns an immutable collection
	 * of handlers when some logging handlers were added.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void handersAreImmutableWhenNotEmpty() {
		fixture.addHandler(LoggingHandler.NULL_IMPLEMENTATION);
		final Collection<LoggingHandler> handlers = fixture.getHandlers();
		Assert.assertEquals(1, handlers.size());
		handlers.add(LoggingHandler.NULL_IMPLEMENTATION);
	}

	/**
	 * A mock implementation of {@code AbstractLogger}.
	 */
	private static final class JUnbitAbstractLoggerMock extends AbstractLogger {

		/**
		 * Does nothing.
		 *
		 * @param record ignored.
		 */
		public void log(final LoggingRecord record) {
			// really nothing here
		}

	}
	
}
