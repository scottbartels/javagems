package gems.logging;

/**
 * Logging record severities.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public enum LoggingSeverity {

	/**
	 * Application is not able to operate anymore.
	 */
	FATAL,

	/**
	 * Unusual, unexpected and unrecoverable conditions.
	 */
	CRITICAL,

	/**
	 * Unusual, unexpected but recoverable conditions.
	 */
	ALERT,

	/**
	 * Unusual but expected conditions.
	 */
	WARNING,

	/**
	 * Usual but rarely occured conditions.
	 */
	NOTICE,

	/**
	 * Usual and often occured conditions.
	 */
	INFO,

	/**
	 * Processing flow watching.
	 */
	VERBOSE,

	/**
	 * Code flow watching.
	 */
	DEBUG,

	/**
	 * Very deep insight.
	 */
	TRACE

}
