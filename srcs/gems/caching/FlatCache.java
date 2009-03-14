package gems.caching;

import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class FlatCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

	private final CacheStorage<K, V> storage = null;

	FlatCache(final CacheEvicter<K> evicter, final SizeEstimator<V> sizer, final CacheLimits limits) {
		assert sizer != null;
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

	@Override public Option<V> get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		lock.readLock().lock();
		try {
			return storage.get(id);
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Eviction scheduler. Periodically runs eviction on storage of the
	 * encapsulating cache. A delay between two subsequent evictions is
	 * adaptive and depends on the churn of cached objects.
	 */
	@SuppressWarnings({"ConstantConditions"}) private final class EvictScheduler implements Runnable {

		/**
		 * Minimal possible delay, in seconds.
		 */
		private static final int MIN_DELAY = 2;

		/**
		 * Maximal possible delay, in seconds.
		 */
		private static final int MAX_DELAY = 32;

		private final CacheEvicter<K> evicter;

		private final CacheLimits limits;


		{
			assert MIN_DELAY > 0;
			assert MIN_DELAY <= MAX_DELAY;
		}

		private EvictScheduler(final CacheEvicter<K> evicter, final CacheLimits limits) {
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
				ExceptionHandler.NULL_HANDLER.handle(e); // TODO: IS THERE ANY SMARTER OPTION?
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
					storage.evict(Collections.unmodifiableCollection(keysToEvict));
				}
			} finally {
				lock.writeLock().unlock();
			}
		}

	}

}
