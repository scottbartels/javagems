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

	/**
	 * Creates a new segmented in-memory cache defined by given attributes. 
	 *
	 * @param evicter a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits.
	 * @param segmenter a cache segmenter defining cache segments.
	 *
	 * @return a new in-memory cache.
	 *
	 * @throws IllegalArgumentException if any of atributes is {@code null}.
	 */
	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvicter<K> evicter,
			final SizeEstimator<V> sizer,
			final CacheLimits limits,
			final CacheSegmenter<K> segmenter
	) {
		return createCache(evicter, sizer, limits, new MemoryStorageFactory<K, V>(), segmenter);
	}

	/**
	 * Cretes a new segmented cache defined by given attributes.
	 *
	 * @param evicter a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits.
	 * @param factory a factory providing low-level storage objects for cached objects.
	 * @param segmenter a cache segmenter defining cache segments.
	 * @return
	 */
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
		return new SegmentedCache<V, K>(evicter, sizer, limits, factory, segmenter, getPool());
	}

	/**
	 * Creates a new fixed thread pool with number of threads equal
	 * to number of available processors on multi-processor system.
	 * This method returns {@code null} if only one avalilable
	 * processor is reported by {@code Runtime.availableProcessor()}
	 * method. 
	 *
	 * @return a new thread pool on MP system, or {@code null} on UP system.
	 */
	private static ExecutorService getPool() {
		final int cpus = Runtime.getRuntime().availableProcessors();
		return (cpus == 1 ? null : Executors.newFixedThreadPool(cpus));
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
