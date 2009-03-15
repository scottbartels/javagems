package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

import java.util.Collection;
import java.util.LinkedList;

final class FlatCacheStorage<K, V extends Identifiable<K>> implements CacheStorage<K, V> {

	private final SizeEstimator<V> sizer;

	FlatCacheStorage(final SizeEstimator<V> sizer) {
		assert sizer != null;
		this.sizer = sizer;
	}

	public Option<V> get(final K key) {
		// todo: implement
		return new Option<V>(null);
	}

	public void put(final V value) {
		// todo: if already owned, update value, or create new one otherwise
	}

	public Collection<CacheItem<K>> itemsForEviction() {
		// todo: return all evictable items.
		return new LinkedList<CacheItem<K>>();
	}

	public void evict(final Collection<K> keys) {
		// todo: remove each key in collection ignoring not owned
	}

}
