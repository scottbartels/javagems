package gems.caching;

import java.util.Collection;

public interface CacheEvicter<T> {

	void evict(Collection<CacheItem<T>> items);
	
}
