package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.SizeEstimator;
import gems.storage.StorageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Segmented cache implementation. It holds a flat cache for
 * each segment and delegates operations to these segments.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> type of cached values.
 * @param <K> type of keys.
 */
final class SegmentedCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	/**
	 * A segmenter.
	 */
	private final CacheSegmenter<K> segmenter;

	/**
	 * Segments.
	 */
	private final List<Cache<V, K>> segments;

	/**
	 * Creates a new segmented cache. 
	 *
	 * @param evicter passed to flat cache segments as is.
	 * @param sizer passed to flat cache segments as is.
	 * @param limits passed to flat cache segments splitting limits across them.
	 * @param factory
	 * @param segmenter a segmenter defining a segmentation for the cache.
	 *
	 * @param pool
	 * @throws IllegalArgumentException if {@code segmenter} is {@code null}.
	 */
	SegmentedCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			StorageFactory<K, V> factory, final CacheSegmenter<K> segmenter,
			ExecutorService pool) {
		if (segmenter == null) {
			throw new IllegalArgumentException();
		}
		this.segmenter = segmenter;
		segments = new ArrayList<Cache<V, K>>(segmenter.maxSegments());
		for (int i = 0; i < segmenter.maxSegments(); i++) {
			segments.add(CacheFactory.createCache(evicter, sizer, new CacheLimits(limits, segmenter.maxSegments()), factory, pool));
		}
	}

	/**
	 * Finds appropriate segment for a given key.
	 *
	 * @param id a key.
	 * @return appropriate segment for a given key. 
	 */
	private Cache<V, K> getSegment(final K id) {
		return segments.get(segmenter.getSegment(id));
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
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public Option<V> provide(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		return getSegment(key).provide(key);
	}
	
}