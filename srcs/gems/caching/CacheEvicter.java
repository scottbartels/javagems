package gems.caching;

import java.util.Collection;

public interface CacheEvicter<K> {

	Collection<K> evict(Collection<CacheItemStatistics<K>> items, CacheLimits limits);
	
}
