package gems.caching;

import java.io.Serializable;
import java.util.Comparator;

final class LeastFrequentlyUsedEvictionComparator<K> implements Comparator<CacheItemStatistics<K>>, Serializable {
	
	private static final long serialVersionUID = 7099838009626557398L;

	@Override public int compare(final CacheItemStatistics<K> x, final CacheItemStatistics<K> y) {
		return (int) (y.getAccesses() - x.getAccesses());
	}

}
