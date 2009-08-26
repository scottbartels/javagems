package gems.caching;

import gems.Identifiable;
import gems.ObjectProvider;
import gems.Option;

/**
 * A factory for cache instance creation. Provides a way how to construct a new cache
 * without knowledge about different cache implementations and their internals.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
public final class CacheFactory<V extends Identifiable<K>, K> implements ObjectProvider<Cache<V, K>, CacheProperties<V, K>> {

	/**
	 * Creates a new cache configured according given cache properties.
	 * If no properties are given, a null-implementation of cache is
	 * returned; see {@code gems.caching.NullCache} for details.
	 *
	 * @param properties a cache properties encapsulated in {@code Option} object.
	 *
	 * @return a new instance of cache encapsulated in {@code Option} object.
	 *
	 * @throws IllegalArgumentException if {@code properties} is {@code null}.
	 */
	public Option<Cache<V, K>> provide(final Option<CacheProperties<V, K>> properties) {
		if (properties == null) {
			throw new IllegalArgumentException();
		}
		if (properties.hasValue()) {
			final CacheProperties<V, K> props = properties.getValue();
			if (props.getSegmenter().equals(CacheSegmenter.NULL_SEGMENTER)) {
				return new Option<Cache<V, K>>(new FlatCache<V, K>(props));
			}
			return new Option<Cache<V, K>>(new SegmentedCache<V, K>(props));
		}
		return new Option<Cache<V, K>>(new NullCache<V, K>());
	}

}
