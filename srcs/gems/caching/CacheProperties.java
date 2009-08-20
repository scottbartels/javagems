package gems.caching;

import gems.Identifiable;
import gems.Limits;
import gems.SizeEstimator;
import gems.Checks;
import gems.storage.MemoryStorageFactory;
import gems.storage.StorageFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An object holder for various caching subsystem properties or components. The object
 * is immutable after creation. The only way how to create the object is using the inner
 * builder. All getters always returns non-null return values.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
public final class CacheProperties<V extends Identifiable<K>, K> { // todo: review generification of fields; maybe it can be more strict

    /**
     * Cache limits.
     */
    private final Limits<CacheLimit> limits;

    /**
     * A cache evictor.
     */
    private final CacheEvictor<K> evictor;

    /**
     * A cache segmenter.
     */
    private final CacheSegmenter<? super K> segmenter;

    /**
     * An eviction handler.
     */
    private final EvictionHandler<? super V> evictionHandler;

    /**
     * A size estimator.
     */
    private final SizeEstimator<? super V> sizer;

    /**
     * A cache storage factory.
     */
    private final StorageFactory<K, V> storageFactory;

    /*
     * A thread pool.
     */
    private final ExecutorService threadPool;

    // TODO: INJECT LOGGER

    public CacheProperties(
            final Limits<CacheLimit> limits,
            final CacheEvictor<K> evictor,
            final EvictionHandler<? super V> evictionHandler,
            final CacheSegmenter<? super K> segmenter,
            final SizeEstimator<? super V> sizer,
            final StorageFactory<K, V> storageFactory,
            final ExecutorService threadPool
    ) {
        this.limits = Checks.assertNotNull(limits);
        this.evictor = Checks.assertNotNull(evictor);
        this.evictionHandler = Checks.assertNotNull(evictionHandler);
        this.segmenter = Checks.assertNotNull(segmenter);
        this.sizer = Checks.assertNotNull(sizer);
        this.storageFactory = Checks.assertNotNull(storageFactory);
        this.threadPool = Checks.assertNotNull(threadPool);
    }


    /**
     * Returns cache limits associated with the properties object.
     * This method never returns {@code null}.
     *
     * @return cache limits associated with the properties object.
     */
    Limits<CacheLimit> getLimits() {
        return limits;
    }

    /**
     * Returns a cache evictor associated with the properties object.
     * This method never returns {@code null}.
     *
     * @return a cache evictor associated with the properties object.
     */
    CacheEvictor<K> getEvictor() {
        return evictor;
    }

    /**
     * Returns an eviction handler associated with the properties object.
     * This method never returns {@code null}. If no eviction handler was
     * set, {@code EvictionHandler.NULL_EVICTION_HANDLER} is returned.
     *
     * @return an eviction handler associated with the properties object.
     */
    EvictionHandler<? super V> getEvictionHandler() {
        return evictionHandler;
    }

    /**
     * Returns a cache segmenter associated with the properties object.
     * This method never returns {@code null}. If no segmenter was set,
     * {@code CacheSegmenter.NULL_SEGMENTER} is returned.
     *
     * @return a cache segmenter associated with the properties object.
     */
    CacheSegmenter<? super K> getSegmenter() {
        return segmenter;
    }

    SizeEstimator<? super V> getSizer() {
        return sizer;
    }

    StorageFactory<K, V> getStorageFactory() {
        return storageFactory;
    }

    ExecutorService getThreadPool() {
        return threadPool;
    }

    public static <V extends Identifiable<K>, K> Builder<V, K> builder(final Limits<CacheLimit> limits) {
        if (limits == null) {
            throw new IllegalArgumentException();
        }
        return new Builder<V, K>(limits);
    }

    public static final class Builder<V extends Identifiable<K>, K> {

        /**
         * A cache limits.
         */
        private final Limits<CacheLimit> limits;

        /**
         * A cache evictor.
         */
        private volatile CacheEvictor<K> evictor = new LeastRecentlyUsedEvictor<K>();

        /**
         * A cache segmenter.
         */
        private volatile CacheSegmenter<? super K> segmenter = CacheSegmenter.NULL_SEGMENTER;

        /**
         * An eviction handler.
         */
        private volatile EvictionHandler<? super V> evictionHandler = EvictionHandler.NULL_EVICTION_HANDLER;

        /**
         * A size estimator.
         */
        private volatile SizeEstimator<? super V> sizer = SizeEstimator.ZERO_ESTIMATOR;

        /**
         * A cache storage factory.
         */
        private volatile StorageFactory<K, V> storageFactory = new MemoryStorageFactory<K, V>();

        /*
         * A thread pool.
         */
        private volatile ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        private Builder(final Limits<CacheLimit> limits) {
            assert limits != null;
            this.limits = limits;
        }

        /**
         * Creates a new cache properties from the builder. It is safe to call
         * this method more than once. This method never returns {@code null}.
         *
         * @return a newly created cache properties object.
         */
        public CacheProperties<V, K> build() {
            return new CacheProperties<V, K>(limits, evictor, evictionHandler, segmenter, sizer, storageFactory, threadPool);
        }

        /**
         * Sets a new cache evictor.
         *
         * @param evictor a new cache evictor.
         *
         * @return this {@code Builder object} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code evictor} is {@code null}.
         */
        public Builder<V, K> with(final CacheEvictor<K> evictor) {
            if (evictor == null) {
                throw new IllegalArgumentException();
            }
            this.evictor = evictor;
            return this;
        }

        /**
         * Sets a new cache segmenter.
         *
         * @param segmenter a new cache segmenter.
         *
         * @return this {@code Builder object} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code segmenter} is {@code null}.
         */
        public Builder<V, K> with(final CacheSegmenter<? super K> segmenter) {
            if (segmenter == null) {
                throw new IllegalArgumentException();
            }
            this.segmenter = segmenter;
            return this;
        }

        /**
         * Sets a new eviction handler.
         *
         * @param evictionHandler a new eviction handler.
         *
         * @return this {@code Builder object} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code handler} is {@code null}.
         */
        public Builder<V, K> with(final EvictionHandler<? super V> evictionHandler) {
            if (evictionHandler == null) {
                throw new IllegalArgumentException();
            }
            this.evictionHandler = evictionHandler;
            return this;
        }

        /**
         * Sets a new sizer.
         *
         * @param sizer a new sizer.
         *
         * @return this {@code Builder} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code sizer} is {@code null}.
         */
        public Builder<V, K> with(final SizeEstimator<? super V> sizer) {
            if (sizer == null) {
                throw new IllegalArgumentException();
            }
            this.sizer = sizer;
            return this;
        }

        /**
         * Sets a new storage factory.
         *
         * @param factory a new storage factory.
         *
         * @return this {@code Builder} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code factory} is {@code null}.
         */
        public Builder<V, K> with(final StorageFactory<K, V> factory) {
            if (factory == null) {
                throw new IllegalArgumentException();
            }
            this.storageFactory = factory;
            return this;
        }

        /**
         * Sets a new thread pool.
         *
         * @param threadPool a new thread pool.
         *
         * @return this {@code Builder} enabling fluent interface usage.
         *
         * @throws IllegalArgumentException if {@code threadPool} is {@code null}.
         */
        public Builder<V, K> with(final ExecutorService threadPool) {
            if (threadPool == null) {
                throw new IllegalArgumentException();
            }
            this.threadPool = threadPool;
            return this;
        }

    }

}
