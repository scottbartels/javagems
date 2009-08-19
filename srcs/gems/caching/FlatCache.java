package gems.caching;

import gems.Identifiable;
import gems.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class FlatCache<V extends Identifiable<K>, K> extends AbstractCache<V, K> {

	/**
	 * A lock controlling access to storage.
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

	private final CacheStorage<K, V> storage;

	FlatCache(final CacheProperties<V, K> properties) {
		super(properties);
		storage = new ParallelCacheStorage<K, V>(properties);
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
			evict();
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * Performs eviction on storage.
	 */
	private void evict() {
		final Collection<K> keysToEvict = getProperties().getEvictor().evict(storage.itemsForEviction(), getProperties().getLimits());
		if (!keysToEvict.isEmpty()) {
			final int itemsEvicted = storage.evict(Collections.unmodifiableCollection(keysToEvict));
			assert keysToEvict.size() == itemsEvicted;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override protected Option<V> retrieve(final Option<K> key) {
		lock.readLock().lock();
		try {
			return storage.get(key.getValue());
		} finally {
			lock.readLock().unlock();
		}
	}

}
