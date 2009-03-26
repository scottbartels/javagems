package gems.caching;

import gems.Limits;

/**
 * Implements static cache limits. It means that each limit is a static number.
 * However, limits can be changed during a lifecycle and the change is reflected.
 */
public final class StaticCacheLimits implements Limits<CacheLimit> { // todo: store attributes via enum map.

	/**
	 * Maximal number of items allowed to be stored in a cache.
	 */
	private volatile int items;

	/**
	 * Size limit of items allowed to be stored in a cache.
	 */
	private volatile long size;

	/**
	 * Sets a new limit for maximal number of items allowed to be stored in a cache.
	 *
	 * @param items a new limit.
	 *
	 * @throws IllegalArgumentException if {@code items} is negative.
	 */
	public void setItems(final int items) {
		if (items < 0) {
			throw new IllegalArgumentException(String.valueOf(items));
		}
		this.items = items;
	}

	/**
	 * Sets a new size limit of items allowed to be stored in a cache.
	 *
	 * @param size a new limit.
	 *
	 * @throws IllegalArgumentException if {@code size} is negative.
	 */
	public void setSize(final long size) {
		if (size < 0L) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		this.size = size;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code measure} is {@code null}.
	 */
	@Override public Number getLimit(final CacheLimit measure) {
		if (measure == null) {
			throw new IllegalArgumentException();
		}
		switch (measure) {
			case ITEMS:
				return items;
			case SIZE:
				return size;
			default:
				return 0;
		}
	}

}
