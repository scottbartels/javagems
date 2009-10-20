package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class LeastRecentlyUsedEvictorFactory<K> implements CacheEvictorFactory<K> {

	public CacheEvictor<K> get() {
		final List<Comparator<CacheItemStatistics<K>>> comparators = new ArrayList<Comparator<CacheItemStatistics<K>>>(2);
		comparators.add(new LeastRecentlyUsedEvictionComparator<K>());
		comparators.add(new LeastFrequentlyUsedEvictionComparator<K>());
		return new GenericCacheEvictor<K>(new ComposedComparator<CacheItemStatistics<K>>(comparators));
	}

}
