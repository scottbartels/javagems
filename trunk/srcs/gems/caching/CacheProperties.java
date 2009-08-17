package gems.caching;

import gems.Identifiable;
import gems.Limits;
import gems.SizeEstimator;
import gems.storage.MemoryStorageFactory;
import gems.storage.StorageFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An object holder for various caching subsystem properties or components. There are two sorts of
 * components: <em>mandatory</em> &ndash; these needs to be specified in the time of the holder
 * creation and cannot be changed later and <em>optional</em> &ndash; these are initially set to
 * some default values and can be changed later.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
public final class CacheProperties<V extends Identifiable<K>, K> { // todo: implement fluent interface for setters.

    /**
     * Cache limits.
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

    private volatile SizeEstimator<? super V> sizer = SizeEstimator.ZERO_ESTIMATOR;

    private volatile StorageFactory<K, V> storageFactory = new MemoryStorageFactory<K, V>();

    private volatile ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public CacheProperties(final Limits<CacheLimit> limits) {
        if (limits == null) {
            throw new IllegalArgumentException();
        }
        this.limits = limits;
    }

    /**
     * Copying constructor. Creates a shallow copy of given cache properties holder.
     *
     * @param properties a holder to be copied.
     *
     * @throws IllegalArgumentException if {@code properties} is {@code null}.
     */
    CacheProperties(final CacheProperties<V, K> properties) {
        if (properties == null) {
            throw new IllegalArgumentException();
        }
        this.limits = properties.limits;
        this.evictor = properties.evictor;
        this.segmenter = properties.segmenter;
        this.evictionHandler = properties.evictionHandler;
        this.sizer = properties.sizer;
        this.storageFactory = properties.storageFactory;
        this.threadPool = properties.threadPool;
    }

    /**
     * Returns cache limits associated with the properties object.
     * This method never returns {@code null}.
     *
     * @return  cache limits associated with the properties object.
     */
    public Limits<CacheLimit> getLimits() {
        return limits;
    }

    /**
     * Sets a new cache evictor.
     *
     * @param evictor a new cache evictor.
     *
     * @throws IllegalArgumentException if {@code evictor} is {@code null}.
     */
    public void setEvictor(final CacheEvictor<K> evictor) {
        if (evictor == null) {
            throw new IllegalArgumentException();
        }
        this.evictor = evictor;
    }

    /**
     * Returns a cache evictor associated with the properties object.
     * This method never returns {@code null}.
     *
     * @return a cache evictor associated with the properties object.
     */
    public CacheEvictor<K> getEvictor() {
        return evictor;
    }

    /**
     * Sets a new eviction handler.
     *
     * @param handler a new eviction handler.
     *
     * @throws IllegalArgumentException if {@code handler} is {@code null}.
     */
    public void setEvictionHandler(final EvictionHandler<? super V> handler) {
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        evictionHandler = handler;
    }

    /**
     * Returns an eviction handler associated with the properties object.
     * This method never returns {@code null}. If no eviction handler was
     * set, {@code EvictionHandler.NULL_EVICTION_HANDLER} is returned.
     *
     * @return an eviction handler associated with the properties object.
     */
    public EvictionHandler<? super V> getEvictionHandler() {
        return evictionHandler;
    }

    /**
     * Sets a new cache segmenter.
     *
     * @param segmenter a new cache segmenter.
     *
     * @throws IllegalArgumentException if {@code segmenter} is {@code null}.
     */
    public void setSegmenter(final CacheSegmenter<? super K> segmenter) {
        if (segmenter == null) {
            throw new IllegalArgumentException();
        }
        this.segmenter = segmenter;
    }

    /**
     * Returns a cache segmenter associated with the properties object.
     * This method never returns {@code null}. If no segmenter was set,
     * {@code CacheSegmenter.NULL_SEGMENTER} is returned.
     *
     * @return a cache segmenter associated with the properties object.
     */
    public CacheSegmenter<? super K> getSegmenter() {
        return segmenter;
    }

    public void setSizer(final SizeEstimator<? super V> sizer) {
        if (sizer == null) {
            throw new IllegalArgumentException();
        }
        this.sizer = sizer;
    }

    public SizeEstimator<? super V> getSizer() {
        return sizer;
    }

    public void setStorageFactory(final StorageFactory<K, V> factory) {
        if (factory == null) {
            throw new IllegalArgumentException();
        }
        this.storageFactory = factory;
    }

    public StorageFactory<K, V> getStorageFactory() {
        return storageFactory;
    }

    public void setThreadPool(final ExecutorService pool) {
        if (pool == null) {
            throw new IllegalArgumentException();
        }
        threadPool = pool;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }


}
