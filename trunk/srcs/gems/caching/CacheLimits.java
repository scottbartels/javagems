package gems.caching;

/**
 * Cache limits, ie maximal number of cached items and maximal size of cached items. Limits are always non-negative.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheLimits {

	/**
	 * Maximal number of cached items.
	 */
	private int count = Integer.MAX_VALUE;

	/**
	 * Maximal size of cached items.
	 */
	private long size = Long.MAX_VALUE;

	/**
	 * Creates a new cache limits holder.
	 */
	public CacheLimits() {
		// really nothing here
	}

	/**
	 * Copying constructor. Creates a new cache limits holder, getting
	 * limits from a given {@code limits} object, but dividing them by
	 * a given fragmentation.
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
	 * Sets maximal number of cached items.
	 *
	 * @param count new maximal limit of cached items.
	 *
	 * @throws IllegalArgumentException if {@code count} is negative.
	 */
	public void setCount(final int count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}
		this.count = count;
	}

	/**
	 * Returns maximal number of cached items. This method always returns a non-negative
	 * number. If the limit was not set, {@code Integer.MAX_VALUE} is returned.
	 *
	 * @return maximal number of cached items.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets maximal size of cached items.
	 *
	 * @param size new maximal size of cached items.
	 *
	 * @throws IllegalArgumentException if {@code size} is nagative.
	 */
	public void setSize(final long size) {
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
	}

	/**
	 * Returns maximal size of cached items. This method always returns a non-negative
	 * number. If the limit was not set, {@code Long.MAX_VALUE} is returned.
	 *
	 * @return maximal size of cached items.
	 */
	public long getSize() {
		return size;
	}

}
