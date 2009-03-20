package gems.caching;

import java.util.Collection;

public interface CacheEvicter<K> {

	Collection<K> evict(Collection<CacheItem<K, ?>> items, CacheLimits limits);
	
}
