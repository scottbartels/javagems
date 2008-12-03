package gems.caching;

/**
 * Cache limits, ie maximal number of cached items and maximal size of cached items. Limits are always non-negative.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheLimits {

	public static final CacheLimits UNLIMITED = new CacheLimits(Integer.MAX_VALUE, Long.MAX_VALUE);

	/**
	 * Maximal number of cached items.
	 */
	private final int count;

	/**
	 * Maximal size of cached items.
	 */
	private final long size;

	/**
	 * Creates a new cache limits holder with given limits.
	 *
	 * @param count maximal number of cached items.
	 * @param size maximal size of cached items.
	 *
	 * @throws IllegalArgumentException if any of arguments is negative.
	 */
	public CacheLimits(final int count, final long size) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}
		if (size < 0L) {
			throw new IllegalArgumentException();
		}
		this.count = count;
		this.size = size;
	}

	/**
	 * Copying constructor. Creates a new cache limits holder,
	 * getting limits from a given object, but dividing them
	 * by a given fragmentation.
	 *
	 * @param limits copied cache limits.
	 * @param fragmentation a required fragmentation.
	 *
	 * @throws IllegalArgumentException if {@code limits} is {@code null} of if {@code fragmentation} is less than 1.
	 */
	CacheLimits(final CacheLimits limits, final int fragmentation) {
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (fragmentation < 1) {
			throw new IllegalArgumentException();
		}
		this.count = limits.count / fragmentation;
		this.size = limits.size / fragmentation;
	}

	/**
	 * Returns maximal number of cached items. This
	 * method always returns a non-negative number.
	 *
	 * @return maximal number of cached items.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns maximal size of cached items. This
	 * method always returns a non-negative number.
	 *
	 * @return maximal size of cached items.
	 */
	public long getSize() {
		return size;
	}

}
