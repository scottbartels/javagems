package gems;

/**
 * This exception is thrown when a numeric value checked by a business logic is not within expected boundaries.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a> 
 * @since 2009.11
 */
@Experimental public class NumericValueOutOfRangeException extends RuntimeException {

    /**
     * Creates a new instance of the exception.
     */
	public NumericValueOutOfRangeException() {
		super();
	}

    /**
     * Creates a new instance of the exception.
     *
     * @param value a numeric value causing troubles.
     */
	public NumericValueOutOfRangeException(final Number value) {
		this(value == null ? null : value.toString());
	}

    /**
     * Creates a new instance of the exception.
     *
     * @param message an exception message.
     */
	public NumericValueOutOfRangeException(final String message) {
		super(message);
	}

}
