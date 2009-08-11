package gems.caching;

import gems.Identifiable;

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
public final class CacheProperties<V extends Identifiable<K>, K> {

    /**
     * A cache evictor.
     */
    private final CacheEvictor<K> evictor;

    /**
     * A cache segmenter.
     */
    private volatile CacheSegmenter<? super K> segmenter = CacheSegmenter.NULL_SEGMENTER;

    /**
     * An eviction handler.
     */
    private volatile EvictionHandler<? super V> evictionHandler = EvictionHandler.NULL_EVICTION_HANDLER;

    // TODO: LIMITS

    public CacheProperties(final CacheEvictor<K> evictor) {
        if (evictor == null) {
            throw new IllegalArgumentException();
        }
        this.evictor = evictor;
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
        this.evictor = properties.evictor;
        this.segmenter = properties.segmenter;
        this.evictionHandler = properties.evictionHandler;
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

}
