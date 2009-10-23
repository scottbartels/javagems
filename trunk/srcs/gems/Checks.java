package gems;

/**
 * Useful checks.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since CURRENT
 */
@Experimental public final class Checks {

	/**
	 * Just disables an instance creation. This is the utility class.
	 *
	 * @throws UnsupportedOperationException always.
	 */
	private Checks() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks that given object is not {@code null} using assertion and returns the same object.
	 * If assertions are not used, this is identity function. If assertions are used and {@code null}
	 * argument is given, this method does not return normally.
	 *
	 * @param object a checked object.
	 *
	 * @return returns the same object.
	 */
	public static <T> T assertNotNull(final T object) {
		assert object != null;
		return object;
	}

	/**
	 * Returns the same object if it is not {@code null} or throws {@code UnexpectedNullException} otherwise.
	 *
	 * @param object the same object.
	 *
	 * @return the same object.
	 *
	 * @throws UnexpectedNullException if {@code object} is {@code null}.
	 */
	public static <T> T ensureNotNull(final T object) {
		if (object == null) {
			throw new UnexpectedNullException();
		}
		return object;
	}

	public static <T extends Number> T ensurePositive(final T number) {
		if (number.doubleValue() > 0.0) {
			return number;
		}
		throw new NumericValueOutOfRangeException(number);
	}

	public static <T extends Number> T assertPositive(final T number) {
		if (number.doubleValue() > 0.0) {
			return number;
		}
		assert false;
		return number;
	}

	public static <T extends Number> T ensureNegative(final T number) {
		if (number.doubleValue() < 0.0) {
			return number;
		}
		throw new NumericValueOutOfRangeException(number);
	}

	public static <T extends Number> T assertNegative(final T number) {
		if (number.doubleValue() < 0.0) {
			return number;
		}
		assert false;
		return number;
	}

}
