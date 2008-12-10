package gems;

/**
 * Represents from-to bounds for operations based on a range of indexed values.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2008.12
 */
final public class Bounds {

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
		if (begin < 0) {
			throw new IllegalArgumentException();
		}
		if (end < 0) {
			throw new IllegalArgumentException();
		}
		if (begin > end) {
			throw new IllegalArgumentException();
		}
		this.begin = begin;
		this.end = end;
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
