package gems.caching;

/**
 * Defines a factory for cache evictors.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cache objects identifiers.
 */
public interface CacheEvictorFactory<K> {

	CacheEvictor<K> get();
	
}
