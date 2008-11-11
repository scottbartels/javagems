package gems.caching;

import gems.Identifiable;

/**
 * Encapsulates one cached item and gathers data about cache access for the item.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <V> a type of values.
 */
final class CacheItem<V extends Identifiable<K>, K> {

	/**
	 * A timestamp when an item was placed into a cache.
	 */
	private final long birth = System.currentTimeMillis();

	/**
	 * A cached item.
	 */
	private final V item;

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
	 * @param item a cached item.
	 *
	 * @throws IllegalArgumentException if {@code item} is {@code null}.
	 */
	CacheItem(final V item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		this.item = item;
	}

	/**
	 * Returns a number of cache hits for the item.
	 *
	 * @return a number of cache hits for the item.
	 */
	int getHits() {
		return hits;
	}

	/**
	 * Returns a time how long the item is cached, in milliseconds.
	 *
	 * @return a time how long the item is cached, in milliseconds.
	 */
	long getAge() {
		return System.currentTimeMillis() - birth;
	}

	/**
	 * Returns a timestamp ot the last access of the item.
	 *
	 * @return a timestamp ot the last access of the item.
	 */
	long getAccess() {
		return access;
	}

	/**
	 * Returns the cached item.
	 *
	 * @return the cached item.
	 */
	V getItem() {
		hits++;
		access = System.currentTimeMillis();
		return item;
	}

}
