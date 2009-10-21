package gems.caching;

import gems.Checks;
import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Option;
import gems.UnexpectedNullException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class ParallelCacheStorage<K, V extends Identifiable<K>> extends AbstractCacheComponent<V, K> implements CacheStorage<K, V> {

	/**
	 * Stores one underlaying storage and tracks information
	 * about number of items stored in that storage. Please
	 * note that this class is only bean-like holder for the
	 * storage size and it is responsibility of client code
	 * to set right value every time the storage is changed.
	 */
	private static final class StorageHolder<K, V extends Identifiable<K>> {

		/**
		 * A wrapped cache storage.
		 */
		private final CacheStorage<K, V> storage;

		/**
		 * Number of items actually kept in the cache storage.
		 */
		private volatile int size;

		/**
		 * Creates a new storage holder for the given storage and initiates
		 * number of items stored inside to zero. In another words, it is
		 * supposed that the storage is empty.
		 *
		 * @param storage a storage.
		 *
		 * @throws UnexpectedNullException if {@code storage} is {@code null}.
		 */
		private StorageHolder(final CacheStorage<K, V> storage) {
			this.storage = Checks.ensureNotNull(storage);
		}

		/**
		 * Returns the wrapped cache storage. This method never returns {@code null}.
		 *
		 * @return the wrapped cache storage.
		 */
		private CacheStorage<K, V> getStorage() {
			return storage;
		}

		/**
		 * Returns number of items in the wrapped storage. This method never returns a negative value.
		 *
		 * @return number if items in the wrapped storage.
		 */
		private int getSize() {
			return size;
		}

		/**
		 * Sets a new size of the cache storage.
		 *
		 * @param size a new size of the cache storage.
		 *
		 * @throws IllegalArgumentException if {@code size} is {@code null}.
		 */
		private void setSize(final int size) {
			if (size < 0) {
				throw new IllegalArgumentException(String.valueOf(size));
			}
			this.size = size;
		}

	}

	/**
	 * Underlaying storage holders.
	 */
	private final List<StorageHolder<K, V>> storages;

	ParallelCacheStorage(final CacheProperties<V, K> properties) {
		super(properties);
		final int cpus = Runtime.getRuntime().availableProcessors();
		storages = new ArrayList<StorageHolder<K, V>>(cpus);
		for (int i = 0; i < cpus; i++) {
			storages.add(new StorageHolder<K, V>(new FlatCacheStorage<K, V>(properties)));
		}
	}

	@Override public Option<V> get(final K key) {
		final GetTaskResult<K, V> result = getImpl(key);
		if (result != null) {
			return new Option<V>(result.getOption().getValue());
		}
		return new Option<V>(null);
	}

	private GetTaskResult<K, V> getImpl(final K key) {
		final Collection<Future<GetTaskResult<K, V>>> tasks = new LinkedList<Future<GetTaskResult<K, V>>>();
		for (final StorageHolder<K, V> storage : storages) {
			tasks.add(getProperties().getThreadPool().submit(new GetTask<K, V>(storage, key)));
		}
		return getTaskResult(tasks);
	}

	private static <K, V extends Identifiable<K>> GetTaskResult<K, V> getTaskResult(final Iterable<Future<GetTaskResult<K, V>>> tasks) {
		for (final Future<GetTaskResult<K, V>> task : tasks) {
			try {
				final GetTaskResult<K, V> taskResult = task.get();
				if (taskResult.getOption().hasValue()) {
					return taskResult;
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
		if (value == null) {
			throw new IllegalArgumentException();
		}
		final GetTaskResult<K, V> result = getImpl(value.getId());
		if (result != null) {
			result.getStorage().put(value);
		} else {
			final StorageHolder<K, V> storage = getEmptiestStorage();
			storage.getStorage().put(value);
			storage.setSize(storage.getSize() + 1);

		}
	}

	private StorageHolder<K, V> getEmptiestStorage() {
		StorageHolder<K, V> result = storages.get(0);
		for (int i = 1; i < storages.size(); i++) {
			final StorageHolder<K, V> storage = storages.get(i);
			if (storage.getSize() < result.getSize()) {
				result = storage;
			}
		}
		return result;
	}

	@Override public Collection<CacheItemStatistics<K>> itemsForEviction() {
		final Collection<Future<Collection<CacheItemStatistics<K>>>> tasks = new LinkedList<Future<Collection<CacheItemStatistics<K>>>>();
		for (final StorageHolder<K, V> storage : storages) {
			tasks.add(getProperties().getThreadPool().submit(new GatheringEvictablesTask<K, V>(storage)));
		}
		return mergeEvictableItems(tasks);
	}

	private static <T> Collection<CacheItemStatistics<T>> mergeEvictableItems(final Iterable<Future<Collection<CacheItemStatistics<T>>>> tasks) {
		final List<CacheItemStatistics<T>> result = new LinkedList<CacheItemStatistics<T>>();
		for (final Future<Collection<CacheItemStatistics<T>>> task : tasks) {
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

	@Override public int evict(final Collection<K> keys) {
		if (keys == null) {
			throw new IllegalArgumentException();
		}
		final Collection<Future<Integer>> tasks = new LinkedList<Future<Integer>>();
		for (final StorageHolder<K, V> storage : storages) {
			tasks.add(getProperties().getThreadPool().submit(new EvictionTask<K, V>(storage, keys)));
		}
		return randezVousEvicters(tasks);
	}

	private static int randezVousEvicters(Collection<Future<Integer>> tasks) {
		int result = 0;
		for (final Future<Integer> task : tasks) {
			try {
				result += task.get();
			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			} catch (final ExecutionException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			}
		}
		return result;
	}

	/**
	 * Abstract task executed on a cache storage. In fact, it holds only
	 * that cache storage which is a subject of real task execution.
	 */
	private abstract static class AbstractStorageTask<K, V extends Identifiable<K>> {

		/**
		 * A cache storage which is a subject of task execution.
		 */
		private final StorageHolder<K, V> storage;

		/**
		 * Creates a new task for the given cache storage.
		 *
		 * @param storage a cache storage.
		 */
		protected AbstractStorageTask(final StorageHolder<K, V> storage) {
			this.storage = Checks.assertNotNull(storage);
		}

		/**
		 * Returns the storage which is a subject of task execution.
		 *
		 * @return the storage which is a subject of task execution.
		 */
		protected StorageHolder<K, V> getStorage() {
			return storage;
		}

	}

	private static final class GetTaskResult<K, V extends Identifiable<K>> {

		private final Option<V> option;

		private final CacheStorage<K, V> storage;

		private GetTaskResult(final Option<V> option, final CacheStorage<K, V> storage) {
			this.option = option;
			this.storage = storage;
		}

		public Option<V> getOption() {
			return option;
		}

		public CacheStorage<K, V> getStorage() {
			return storage;
		}
	}

	/**
	 * Implements getting value from a storage.
	 */
	private static final class GetTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<GetTaskResult<K, V>> { // todo: review documentation

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
		private GetTask(final StorageHolder<K, V> storage, final K key) {
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
		@Override public GetTaskResult<K, V> call() throws Exception {
			final CacheStorage<K, V> storage = getStorage().getStorage();
			final Option<V> option = storage.get(key);
			return new GetTaskResult<K, V>(option, storage);
		}

	}

	/**
	 * Gets evictable items from the storage.
	 */
	private static final class GatheringEvictablesTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Collection<CacheItemStatistics<K>>> {

		/**
		 * Creates a new task for the given cache storage.
		 *
		 * @param storage a cache storage.
		 */
		private GatheringEvictablesTask(final StorageHolder<K, V> storage) {
			super(storage);
		}

		/**
		 * Returns evictable items from the cache storage.
		 *
		 * @return a collection of evictable items from the cache storage.
		 *
		 * @throws Exception hopefully never.
		 */
		@Override public Collection<CacheItemStatistics<K>> call() throws Exception {
			return getStorage().getStorage().itemsForEviction();
		}

	}

	/**
	 * Runs eviction on the storage.
	 */
	private static final class EvictionTask<K, V extends Identifiable<K>> extends AbstractStorageTask<K, V> implements Callable<Integer> {

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
		private EvictionTask(final StorageHolder<K, V> storage, final Collection<K> keys) {
			super(storage);
			assert keys != null;
			this.keys = keys;
		}

		/**
		 * Runs eviction.
		 */
		@Override public Integer call() throws Exception {
			final StorageHolder<K, V> holder = getStorage();
			final int evictedItems = holder.getStorage().evict(keys);
			holder.setSize(holder.getSize() - evictedItems);
			return evictedItems;
		}

	}

}
