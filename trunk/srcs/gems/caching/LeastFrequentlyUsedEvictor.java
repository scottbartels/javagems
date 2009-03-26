package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class LeastFrequentlyUsedEvictor<K> extends AbstractCacheEvictor<K> {

	public LeastFrequentlyUsedEvictor() {
		super(new ComparatorFactory<K>().create());
	}

	private static final class ComparatorFactory<T> {

		private Comparator<? super CacheItemStatistics<T>> create() {
			final List<Comparator<CacheItemStatistics<T>>> comparators = new ArrayList<Comparator<CacheItemStatistics<T>>>(2);
			comparators.add(new LeastFrequentlyUsedEvictionComparator<T>());
			comparators.add(new LeastRecentlyUsedEvictionComparator<T>());
			return new ComposedComparator<CacheItemStatistics<T>>(comparators);
		}

	}

}
