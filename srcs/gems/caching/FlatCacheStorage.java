package gems.caching;

import gems.AbstractIdentifiable;
import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;
import gems.storage.Storage;
import gems.storage.StorageFactory;
import gems.storage.MemoryStorage;

import java.util.Collection;
import java.util.LinkedList;

final class FlatCacheStorage<K, V extends Identifiable<K>> implements CacheStorage<K, V> {

	private final SizeEstimator<V> sizer;

	/**
	 * This is the storage for cached objects.
	 */
	private final Storage<K, V> values;

	private final Storage<K, CacheItem> items = new MemoryStorage<K, CacheItem>();

	FlatCacheStorage(final SizeEstimator<V> sizer, StorageFactory<K, V> factory) {
		if (sizer == null) {
			throw new IllegalArgumentException();
		}
		if (factory == null) {
			throw new IllegalArgumentException();
		}
		this.sizer = sizer;
		values = factory.getStorage();
	}

	@Override public Option<V> get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		final Option<CacheItem> cachedOption = items.get(key);
		if (!cachedOption.hasValue()) {
			return new Option<V>(null);
		}
		final FlatCacheStorage<K, V>.CacheItem cachedValue = cachedOption.getValue();
		if (cachedValue.isExpired()) { // todo: what about synchronization?
			items.remove(key);
			values.remove(key);
			return new Option<V>(null);
		}
		return new Option<V>(cachedValue.getValue());
	}

	@Override public void put(final V value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		// todo: if already owned, update value, or create new one otherwise
	}

	@Override public Collection<CacheItemStatistics<K>> itemsForEviction() {
		// todo: 1) Remove expired items first
		// todo: 2) Return all evictable items.
		return new LinkedList<CacheItemStatistics<K>>();
	}

	@Override public int evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		// todo: remove each key in collection ignoring not owned
		return 0;
	}

	/**
	 * This is a holder for the unit of cached information. It encapsulates an identifier
	 * of the cached object and various statistics like cache hits, cache misses, last access
	 * time, expiration time, and so on. The cached object itself is held out of this object,
	 * in the dedicated storage, which is a part of encapsulating class.
	 */
	/*### private ###*/
	final class CacheItem extends AbstractIdentifiable<K> { // TODO: MAKE READY FOR A PERSISTENT STORAGE

		/**
		 * Cache statistics.
		 */
		private final CacheItemStatistics<K> statistics;

		/**
		 * A flag indicating that the cache item is expired. If a cache item is
		 * expired, no other business operation is allowed on it and it should
		 * be simply destroyed.
		 */
		private volatile boolean expired;

		/**
		 * Creates a new cached item for a given value of given size.
		 *
		 * @param value a cached object.
		 * @param size a size estimation for the object.
		 */
		/*### private ###*/
		CacheItem(final V value, final long size) {
			super(value.getId());
			statistics = new CacheItemStatistics<K>(value.getId());
			update(value, size);
		}

		/**
		 * Evicts the cached item. Cached object will be removed from the values
		 * storage, and the eviction is recored to statistics of the cache item.
		 *
		 * @throws ItemAlreadyExpiredExpception if the item has been alredy expired.
		 */
		/*### private ###*/
		synchronized void evict() {
			ensureNonExpiredStatus();
			assert isEvictable();
			values.remove(getId());
			statistics.recordEviction();
		}

		/**
		 * Update a cache item with a new value of given size.
		 *
		 * @param value a new value.
		 * @param size a size estimation for the object.
		 *
		 * @throws IllegalArgumentException if {@code value} is {@code null} or {@code size} is negative.
		 * @throws KeysMismatchException if ID of the cache item and ID of given value differs.
		 * @throws ItemAlreadyExpiredExpception if the item has been already expired.
		 */
		/*### private ###*/
		synchronized void update(final V value, final long size) {
			if (value == null) {
				throw new IllegalArgumentException();
			}
			if (!value.getId().equals(getId())) {
				throw new KeysMismatchException();
			}
			if (size < 0) {
				throw new IllegalArgumentException(String.valueOf(size));
			}
			ensureNonExpiredStatus();
			values.put(value);
			statistics.recordSize(size);
		}

		/**
		 * Returns a cached object for the cache item or {@code null} if the item has been evicted.
		 *
		 * @return a cached object for the cache item or {@code null} if the item has been evicted.
		 *
		 * @throws ItemAlreadyExpiredExpception if the item has been already expired.
		 */
		/*### private ###*/
		synchronized V getValue() {
			ensureNonExpiredStatus();
			final Option<V> value = values.get(getId());
			statistics.recordAccess(value.hasValue());
			return value.getValue();
		}

		/**
		 * Checks whether the cache item is evictable. The cache item is considered
		 * to be evictable, if its cahed object is still in the cache and it has not
		 * been evicted yet.
		 *
		 * @return {@code true} if the cache item can be evicted, {@code false} otherwise.
		 *
		 * @throws ItemAlreadyExpiredExpception if the item has been already expired.
		 */
		/*### private ###*/
		synchronized boolean isEvictable() {
			ensureNonExpiredStatus();
			// XXX: This is the naive implementation and it can be unnecessary slow if
			// there is a lot of values. Maybe the cache item can hold its 'evictable'
			// or 'evicted' status itself. On the other hand this is pretty consistent
			// in all cases.
			return !values.get(getId()).hasValue();
		}

		/**
		 * Evaluates whether this cache item has to be evicted. This method can be
		 * called safely on already expired cahe item. The implementation ensures
		 * that once expired item will never return back to non-expired status.
		 *
		 * @return {@code true} if the cache item has to be evicted, {@cdoe false} otherwise.
		 */
		/*### private ###*/
		synchronized boolean isExpired() {
			//  This method may be called for expired item.
			if (expired) { // No way back for once expired item.
				return true;
			}
			// TODO: RECOMPUTE EXPIRED STATUS HERE.
			// This is the only place where 'expired' may be set to 'true'.
			return expired;
		}

		/**
		 * Returns a snapshot of cache item's statistics. This method
		 * can be called safely on already expired cahe item.
		 *
		 * @return s a snapshot of cache item's statistics.
		 */
		/*### private ###*/
		synchronized CacheItemStatistics<K> getStatisticsSnapshot() {
			//  This method may be called for expired item.
			return statistics.getSnapshot();
		}

		/**
		 * Returns silently if the cache item has not been expired yet.
		 *
		 * @throws ItemAlreadyExpiredExpception if the cache item has been expired yet.
		 */
		private void ensureNonExpiredStatus() {
			// Do not call isExpired() here. At first, it has side effects
			// of re-evaluating expired status and it can cause a deadlock,
			// if lack of proper synchronization on upper levels.
			if (expired) {
				throw new ItemAlreadyExpiredExpception();
			}
		}

	}

	/**
	 * This exception is thrown when it is attepted to stored a value
	 * with an ID into a cache item with a different ID. 
	 */
	public static final class KeysMismatchException extends IllegalArgumentException {

		private KeysMismatchException() {
			// really nothing here
		}

	}

	/**
	 * This exception is thrown when it is manipulated from already expired cache item.
	 */
	public static final class ItemAlreadyExpiredExpception extends IllegalStateException {

		private ItemAlreadyExpiredExpception() {
			// really nothing here
		}

	}


}
