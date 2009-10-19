package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class LeastRecentlyUsedEvictor<K> extends AbstractCacheEvictor<K> {

	public LeastRecentlyUsedEvictor() {
		super(new LeastRecentlyUsedEvictor.ComparatorFactory<K>().create());
	}

	private static final class ComparatorFactory<T> {

		private Comparator<? super CacheItemStatistics<T>> create() {
			final List<Comparator<CacheItemStatistics<T>>> comparators = new ArrayList<Comparator<CacheItemStatistics<T>>>(2);
			comparators.add(new LeastRecentlyUsedEvictionComparator<T>());
			comparators.add(new LeastFrequentlyUsedEvictionComparator<T>());
			return new ComposedComparator<CacheItemStatistics<T>>(comparators);
		}

	}

}
