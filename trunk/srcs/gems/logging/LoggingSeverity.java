package gems.logging;

/**
 * Logging record severities.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public enum LoggingSeverity {

	/**
	 * Very deep insight.
	 */
	TRACE,

	/**
	 * Code flow watching.
	 */
	DEBUG,

	/**
	 * Processing flow watching.
	 */
	VERBOSE,

	/**
	 * Usual and often occured conditions.
	 */
	INFO,

	/**
	 * Usual but rarely occured conditions.
	 */
	NOTICE,

	/**
	 * Unusual but expected conditions.
	 */
	WARNING,

	/**
	 * Unusual, unexpected but recoverable conditions.
	 */
	ALERT,

	/**
	 * Unusual, unexpected and unrecoverable conditions.
	 */
	CRITICAL,

	/**
	 * Application is not able to operate anymore.
	 */
	FATAL

}
