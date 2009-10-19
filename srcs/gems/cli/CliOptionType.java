package gems.cli;

/**
 * Types of CLI option's value.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public enum CliOptionType {

	/**
	 * None value, the option is a simple switch.
	 */
	NONE,

	/**
	 * Exactly one value, even if typed several times by the user.
	 */
	SINGLE,

	/**
	 * Multiple values.
	 */
	MULTIPLE

}
