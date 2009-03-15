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
		final Collection<Future<Option<V>>> tasks = new LinkedList<Future<Option<V>>>();
		for (final CacheStorage<K, V> storage : storages) {
			tasks.add(pool.submit(new GetTask<K, V>(storage, key)));
		}
		return new Option<V>(getValue(tasks));
	}

	private static <T> T getValue(final Iterable<Future<Option<T>>> tasks) {
		for (final Future<Option<T>> task : tasks) {
			try {
				final Option<T> option = task.get();
				if (option.hasValue()) {
					return option.getValue();
				}
			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			} catch (final ExecutionException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			}
		}
		return null;
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
			tasks.add(pool.submit(new GatheringEvictablesTask<K, V>(storage)));
		}
		return mergeEvictableItems(tasks);
	}

	private static <T> Collection<CacheItem<T>> mergeEvictableItems(final Iterable<Future<Collection<CacheItem<T>>>> tasks) {
		final List<CacheItem<T>> result = new LinkedList<CacheItem<T>>();
		for (final Future<Collection<CacheItem<T>>> task : tasks) {
			try {
				result.addAll(task.get());
			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			} catch (final ExecutionException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			}
		}
		return result;
	}

	@Override public void evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		final Collection<Future<?>> tasks = new LinkedList<Future<?>>();
		for (final CacheStorage<K, V> storage : storages) {
			tasks.add(pool.submit(new EvictionTask<K, V>(storage, keys)));
		}
		randezVous(tasks);
	}

	private static void randezVous(Collection<Future<?>> tasks) {
		for (final Future<?> task : tasks) {
			try {
				task.get();

			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			} catch (final ExecutionException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			}
		}
	}


	private static abstract class AbstractStorageTask<K, V extends Identifiable<K>> {

		private final CacheStorage<K, V> storage;

		protected AbstractStorageTask(final CacheStorage<K, V> storage) {
			this.storage = storage;
		}

		protected CacheStorage<K, V> getStorage() {
			return storage;
		}

	}

	private static final class GetTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Option<V>> {

		private final K key;

		private GetTask(CacheStorage<K, V> storage, final K key) {
			super(storage);
			this.key = key;
		}

		public Option<V> call() throws Exception {
			return getStorage().get(key);
		}
	}

	private static final class GatheringEvictablesTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Collection<CacheItem<K>>> {

		private GatheringEvictablesTask(final CacheStorage<K, V> storage) {
			super(storage);
		}

		@Override public Collection<CacheItem<K>> call() throws Exception {
			return getStorage().itemsForEviction();
		}

	}

	private static final class EvictionTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Runnable {

		private final Collection<K> keys;

		private EvictionTask(final CacheStorage<K, V> storage, final Collection<K> keys) {
			super(storage);
			this.keys = keys;
		}

		@Override public void run() {
			getStorage().evict(keys);
		}

	}

}
