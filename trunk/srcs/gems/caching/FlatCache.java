package gems.caching;

import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

final class FlatCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	private static final int DELAY = 10; // TODO: INJECT

	private final CacheEvicter<K> evicter;

	private final CacheStorage<K, V> storage = null;

	FlatCache(final CacheEvicter<K> evicter, final SizeEstimator<V> sizer, final CacheLimits limits) {
		assert evicter != null;
		assert sizer != null;
		assert limits != null;
		this.evicter = evicter;
		new EvictSchedulerDaemon(DELAY).start();
	}

	@Override public synchronized void offer(final V object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		storage.put(object);
	}

	@Override public synchronized Option<V> get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return storage.get(id);
	}
	
	// TODO: USE SYNCHRONIZATION ALLOWING SEVERAL CONCURRENT LOOKUPS

	private synchronized void evict() {
		storage.evict(evicter.evict(storage.itemsForEviction()));
	}

	private final class EvictSchedulerDaemon extends Thread { // TODO: SOMETHING SMARTER MIGHT BE USEFUL FOR HIGH LOADS

		private final long delay;

		private EvictSchedulerDaemon(final int delay) {
			this.delay = delay * 1000;
			this.setDaemon(true);
		}

		@SuppressWarnings({"InfiniteLoopStatement"})
		@Override public void run() {
			while (true) {
				try {
					Thread.sleep(delay);
				} catch (final InterruptedException e) {
					ExceptionHandler.NULL_HANDLER.handle(e); // TODO: IS THERE ANY SMARTER OPTION?
				}
				evict();
			}
		}

	}

}
