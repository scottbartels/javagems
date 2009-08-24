package gems.caching;

import gems.Identifiable;
import gems.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Segmented cache implementation. It holds a flat cache for each segment and delegates operations to these segments.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> type of cached values.
 * @param <K> type of keys.
 */
final class SegmentedCache<V extends Identifiable<K>, K> extends AbstractCacheComponent<V, K> implements Cache<V, K> {

	/**
	 * Segments.
	 */
	private final List<Cache<V, K>> segments;

	/**
	 * Creates a new segmented cache.
	 *
	 * @param properties cache properties.
	 *
	 * @throws IllegalArgumentException if {@code properties} argument is {@code null}.
	 */
	SegmentedCache(final CacheProperties<V, K> properties) {
       super(properties);
		segments = new ArrayList<Cache<V, K>>(getProperties().getSegmenter().maxSegments());
		for (int i = 0; i < getProperties().getSegmenter().maxSegments(); i++) {
			segments.add(new FlatCache<V,K>(properties));
		}
	}

	/**
	 * Finds appropriate segment for a given key.
	 *
	 * @param id a key.
	 *
	 * @return appropriate segment for a given key.
	 */
	private Cache<V, K> getSegment(final K id) {
		return segments.get(getProperties().getSegmenter().getSegment(id));
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
		getSegment(object.getId()).offer(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Option<V> provide(final K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
		return getSegment(key).provide(key);
	}

}
