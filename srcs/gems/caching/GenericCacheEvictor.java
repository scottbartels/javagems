package gems.caching;

import gems.Limits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A cache evictor implementation based on a particular ordering
 * of cache item statistics provided by an external comparator.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cache objects identifiers.
 */
final class GenericCacheEvictor<K> implements CacheEvictor<K> {

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
	GenericCacheEvictor(final Comparator<? super CacheItemStatistics<K>> comparator) {
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
		final List<K> result = new LinkedList<K>();
		int cumulativeCount = 0;
		long cumulativeSize = 0L;
		for (final CacheItemStatistics<K> item : getSorted(statistics)) {
			if (satisfyLimits(item, limits, cumulativeCount, cumulativeSize)) {
				cumulativeCount++;
				cumulativeSize += item.getSize();
			} else {
				result.add(item.getId());
			}
		}
		return result;
	}

	private static boolean satisfyLimits(
			final CacheItemStatistics<?> item,
			final Limits<CacheLimit> limits,
			final int cumulativeCount,
			final long cumulativeSize
	) {
		return !countExceeded(limits, cumulativeCount) && !sizeExceeded(limits, cumulativeSize + item.getSize());
	}

	private static boolean sizeExceeded(final Limits<CacheLimit> limits, final long size) {
		return size > limits.getLimit(CacheLimit.SIZE).longValue();
	}

	private static boolean countExceeded(final Limits<CacheLimit> limits, final int cumulativeCount) {
		return cumulativeCount + 1 > limits.getLimit(CacheLimit.ITEMS).intValue();
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
