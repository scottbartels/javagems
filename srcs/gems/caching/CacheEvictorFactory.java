package gems.caching;

public interface CacheEvictorFactory<K> {

	CacheEvictor<K> get();
	
}
