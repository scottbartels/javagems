package gems.caching;

import gems.Identifiable;
import gems.Limits;
import gems.SizeEstimator;
import gems.storage.MemoryStorageFactory;
import gems.storage.StorageFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	/* PARALLEL CACHE FACTORIES */

	/**
	 * Creates a new segmented in-memory cache defined by given attributes. Please note
	 * that specified limits are used as limits for a single cache segment, not for the
	 * whole cache.
	 *
	 * @param evictor a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits for a signle segment.
	 * @param segmenter a cache segmenter defining cache segments.
	 * @param <V> type of cached objects.
	 * @param <K> type of cached objects IDs.
	 *
	 * @return a new in-memory cache.
	 * @throws IllegalArgumentException if any of atributes is {@code null}.
	 */
	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvictor<K> evictor,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits,
			final CacheSegmenter<K> segmenter
	) {
		return createCache(evictor, sizer, limits, new MemoryStorageFactory<K, V>(), segmenter);
	}

	/**
	 * Cretes a new segmented cache defined by given attributes. Please note
	 * that specified limits are used as limits for a single cache segment,
	 * not for the whole cache.
	 *
	 * @param evictor a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits for a single segment.
	 * @param factory a factory providing low-level storage objects for cached objects.
	 * @param segmenter a cache segmenter defining cache segments.
	 * @param <V> type of cached objects.
	 * @param <K> type of cached objects IDs.
	 *
	 * @return a new segmented cache.
	 * @throws IllegalArgumentException if any of atributes is {@code null}.
	 */
	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvictor<K> evictor,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits,
			final StorageFactory<K, V> factory,
			final CacheSegmenter<K> segmenter
	) {
		if (sizer == null) {
			throw new IllegalArgumentException();
		}
		if (evictor == null) {
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
		return new SegmentedCache<V, K>(evictor, sizer, limits, factory, segmenter, getPool());
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

	/* FLAT CACHE FACTORIES */

	/**
	 * Creates a new flat in-memory cache defined by given attributes.
	 *
	 * @param evictor a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits.
	 * @param <V> type of cached objects.
	 * @param <K> type of cached objects IDs.
	 *
	 * @return a new flat in-memory cache.
	 * @throws IllegalArgumentException if any of atributes is {@code null}.
	 */
	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvictor<K> evictor,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits
	) {
		return createCache(evictor, sizer, limits, new MemoryStorageFactory<K, V>());
	}

	/**
	 * Creates a new flat cache defind by given attributes.
	 *
	 * @param evictor a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits.
	 * @param factory a factory providing low-level storage objects for cached objects.
	 * @param <V> type of cached objects.
	 * @param <K> type of cached objects IDs.
	 *
	 * @return a new flat cache.
	 * @throws IllegalArgumentException if any of atributes is {@code null}.
	 */
	public static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvictor<K> evictor,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits,
			final StorageFactory<K, V> factory
	) {
		return createCache(evictor, sizer, limits, factory, (ExecutorService) null);
	}

	/**
	 * Creates a new flat cache defined by given attributes.
	 *
	 * @param evictor a cache evicter.
	 * @param sizer an implementation of size estimation of cached objects.
	 * @param limits cache limits.
	 * @param factory a factory providing low-level storage objects for cached objects.
	 * @param pool a thread pool for parallel cache storage; a flat storage is used if it is {@code null}.
	 * @param <V> type of cached objects.
	 * @param <K> type of cached objects IDs.
	 *
	 * @return a new flat cache.
	 * @throws IllegalArgumentException if any of atributes except {@code pool} is {@code null}.
	 */
	static <V extends Identifiable<K>, K> Cache<V, K> createCache(
			final CacheEvictor<K> evictor,
			final SizeEstimator<V> sizer,
			final Limits<CacheLimit> limits,
			final StorageFactory<K, V> factory,
			final ExecutorService pool
	) {
		if (evictor == null) {
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
		return new FlatCache<V, K>(evictor, sizer, limits, factory, pool);
	}

}
