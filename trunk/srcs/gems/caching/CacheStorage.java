package gems.caching;

import gems.Option;
import gems.Identifiable;

import java.util.Collection;

/**
 * This interface provides a contract for interaction between
 * the cache and its low level storage implementations.
 *
 * @param <K> type of key identifying cached objects.
 * @param <V> type of cached objects. 
 */
interface CacheStorage<K, V extends Identifiable<K>> {

	/**
	 * Returns a cached value identified by a given key, if found in the store.
	 * If no such object is found in the storage, the returned option is empty.
	 *
	 * @param key a key identifying requested object.
	 * @return a cached value identified by {@code key}.
	 */
	Option<V> get(K key);

	/**
	 * Puts given value into the storage.
	 *
	 * @param value a new cached value. 
	 */
	void put(V value);

	/**
	 * Returns a collection of all cached items suitable for eviction.
	 * In another words, already evicted cache items should not be
	 * contained in the returned collection. This method should never
	 * return {@code null}. If there are not any suitable items for
	 * eviction, an empty collection should be returned.
	 *
	 * @return a collection of cached items suitable for eviction.
	 */
	Collection<CacheItem<K>> itemsForEviction();

	/**
	 * Evicts all cached items identified by keys in a given collection.
	 * If a collection contains a key which is not contained in the storage
	 * itself, the storage has to ignore it silently. Please note that the
	 * {@code keys} collection may be unmodifiable. 
	 *
	 * @param keys keys of cache items to evict.
	 */
	void evict(Collection<K> keys);

}
