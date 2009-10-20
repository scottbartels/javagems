package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Deprecated public final class LeastFrequentlyUsedEvictor<K> {

	public static <X> CacheEvictor<X> create() {
		return new GenericCacheEvictor<X>((new ComparatorFactory<X>().create()));
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
