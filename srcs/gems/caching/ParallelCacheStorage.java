package gems.caching;

import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

final class ParallelCacheStorage<K, V extends Identifiable<K>> implements CacheStorage<K, V> {

	private final Collection<CacheStorage<K, V>> storages;

	private final ExecutorService pool;

	ParallelCacheStorage(final SizeEstimator<V> sizer) {
		assert sizer != null;
		final int cpus = Runtime.getRuntime().availableProcessors();
		storages = new ArrayList<CacheStorage<K, V>>(cpus);
		for (int i = 0; i < cpus; i++) {
			storages.add(new FlatCacheStorage<K, V>(sizer));
		}
		pool = Executors.newFixedThreadPool(cpus);
	}

	@Override public Option<V> get(final K key) {
		// todo: 1) get from all storages in parallel
		// todo: 2) ensure that at most one was found (assert?)
		// todo: 3) if one was found, re-pack into new Option and return
		return new Option<V>(null);
	}

	@Override public void put(final V value) {
		// todo: 1) try to get
		// todo: 2) if found, update in the right storage and finish
		// todo: 3) otherwise select storage
		// todo: 4) put value into
	}

	@Override public Collection<CacheItem<K>> itemsForEviction() {
		final Collection<Future<Collection<CacheItem<K>>>> tasks = new LinkedList<Future<Collection<CacheItem<K>>>>();
		for (final CacheStorage<K, V> storage : storages) {
			tasks.add(pool.submit(new GatheringEvictablesTask<K>(storage)));
		}
		final List<CacheItem<K>> result = new LinkedList<CacheItem<K>>();
		try {
			for (final Future<Collection<CacheItem<K>>> task : tasks) {
				result.addAll(task.get());
			}
		} catch (final InterruptedException e) {
			ExceptionHandler.NULL_HANDLER.handle(e);
		} catch (final ExecutionException e) {
			ExceptionHandler.NULL_HANDLER.handle(e);
		}
		return result;
	}

	@Override public void evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		final Collection<Future<?>> tasks = new LinkedList<Future<?>>();
		for (final CacheStorage<K, V> storage : storages) {
			tasks.add(pool.submit(new EvictionTask<K>(storage, keys)));
		}
		try {
			for (final Future<?> task : tasks) {
				task.get();
			}
		} catch (final InterruptedException e) {
			ExceptionHandler.NULL_HANDLER.handle(e);
		} catch (final ExecutionException e) {
			ExceptionHandler.NULL_HANDLER.handle(e);
		}
	}


	private static abstract class AbstractStorageTask<T> {

		private final CacheStorage<T, ? extends Identifiable<T>> storage;

		protected AbstractStorageTask(final CacheStorage<T, ? extends Identifiable<T>> storage) {
			this.storage = storage;
		}

		protected CacheStorage<T, ? extends Identifiable<T>> getStorage() {
			return storage;
		}

	}

	private static final class EvictionTask<T> extends AbstractStorageTask<T> implements Runnable {

		private final Collection<T> keys;

		private EvictionTask(final CacheStorage<T, ? extends Identifiable<T>> storage, final Collection<T> keys) {
			super(storage);
			this.keys = keys;
		}

		@Override public void run() {
			getStorage().evict(keys);
		}

	}

	private static final class GatheringEvictablesTask<T> extends AbstractStorageTask<T> implements Callable<Collection<CacheItem<T>>> {

		private GatheringEvictablesTask(final CacheStorage<T, ? extends Identifiable<T>> storage) {
			super(storage);
		}

		@Override public Collection<CacheItem<T>> call() throws Exception {
			return getStorage().itemsForEviction();
		}

	}

}
