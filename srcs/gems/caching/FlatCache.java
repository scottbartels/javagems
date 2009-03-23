package gems.caching;

import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;
import gems.Limits;
import gems.storage.StorageFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ExecutorService;

final class FlatCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	/**
	 * A lock controlling access to storage. 
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

	private final CacheStorage<K, V> storage;

	FlatCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits,
			final StorageFactory<K, V> factory,
			ExecutorService pool) {
		if (pool != null) {
			storage = new ParallelCacheStorage<K,V>(sizer, factory, pool);
		} else {
			storage = new FlatCacheStorage<K,V>(sizer, factory);
		}
		startEvicterDaemon(new EvictScheduler(evicter, limits));
	}

	/**
	 * Starts scheduler daemon for eviction.
	 *
	 * @param scheduler scheduler implementation.
	 */
	private static void startEvicterDaemon(final Runnable scheduler) {
		final Thread daemon = new Thread(scheduler);
		daemon.setDaemon(true);
		daemon.start();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code object} is {@code null}.
	 */
	@Override public void offer(final V object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		lock.writeLock().lock();
		try {
			storage.put(object);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public Option<V> get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		lock.readLock().lock();
		try {
			return storage.get(key);
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Eviction scheduler. Periodically runs eviction on storage of the
	 * encapsulating cache. A delay between two subsequent evictions is
	 * adaptive and depends on the churn of cached objects.
	 */
	private final class EvictScheduler implements Runnable {

		/**
		 * Minimal possible delay, in seconds.
		 */
		private static final int MIN_DELAY = 2;

		/**
		 * Maximal possible delay, in seconds.
		 */
		private static final int MAX_DELAY = 32;

		private final CacheEvicter<K> evicter;

		private final Limits<CacheLimit> limits;

		private EvictScheduler(final CacheEvicter<K> evicter, final Limits<CacheLimit> limits) {
			assert evicter != null;
			assert limits != null;
			this.evicter = evicter;
			this.limits = limits;
		}

		/**
		 * An adaptive delay between two subsequent evictions, in seconds.
		 */
		private int delay = MIN_DELAY;

		/**
		 * Periodically invokes eviction.
		 */
		@Override @SuppressWarnings({"InfiniteLoopStatement"}) public void run() {
			while (true) { // TODO: COULD BE STOPPED BY FINALIZER OF ENCAPSULATING CLASS?
				sleep();
				evict();
			}
		}

		/**
		 * Sleeps.
		 */
		private void sleep() {
			try {
				Thread.sleep(delay * 1000L);
			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e);
			}
		}

		/**
		 * Performs eviction on storage. As a side effect of the eviction,
		 * a delay is adapted. If some objects were selected for eviction,
		 * subsequent delay is set to {@code MIN_DELAY}. In oposite case,
		 * delay is increased by one second up to {@code MAX_DELAY}.
		 */
		private void evict() {
			// Everything must be done holding the write lock. It ensures that there is
			// not any reader active during eviction. It is because a reading operation
			// is modifying cached items statistics data, but an eviction algorithm
			// usually highly depends on them.
			lock.writeLock().lock();
			try {
				final Collection<K> keysToEvict = evicter.evict(storage.itemsForEviction(), limits);
				if (keysToEvict.isEmpty()) {
					if (delay < MAX_DELAY) {
						delay++;
					}
				} else {
					delay = MIN_DELAY;
					final int itemsEvicted = storage.evict(Collections.unmodifiableCollection(keysToEvict));
					assert keysToEvict.size() == itemsEvicted;
				}
			} finally {
				lock.writeLock().unlock();
			}
		}

	}

}
