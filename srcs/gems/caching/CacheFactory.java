package gems.caching;

import gems.Identifiable;
import gems.SizeEstimator;

public final class CacheFactory {

	private CacheFactory() {
		throw new UnsupportedOperationException();
	}

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final CacheSegmenter<K> segmenter
	) {
		if (evicter == null) {
			throw new IllegalArgumentException();
		}
		if (sizer == null) {
			throw new IllegalArgumentException();
		}
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (segmenter == null) {
			throw new IllegalArgumentException();
		}
		return new SegmentedCache<V, K>(evicter, sizer, limits, segmenter);
	}

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits
	) {
		if (evicter == null) {
			throw new IllegalArgumentException();
		}
		if (sizer == null) {
			throw new IllegalArgumentException();
		}
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		return new FlatCache<V, K>(evicter, sizer, limits);
	}

}
