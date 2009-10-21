package gems.caching;

import java.io.Serializable;
import java.util.Comparator;

final class LeastRecentlyUsedEvictionComparator<K> implements Comparator<CacheItemStatistics<K>>, Serializable {

	private static final long serialVersionUID = -8409516268156334578L;

	@Override public int compare(final CacheItemStatistics<K> x, final CacheItemStatistics<K> y) {
		return (int) (y.getLastAccess() - x.getLastAccess());
	}

}
