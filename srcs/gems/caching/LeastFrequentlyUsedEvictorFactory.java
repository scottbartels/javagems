package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class LeastFrequentlyUsedEvictorFactory<K> implements CacheEvictorFactory<K> {

	public CacheEvictor<K> get() {
		final List<Comparator<CacheItemStatistics<K>>> comparators = new ArrayList<Comparator<CacheItemStatistics<K>>>(2);
		comparators.add(new LeastFrequentlyUsedEvictionComparator<K>());
		comparators.add(new LeastRecentlyUsedEvictionComparator<K>());
		return new GenericCacheEvictor<K>(new ComposedComparator<CacheItemStatistics<K>>(comparators));
	}

}
