package gems.logging;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * A <em>logging record</em> is a unit of logged information.
 * It encapsulates a logged information as well as any related
 * metadata. A logging record is immutable after creation. 
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingRecord {

	/**
	 * A string used for a {@code null} value representation.
	 */
	private static final String NULL_AS_STRING = "null";

	/**
	 * A name of this package.
	 */
	private static final String CURRENT_PACKAGE = LoggingRecord.class.getPackage().getName();

	/**
	 * A logging record creation timestamp.
	 */
	private final long timestamp = System.currentTimeMillis();

	/**
	 * A thread info object.
	 */
	private final ThreadInfo threadInfo = new ThreadInfo();

	/**
	 * A caller creating the logging record.
	 */
	private final StackTraceElement creator = findCaller();

	/**
	 * A logging record message.
	 */
	private final String message;

	/**
	 * Tags associated with the logging record.
	 */
	private final LoggingTags tags;

	/**
	 * Creates a new logging record holding a given object as a message and
	 * with logging tags metadata. At least one logging tag must be given. 
	 *
	 * @param object an object.
	 * @param tags optional metadata.
	 */
	public LoggingRecord(final Object object, final LoggingTag... tags) {
		message = objectToMessage(object);
		this.tags = new LoggingTags(tags);
	}

	/**
	 * Returns a logging record creation timestamp.
	 *
	 * @return a logging record creation timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns a message of the logging record. This method never returns {@code null}.
	 *
	 * @return a mesasge of the logging record.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Retunrs a thread info object associated with the logging record. This method never returns {@code null}.
	 *
	 * @return a thread info object associated with the logging record.
	 */
	public ThreadInfo getThreadInfo() {
		return threadInfo;
	}

	/**
	 * Returns a record creator identification. Be ready for {@code null} return value.
	 * Please note that this is only an estimation and it can be misleading.
	 *
	 * @return a record creator identification.
	 */
	public StackTraceElement getCreator() {
		return creator;
	}

	/**
	 * Returns tags associated with the logging record. This method never returns {@code null}.
	 *
	 * @return tags associated with the logging record.
	 */
	public LoggingTags getTags() {
		return tags;
	}

	/**
	 * Converts a given object to a string. This method never returns {@code null}.
	 *
	 * @param object a converted object.
	 *
	 * @return a string representation of a given object or {@code "null"} if {@code object} is {@code null}.
	 */
	private static String objectToMessage(final Object object) {
		if (object == null) {
			return NULL_AS_STRING;
		}
		if (object instanceof Throwable) {
			return throwableToMessage((Throwable) object);
		}
		final String result = object.toString();
		return result == null ? NULL_AS_STRING : result;
	}

	/**
	 * Converts a given {@code Throwable} object into a string. This method never returns {@code null}.
	 *
	 * @param throwable a converted object.
	 *
	 * @return a string representation of a given object.
	 */
	private static String throwableToMessage(final Throwable throwable) {
		assert throwable != null;
		final OutputStream stream = new ByteArrayOutputStream();
		final PrintWriter writer = new PrintWriter(stream, true);
		throwable.printStackTrace(writer);
		writer.close();
		return stream.toString();
	}

	/**
	 * Tries to find a caller producing the logging record by stack trace analysis.
	 *
	 * @return a caller producing the logging record.
	 */
	private static StackTraceElement findCaller() {
		for (final StackTraceElement element : (new Throwable()).getStackTrace()) {
			if (!element.getClassName().startsWith(CURRENT_PACKAGE)) {
				return element;
			}
		}
		return null;
	}

}
