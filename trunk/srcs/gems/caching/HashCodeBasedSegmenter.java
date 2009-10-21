package gems.caching;

import gems.Checks;
import gems.UnexpectedNullException;

/**
 * A cache segmenter based on keys hash codes.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of cahed object keys.
 */
public final class HashCodeBasedSegmenter<K> extends AbstractCacheSegmenter<K> {

	/**
	 * Creates a new segmenter for a given number of segments.
	 *
	 * @param segments number of segments.
	 *
	 * @throws IllegalArgumentException if {@code segments} is less than 1.
	 */
	public HashCodeBasedSegmenter(final int segments) {
		super(segments);
	}

	/**
	 * Returns segment number for a given key.
	 *
	 * @param key an analyzed key.
	 *
	 * @return segment number for a given key.
	 *
	 * @throws UnexpectedNullException if {@code key} is {@code null}.
	 */
	@Override public int getSegment(final K key) {
		return Math.abs(Checks.ensureNotNull(key).hashCode() % maxSegments());
	}

}
