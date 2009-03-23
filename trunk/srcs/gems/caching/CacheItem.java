package gems.caching;

import gems.AbstractIdentifiable;
import gems.Identifiable;

/**
 * This is a holder for the unit of cached information. It encapsulates an identifier
 * of the cached object, cached object itself and various statistics like cache hits,
 * cache misses, last access time, expiration time, and so on.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of object identifiers.
 * @param <V> type of cached objects.
 */
final class CacheItem<K, V extends Identifiable<K>> extends AbstractIdentifiable<K> { // TODO: MAKE READY FOR A PERSISTENT STORAGE

	private final CacheItemStatistics<K> statistics;

	/**
	 * A flag indicating that the cache item is expired. If a cache item is
	 * expired, no other business operation is allowed on it and it should
	 * be simply destroyed.
	 */
	private volatile boolean expired;

	/**
	 * TODO: Do not hold value directly. An indirection between CacheItem and its value is necessary. Deja-vu?
	 *
	 * This is still tricky. Let's have 10GB of web pages cached in a persistent cache
	 * storage. Before evicting, we need to go throught all of them and check them for
	 * expiration and evictability. Whit a current implementation, it means to load 10GB
	 * of data into memory.
	 */
	private V value;

	CacheItem(final K key, final V value, final long size) {
		super(key);
		statistics = new CacheItemStatistics<K>(key);
		update(value, size);
	}

	// LOCAL INTERFACE (FOR CACHING INFRASTRUCTURE)

	synchronized void evict() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		value = null;
		statistics.recordEviction();
	}

	synchronized void update(final V value, final long size) { // TODO: Document ItemAlreadyExpiredExpception for this method.
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (!value.getId().equals(getId())) {
			throw new IllegalArgumentException();
		}
		if (size < 0) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		ensureNonExpiredStatus();
		this.value = value;
		statistics.setSize(size);
	}

	synchronized V getValue() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		statistics.recordAccess(value != null);
		return value;
	}

	synchronized boolean isEvictable() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return value != null;
	}

	synchronized boolean isExpired() {
		 // This is the only method which can be called after expired status was set.
		if (expired) {
			return true;
		}
		// TODO: RECOMPUTE EXPIRED STATUS HERE.
		// This is the only place where 'expired' may be set to 'true'.

		return expired;
	}

	synchronized CacheItemStatistics<K> getStatisticsSnapshot() {
		return statistics.getSnapshot();
	}

	private void ensureNonExpiredStatus() {
		// Do not call isExpired() here. At first, it has side effects
		// of re-evaluating expired status and it can cause a deadlock,
		// if lack of proper synchronization on upper levels.
		if (expired) {
			throw new ItemAlreadyExpiredExpception();
		}
	}

	public static final class ItemAlreadyExpiredExpception extends IllegalStateException {

		private ItemAlreadyExpiredExpception() {
			// really nothing here
		}

	}

}
