package gems.caching;

import gems.ComposedComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Provides <em>last-recenlty used</em> evictors.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cache objects identifiers.
 */
public final class LeastRecentlyUsedEvictorFactory<K> implements CacheEvictorFactory<K> {

	/**
	 * Returns a <em>last-recently used</em> evictor. This method never returns {@code null}.
	 *
	 * @return a <em>last-recently used</em> evictor.
	 */
	public CacheEvictor<K> get() {

		// todo: with the current implementation, result can be cached.
		
		final List<Comparator<CacheItemStatistics<K>>> comparators = new ArrayList<Comparator<CacheItemStatistics<K>>>(2);
		comparators.add(new LeastRecentlyUsedEvictionComparator<K>());
		comparators.add(new LeastFrequentlyUsedEvictionComparator<K>());
		return new GenericCacheEvictor<K>(new ComposedComparator<CacheItemStatistics<K>>(comparators));
	}

}
