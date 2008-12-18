package gems.caching;

import java.util.Collection;

public interface CacheEvict<K> {

	Collection<K> evict(Collection<CacheItem<K>> items);

	Collection<K> evictDueToSize(Collection<CacheItem<K>> items, long remove);

	Collection<K> evictDueToCount(Collection<CacheItem<K>> items, int remove);

}
