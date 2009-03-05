package gems.caching;

import gems.Identifiable;
import gems.Option;

import java.util.ArrayList;
import java.util.List;

final class SegmentedCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	private final CacheSegmenter<K> segmenter;

	private final List<Cache<V, K>> segments;

	SegmentedCache(final CacheEvicter<?> evicter, final SizeEstimator<V> sizer, final CacheLimits limits, final CacheSegmenter<K> segmenter) {
		assert segmenter != null;
		this.segmenter = segmenter;
		segments = new ArrayList<Cache<V, K>>(segmenter.maxSegments());
		for (int i = 0; i < segmenter.maxSegments(); i++) {
			segments.add(CacheFactory.create(evicter, sizer, new CacheLimits(limits, segmenter.maxSegments())));
		}
	}

	private Cache<V, K> getSegment(final K id) {
		return segments.get(segmenter.getSegment(id));
	}

	@Override public synchronized void offer(final V object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		getSegment(object.getId()).offer(object);
	}

	@Override public synchronized Option<V> get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return getSegment(id).get(id);
	}
}
