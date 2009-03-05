package gems.caching;

import java.util.Collection;

/**
 * @deprecated due to incomplete design. 
 */
@Deprecated public interface CacheEvicter<T> { // todo: needs to be generified better.

	Collection<T> evict(Collection<T> items);
	
}
