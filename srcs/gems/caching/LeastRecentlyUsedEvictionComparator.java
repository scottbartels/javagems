package gems.caching;

import java.util.Comparator;

final class LeastRecentlyUsedEvictionComparator<K> implements Comparator<CacheItemStatistics<K>> {

	LeastRecentlyUsedEvictionComparator() {
		// really nothing here
	}

	@Override public int compare(final CacheItemStatistics<K> x, final CacheItemStatistics<K> y) {
		return (int) (y.getLastAccess() - x.getLastAccess());
	}

}
