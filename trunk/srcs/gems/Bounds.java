package gems;

/**
 * Represents from-to bounds for operations based on a range of indexed values.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2009.01
 */
public final class Bounds {

	/**
	 * The first index.
	 */
	private final int begin;

	/**
	 * The second index.
	 */
	private final int end;

	/**
	 * Creates a new bounds object holding given indexes.
	 *
	 * @param begin a "from" index.
	 * @param end a "to" index.
	 *
	 * @throws IllegalArgumentException if any of indexes is negative or if {@code begin} is greather than {@code end}.
	 */
	public Bounds(final int begin, final int end) {
		this.begin = Checks.ensureNonNegative(begin);
		this.end = Checks.ensureNonNegative(end);
        if (begin > end) {
            throw new IllegalArgumentException(begin + " > " + end);
        }
	}

	/**
	 * Returns the first index. This method always returns a non-negative integer.
	 *
	 * @return the first index.
	 */
	public int begin() {
		return begin;
	}

	/**
	 * Returns the second index. This method always returns a non-negative integer.
	 *
	 * @return the second index.
	 */
	public int end() {
		return end;
	}

	/**
	 * Returns a number of 'items' between begin and end. In another words,
	 * it returns exactly the value of expression {@code end - begin}.
	 * This method always returns a non-negative integer.
	 *
	 * @return a number of 'items' between begin and end.
	 */
	public int range() {
		return end - begin;
	}

}
