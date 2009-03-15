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
		// todo: reorder storages that way to have the emptiest one first; it will be used for subsequent insert.  
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

	/**
	 * Abstract task executed on a cache storage. In fact, it holds only
	 * that cache storage which is a subject of real task execution.
	 */
	private static abstract class AbstractStorageTask<K, V extends Identifiable<K>> {

		/**
		 * A cache storage which is a subject of task execution.
		 */
		private final CacheStorage<K, V> storage;

		/**
		 * Creates a new task for the given cache storage.
		 *
		 * @param storage a cache storage.
		 */
		protected AbstractStorageTask(final CacheStorage<K, V> storage) {
			assert storage != null;
			this.storage = storage;
		}

		/**
		 * Returns the storage which is a subject of task execution.
		 *
		 * @return the storage which is a subject of task execution.
		 */
		protected CacheStorage<K, V> getStorage() {
			return storage;
		}

	}

	/**
	 * Implements getting value from a storage.
	 */
	private static final class GetTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Option<V>> {

		/**
		 * A key of required value.
		 */
		private final K key;

		/**
		 * Creates a new task for a getting value identified by {@code key} from the {@code storage}.
		 *
		 * @param storage a storage which is a subject of getting task.
		 * @param key a key of required object.
		 */
		private GetTask(final CacheStorage<K, V> storage, final K key) {
			super(storage);
			assert key != null;
			this.key = key;
		}

		/**
		 * Gets a value from the cache storage.
		 *
		 * @return a value from the cache storage.
		 *
		 * @throws Exception hopefully never.
		 */
		@Override public Option<V> call() throws Exception {
			return getStorage().get(key);
		}

	}

	/**
	 * Gets evictable items from the storage.
	 */
	private static final class GatheringEvictablesTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Collection<CacheItem<K>>> {

		/**
		 * Creates a new task for the given cache storage.
		 *
		 * @param storage a cache storage.
		 */
		private GatheringEvictablesTask(final CacheStorage<K, V> storage) {
			super(storage);
		}

		/**
		 * Returns evictable items from the cache storage.
		 *
		 * @return a collection of evictable items from the cache storage.
		 *
		 * @throws Exception hopefully never.
		 */
		@Override public Collection<CacheItem<K>> call() throws Exception {
			return getStorage().itemsForEviction();
		}

	}

	/**
	 * Runs eviction on the storage.
	 */
	private static final class EvictionTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Runnable {

		/**
		 * Keys to evict.
		 */
		private final Collection<K> keys;

		/**
		 * Creates a new task evicting {@code keys} from {@code storage}.
		 *
		 * @param storage a cache storage.
		 * @param keys keys to evict.
		 */
		private EvictionTask(final CacheStorage<K, V> storage, final Collection<K> keys) {
			super(storage);
			assert keys != null;
			this.keys = keys;
		}

		/**
		 * Runs eviction.
		 */
		@Override public void run() {
			getStorage().evict(keys);
		}

	}

}
