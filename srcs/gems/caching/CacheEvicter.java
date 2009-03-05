package gems.caching;

import java.util.Collection;

public interface CacheEvicter<T> { // todo: needs to be generified better.

	Collection<T> evict(Collection<T> items);
	
}
