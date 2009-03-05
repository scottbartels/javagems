package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;

/**
 * @deprecated due to incomplete implementation. 
 */
@Deprecated final class FlatCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	private static final int DELAY = 10; // TODO: INJECT

	private final CacheStorage<K, V> storage = null; // todo: real implementation needed here

	private final CacheEvicter<?> evicter; // TODO: MOVE TO STORAGE.

	private final SizeEstimator<V> sizer; // TODO: MOVE TO STORAGE.

	private final CacheLimits limits; // TODO: MOVE TO STORAGE.

	FlatCache(final CacheEvicter<?> evicter, final SizeEstimator<V> sizer, final CacheLimits limits) {
		assert evicter != null;
		assert sizer != null;
		assert limits != null;
		this.evicter = evicter;
		this.sizer = sizer;
		this.limits = new CacheLimits(limits);
		new EvictSchedulerDaemon(DELAY).start();
	}

	@Override public synchronized Option<V> get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		final Option<CacheItem<V>> item = storage.get(id);
		if (item.hasValue()) {
			final CacheItem<V> cachedItem = item.getValue();
			cachedItem.recordAccess();
			return new Option<V>(cachedItem.getValue());
		}
		return new Option<V>(null);
	}

	@Override public synchronized void offer(final V object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		final Option<CacheItem<V>> item = storage.get(object.getId());
		if (item.hasValue()) {
			item.getValue().updateValue(object);
		} else {
			storage.put(new CacheItem<V>(object));
		}
	}

	private final class EvictSchedulerDaemon extends Thread {

		private final long delay;

		private EvictSchedulerDaemon(final int delay) {
			this.delay = delay * 1000;
			this.setDaemon(true);
		}

		@Override public void run() {
			while (true) {
				try {
					Thread.sleep(delay);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e); // todo: or smething smarter?
				}
				storage.evict();
			}
		}

	}

}
