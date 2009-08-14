package gems.caching;

import gems.Identifiable;

/**
 * A factory for cache instance creation. Provides a way how to construct a new cache
 * whithout knowledge about different cache implementations and their internals.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheFactory {


    /**
     * Just disables an instance creation of this utility class.
     */
    private CacheFactory() {
        throw new UnsupportedOperationException();
    }

    public static <V extends Identifiable<K>, K> Cache<V, K> createCache(final CacheProperties<V, K> properties) {
        if (properties == null) {
            throw new IllegalArgumentException();
        }
        if (properties.getSegmenter().equals(CacheSegmenter.NULL_SEGMENTER)) {
            return new FlatCache<V,K>(properties);
        }
        return new SegmentedCache<V,K>(properties);
    }

}
