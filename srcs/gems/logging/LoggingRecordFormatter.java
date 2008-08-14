package gems.logging;

/**
 * Defines an operation for a logging records formatting.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface LoggingRecordFormatter {

	/**
	 * Formats a given logging record.
	 *
	 * @param record a logging record.
	 *
	 * @return a string representation of a given logging record.
	 */
	String format(LoggingRecord record);

}
