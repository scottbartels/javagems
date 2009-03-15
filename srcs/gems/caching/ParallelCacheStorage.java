package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

final class ParallelCacheStorage<K, V extends Identifiable<K>> implements CacheStorage<K, V> {

	private final Collection<CacheStorage<K, V>> storages;

	ParallelCacheStorage(final SizeEstimator<V> sizer) {
		assert sizer != null;
		final int cpus = Runtime.getRuntime().availableProcessors();
		storages = new ArrayList<CacheStorage<K, V>>(cpus);
		for (int i = 0; i < cpus; i++) {
			storages.add(new FlatCacheStorage<K, V>(sizer));
		}
	}

	public Option<V> get(final K key) {
		// todo: 1) get from all storages in parallel
		// todo: 2) ensure that at most one was found (assert?)
		// todo: 3) if one was found, re-pack into new Option and return
		return new Option<V>(null);
	}

	public void put(final V value) {
		// todo: 1) try to get
		// todo: 2) if found, update in the right storage and finish
		// todo: 3) otherwise select storage
		// todo: 4) put value into
	}

	public Collection<CacheItem<K>> itemsForEviction() {
		// todo: 1) get times for eviction from all storages in parallel
		// todo: 2) merge them together and return.
		return new LinkedList<CacheItem<K>>();
	}

	public void evict(final Collection<K> keys) {
		// todo: pass keys to all storages in parallel.
	}

	private static final class EvictionExecutor<T> implements Runnable {

		private final CacheStorage<T, ? extends Identifiable<T>> storage;

		private final Collection<T> keys;

		private EvictionExecutor(
				final CacheStorage<T, ? extends Identifiable<T>> storage,
				final Collection<T> keys
		) {
			this.storage = storage;
			this.keys = keys;
		}

		public void run() {
			storage.evict(keys);
		}
		
	}

}
