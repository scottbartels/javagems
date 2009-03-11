package gems.caching;

import gems.Option;

/**
 * @deprecated due to incomplete design.
 */
@Deprecated interface CacheStorage<K, V> {

	Option<CacheItem<V>> get(K key); // todo: common 'Storage' functionality

	void put(CacheItem<V> item); // todo: common 'Storage' functionality

	void evict(); // todo: cache-specific functionality

}
