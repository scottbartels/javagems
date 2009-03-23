package gems.caching;

import gems.Limits;

import java.util.Collection;

public interface CacheEvicter<K> {

	Collection<K> evict(Collection<CacheItemStatistics<K>> items, Limits<CacheLimit> limits);
	
}
