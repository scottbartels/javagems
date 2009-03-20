package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;
import gems.storage.StorageFactory;
import gems.storage.Storage;

import java.util.Collection;
import java.util.LinkedList;

final class FlatCacheStorage<K, V extends Identifiable<K>> implements CacheStorage<K, V> {

	private final SizeEstimator<V> sizer;

	private final Storage<K, V> storage;

	FlatCacheStorage(final SizeEstimator<V> sizer, StorageFactory<K, V> factory) {
		if (sizer == null) {
			throw new IllegalArgumentException();
		}
		if (factory == null) {
			throw new IllegalArgumentException();
		}
		this.sizer = sizer;
		storage = factory.getStorage();
	}

	@Override public Option<V> get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		// todo: 1) try to find
		// todo: 2) if nothing found, return an empty Option
		// todo: 3) if the found one is expired, destroy it and return an empty Option
		// todo: 4) return Option with value gotten from the found one
		return new Option<V>(null);
	}

	@Override public void put(final V value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		// todo: if already owned, update value, or create new one otherwise
	}

	@Override public Collection<CacheItem<K, ?>> itemsForEviction() {
		// todo: 1) Remove expired items first
		// todo: 2) Return all evictable items.
		return new LinkedList<CacheItem<K,?>>();
	}

	@Override public int evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		// todo: remove each key in collection ignoring not owned
		return 0;
	}

}
