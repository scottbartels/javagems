package gems.caching;

import gems.ExceptionHandler;
import gems.Identifiable;
import gems.Limits;
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
        // TODO: REMOVE: startEvicterDaemon(new EvictScheduler(evictor, limits));
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
     */
    @Override protected Option<V> retrieve(final Option<K> key) {
        lock.readLock().lock();
        try {
            return storage.get(key.getValue());
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

        private static final long MILLISECONDS_PER_SECOND = 1000L;

        /**
         * Minimal possible delay, in seconds.
         */
        private static final int MIN_DELAY = 2;

        /**
         * Maximal possible delay, in seconds.
         */
        private static final int MAX_DELAY = 32;

        private final CacheEvictor<K> evictor;

        private final Limits<CacheLimit> limits;

        /**
         * An adaptive delay between two subsequent evictions, in seconds.
         */
        private int delay = MIN_DELAY;

        private EvictScheduler(final CacheEvictor<K> evictor, final Limits<CacheLimit> limits) {
            assert evictor != null;
            assert limits != null;
            this.evictor = evictor;
            this.limits = limits;
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
                Thread.sleep(delay * MILLISECONDS_PER_SECOND);
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
                final Collection<K> keysToEvict = evictor.evict(storage.itemsForEviction(), limits);
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
