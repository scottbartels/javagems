package gems.caching;

/**
 * This is a java bean representing cache limits. The object is mutable,
 * but it provides copying constructors for easy defense copy creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheLimits { // TODO: SHOULD BE AN INTERFACE, but it breaks current usage in segmented cache (splitting limits for segments)

	/**
	 * Default maximal number of items allowed to be stored in a cache.
	 */
	public static final int DEFAULT_ITEMS = 0;

	/**
	 * Default size limit of items allowed to be stored in a cache.
	 */
	public static final long DEFAULT_SIZE = 0L;

	/**
	 * Maximal number of items allowed to be stored in a cache.
	 */
	private int items;

	/**
	 * Size limit of items allowed to be stored in a cache.
	 */
	private long size;

	/**
	 * Creates a new cache limits bean. All limits are set to
	 * default values. See documentation of {@code DEFAULT_*}
	 * constants of this class.
	 */
	public CacheLimits() {
		items = DEFAULT_ITEMS;
		size = DEFAULT_SIZE;
	}

	/**
	 * Creates a new cache limits bean containing the same limits a given object.
	 *
	 * @param limits copied limits.
	 *
	 * @throws IllegalArgumentException if {@code limits} is {@code null}.
	 */
	public CacheLimits(final CacheLimits limits) {
		this(limits, 1);
	}

	/**
	 * Creates a new cache limits bean getting limits from
	 * given object, but scaling them down to given portion.
	 *
	 * @param limits original limits.
	 * @param portion required poriton for new limits.
	 *
	 * @throws IllegalArgumentException if {@code limits} is {@code null} or {@code portion} is less than 1.
	 */
	CacheLimits(final CacheLimits limits, final int portion) {
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (portion < 1) {
			throw new IllegalArgumentException(String.valueOf(portion));
		}
		this.items = limits.items / portion;
		this.size = limits.size / portion;
	}

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
	 * Rerurns a limit for maximal number of items allowed to be
	 * stored in a cache. This method never returns a negative value.
	 *
	 * @return maximal number of items allowed to be stored ina a cache.
	 */
	public int getItems() {
		return items;
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
	 * Returns size limit of items allowed to be stored in
	 * a cache. This method never returns a negative value.
	 *
	 * @return size limit of items allowed to be stored ina a cache.
	 */
	public long getSize() {
		return size;
	}

}
