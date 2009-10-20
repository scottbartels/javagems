package gems.caching;

/**
 * A skeleton implementation of cache segmenter holding maximal number of segments.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of key.
 */
abstract class AbstractCacheSegmenter<K> implements CacheSegmenter<K> {

	/**
	 * Number of segments.
	 */
	private final int segments;

	/**
	 * Creates a new segmenter for given numbe of segments.
	 *
	 * @param segments number of segments.
	 *
	 * @throws IllegalArgumentException if {@code segments} is less than 1.
	 */
	protected AbstractCacheSegmenter(final int segments) {
		if (segments < 1) { // todo: Checks.ensurePositive();
			throw new IllegalArgumentException(String.valueOf(segments));
		}
		this.segments = segments;
	}

	/**
	 * Gets number of segments.
	 *
	 * @return number of segments.
	 */
	@Override public final int maxSegments() {
		return segments;
	}

}
