package gems;

/**
 * Represents from-to bounds for operations based on a range of indexed values.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
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
	 * Returns the first index.
	 *
	 * @return the first index.
	 */
	public int begin() {
		return begin;
	}

	/**
	 * Returns the second index.
	 *
	 * @return the second index.
	 */
	public int end() {
		return end;
	}

	/**
	 * Returns a number of 'items' between begin and end. In another words,
	 * it returns exactly the value of expression {@code end - begin}.
	 *
	 * @return a number of 'items' between begin and end.
	 */
	public int range() {
		return end - begin;
	}

}
