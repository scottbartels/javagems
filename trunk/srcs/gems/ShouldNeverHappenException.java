package gems;

/**
 * This exception is thrown from a code considered to be unreachable,
 * but a compiler requires a sort of flow execution interruption like
 * a {@code return} statement there.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2009.01
 */
@Experimental public class ShouldNeverHappenException extends RuntimeException {

	/**
	 * Creates a new instance.
	 */
	public ShouldNeverHappenException() {
		super();
	}

	/**
	 * Creates a new instance with a given messages.
	 *
	 * @param message an exception message.
	 */
	public ShouldNeverHappenException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance with a given cause.
	 *
	 * @param cause an exception cause.
	 */
	public ShouldNeverHappenException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance with given message and cause.
	 *
	 * @param message an exception message.
	 * @param cause an exception cause.
	 */
	public ShouldNeverHappenException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
