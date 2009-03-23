package gems.caching;

import gems.Limits;

import java.util.*;

/**
 * A cache evictor implementaton based on a particural ordering
 * of cache item statistics provide by an external comarator.
 * the only responsibility of a subclass is to provide that
 * comparator during evictor creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cache objects identifiers.
 */
public abstract class AbstractCacheEvictor<K> implements CacheEvicter<K> { // todo: rename to AbstractComparatorBasedCacheEvictor (or something like that)

	/**
	 * A comparator.
	 */
	private final Comparator<? super CacheItemStatistics<K>> comparator;

	/**
	 * Creates a new comparator-based evictor using the given comparator. The comparator
	 * should define such ordering of {@code CacheItemStatistics} objects, that list of
	 * these statistics objects ordered according the comparator has an object most suitable
	 * for eviction as the last one.
	 *
	 * @param comparator a comparator.
	 *
	 * @throws IllegalArgumentException if {@code comparator} is {@code null}.
	 */
	protected AbstractCacheEvictor(final Comparator<? super CacheItemStatistics<K>> comparator) {
		if (comparator == null) {
			throw new IllegalArgumentException();
		}
		this.comparator = comparator;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public final Collection<K> evict(
			final Collection<CacheItemStatistics<K>> statistics,
			final Limits<CacheLimit> limits
	) {
		if (statistics == null) {
			throw new IllegalArgumentException();
		}
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (statistics.isEmpty()) {
			return Collections.emptyList();
		}
		final Collection<CacheItemStatistics<K>> sorted = getSorted(statistics);
		final List<K> result = new LinkedList<K>();

		// TODO: PLACE ITEMS FOR EVICTION TO THE 'result' LIST

		return result;
	}

	/**
	 * Returns a collection of statistics with iteration order specified by the comparator.
	 *
	 * @param statistics objects to sort.
	 *
	 * @return a collection of statistics with iteration order specified by the comparator.
	 */
	private Collection<CacheItemStatistics<K>> getSorted(final Collection<CacheItemStatistics<K>> statistics) {
		final List<CacheItemStatistics<K>> result = new ArrayList<CacheItemStatistics<K>>(statistics);
		if (result.size() > 1) {
			Collections.sort(result, comparator);
		}
		return result;
	}
	
}
