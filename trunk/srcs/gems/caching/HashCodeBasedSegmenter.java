package gems.caching;

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
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public int getSegment(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		// todo: needs to be tested whether keys are mapped to all segments.
		final int hash = key.hashCode();
		if (hash == Integer.MIN_VALUE) {
			return 0;
		}
		return Math.abs(hash) % maxSegments();
	}

}
