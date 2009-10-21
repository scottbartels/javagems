package gems;

/**
 * This exception is thrown when an unexpected {@code null} value is detected by
 * business logic, leaving pure {@code NullPointerException} solely for JVM internal
 * usage. However, this exception is a subclass of {@code NullPointerException}
 * because of general contract of {@code NullPointerException}: <em>Applications
 * should throw instances of this class</em> - i.e. NPE - <em>to indicate other illegal uses
 * of the {@code null} object.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2009.10
 */
@Experimental public class UnexpectedNullException extends NullPointerException {

	/**
	 * Creates a new exception.
	 */
	public UnexpectedNullException() {
		super();
	}

	/**
	 * Creates a new exception with a given message.
	 *
	 * @param s an exception message.
	 */
	public UnexpectedNullException(final String s) {
		super(s);
	}

}
