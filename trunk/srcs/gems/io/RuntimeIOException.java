package gems.io;

/**
 * Signals that an I/O exception of some sort has occurred. The meaning of this exception
 * is exactly the same as the meaning of {@code java.io.IOException}, but using this exception
 * do not force exception handling in a client code.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a> 
 */
public class RuntimeIOException extends RuntimeException {

	/**
	 * Creates a new instance.
	 */
	public RuntimeIOException() {
		super();
	}

	/**
	 * Creates a new instance with a given messages.
	 *
	 * @param message an exception message.
	 */
	public RuntimeIOException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance with a given cause.
	 *
	 * @param cause an exception cause.
	 */
	public RuntimeIOException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance with given message and cause.
	 *
	 * @param message an exception message.
	 * @param cause an exception cause.
	 */
	public RuntimeIOException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
