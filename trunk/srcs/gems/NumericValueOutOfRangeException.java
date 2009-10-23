package gems;

public class NumericValueOutOfRangeException extends RuntimeException {

	public NumericValueOutOfRangeException() {
		super();
	}

	public NumericValueOutOfRangeException(final Number value) {
		this(value == null ? null : value.toString());
	}

	public NumericValueOutOfRangeException(final String message) {
		super(message);
	}

}
