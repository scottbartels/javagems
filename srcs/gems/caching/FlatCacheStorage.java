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

	@Override public Option<V> get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		// todo: implement
		return new Option<V>(null);
	}

	@Override public void put(final V value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		// todo: if already owned, update value, or create new one otherwise
	}

	@Override public Collection<CacheItem<K>> itemsForEviction() {
		// todo: return all evictable items.
		return new LinkedList<CacheItem<K>>();
	}

	@Override public int evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		return 0; // todo: remove each key in collection ignoring not owned
	}

}
