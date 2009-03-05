package gems.caching;

import gems.Option;

interface CacheStorage<K, V> {

	Option<CacheItem<V>> get(K key);

	void put(CacheItem<V> item);

	void evict();

}
