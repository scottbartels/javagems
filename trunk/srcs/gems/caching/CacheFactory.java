package gems.caching;

import gems.Identifiable;
import gems.StorageFactory;
import gems.Storage;
import gems.MemoryStorage;
import gems.SizeEstimator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class CacheFactory {
	public static final class MemoryStorageFactory<K, V extends Identifiable<K>> implements StorageFactory<K, V> {

		@Override public Storage<K, V> getStorage() {
			return new MemoryStorage<K, V>();
		}

	}

	private CacheFactory() {
		throw new UnsupportedOperationException();
	}

	// PARALLEL CACHE FACTORIES

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final CacheSegmenter<K> segmenter
	) {
		return createCache(evicter, sizer, limits, new MemoryStorageFactory<K, V>(), segmenter);
	}

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final StorageFactory<K, V> factory,
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
		if (factory == null) {
			throw new IllegalArgumentException();
		}
		if (segmenter == null) {
			throw new IllegalArgumentException();
		}
		final ExecutorService pool;
		final int cpus = Runtime.getRuntime().availableProcessors();
		if (cpus == 1) {
			pool = null;
		} else {
			pool = Executors.newFixedThreadPool(cpus);
		}
		return new SegmentedCache<V, K>(evicter, sizer, limits, factory, segmenter, pool);
	}

	// FLAT CACHE FACTORIES

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits
	) {
		return createCache(evicter, sizer, limits, new MemoryStorageFactory<K, V>());
	}

	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final StorageFactory<K, V> factory
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
		if (factory == null) {
			throw new IllegalArgumentException();
		}
		return createCache(evicter, sizer, limits, factory, (ExecutorService) null);
	}

	static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final StorageFactory<K, V> factory,
			final ExecutorService pool
	) {
		return new FlatCache(evicter, sizer, limits, factory, pool);
	}


}
