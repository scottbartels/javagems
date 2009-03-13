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

	private static final int DELAY = 10; // TODO: INJECT

	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

	private final CacheLimits limits;

	private final CacheEvicter<K> evicter;

	private final CacheStorage<K, V> storage = null;

	FlatCache(final CacheEvicter<K> evicter, final SizeEstimator<V> sizer, final CacheLimits limits) {
		assert evicter != null;
		assert sizer != null;
		assert limits != null;
		this.limits = limits;
		this.evicter = evicter;
		new EvictSchedulerDaemon(DELAY).start();
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

	private final class EvictSchedulerDaemon extends Thread {

		// TODO: SOMETHING SMARTER FOR HIGH LOADS?
		//
		// 1) delay might be adaptive. If no items are evicted in one run,
		//    delay might be increased. If some items are evicted [in N subsequent
		//    runs, delay might be decreased.
		//
		// 2) frequency of adding new items might be observed and this information
		//    can be used somehow.
		//
		// 3) wait-notify mechamizm might be used instead of sleeping. 

		private final long delay;

		private EvictSchedulerDaemon(final int delay) {
			this.delay = delay * 1000L;
			this.setDaemon(true);
		}

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
				Thread.sleep(delay);
			} catch (final InterruptedException e) {
				ExceptionHandler.NULL_HANDLER.handle(e); // TODO: IS THERE ANY SMARTER OPTION?
			}
		}

		/**
		 * Performs eviction on storage.
		 */
		private void evict() {
			// Everything must be done holding the write lock. It ensures that there is
			// not any reader active during eviction. It is because a reading operation
			// is modifying cached items statistics data, but an eviction algorithm
			// usually highly depends on them.
			lock.writeLock().lock();
			try {
				final Collection<K> keysToEvict = evicter.evict(storage.itemsForEviction(), limits);
				if (!keysToEvict.isEmpty()) {
					storage.evict(Collections.unmodifiableCollection(keysToEvict));
				}
			} finally {
				lock.writeLock().unlock();
			}
		}

	}

}
