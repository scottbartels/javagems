package gems.caching;

import gems.Limits;

import java.util.Collection;

/**
 * Cache eviction strategy. Implementation have to be thread-safe, ideally re-entrant. 
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cache objects identifiers.
 */
public interface CacheEvictor<K> {

	/**
	 * Evaluates given collection of cache item statistics and selects
	 * some of them - if any - for eviction as necessary for satisfying
	 * given cache limits. This method should never return {@code null};
	 * at least an empty collection has to returned even if no items are
	 * selected for eviction.
	 *
	 * @param statistics a collection of cache item statistics.
	 * @param limits cache limits to satisfy.
	 * @return a collection - possibly an empty one - of cache item keys selected for eviction.
	 */
	Collection<K> evict(Collection<CacheItemStatistics<K>> statistics, Limits<CacheLimit> limits);

}
