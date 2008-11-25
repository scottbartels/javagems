package gems.caching;

import gems.AbstractIdentifiable;

/**
 * Encapsulates one cached item and gathers data about cache access for the item.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 */
public final class CacheItem<K> extends AbstractIdentifiable<K> {

	/**
	 * A timestamp when an item was placed into a cache.
	 */
	private final long birth = System.currentTimeMillis();

	/**
	 * Hits counter.
	 */
	private int hits;

	/**
	 * A timestamp of the last access.
	 */
	private long access = birth;

	/**
	 * Creates a new cache item for a given item.
	 *
	 * @param id an ID of cached object.
	 *
	 * @throws IllegalArgumentException if {@code item} is {@code null}.
	 */
	CacheItem(final K id) {
		super(id);
	}

	/**
	 * Increases a hit counter by one.
	 */
	public void hit() {
		hits++;
	}

	/**
	 * Returns a number of cache hits for the item.
	 *
	 * @return a number of cache hits for the item.
	 */
	public int getHits() {
		return hits;
	}

	/**
	 * Returns a time how long the item is cached, in milliseconds.
	 *
	 * @return a time how long the item is cached, in milliseconds.
	 */
	public long getAge() {
		return System.currentTimeMillis() - birth;
	}

	/**
	 * Returns a timestamp ot the last access of the item.
	 *
	 * @return a timestamp ot the last access of the item.
	 */
	public long getAccess() {
		return access;
	}

}
