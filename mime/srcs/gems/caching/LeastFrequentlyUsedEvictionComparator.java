package gems.caching;

import java.util.Comparator;

final class LeastFrequentlyUsedEvictionComparator<K> implements Comparator<CacheItemStatistics<K>> {

	LeastFrequentlyUsedEvictionComparator() {
		// really nothing here
	}

	@Override public int compare(final CacheItemStatistics<K> x, final CacheItemStatistics<K> y) {
		return (int) (y.getAccesses() - x.getAccesses());
	}

}
