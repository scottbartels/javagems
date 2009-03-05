package gems.caching;

/**
 * Provides support for segmented cache. It defines maximum number of segments
 * and maps IDs of cached value to proper segment. Please note that <em>implementations
 * have to be thread-safe</em>. This is pretty straightforward for {@code maxSegments()}
 * method, but take care about {@code getSegment()}: make it properly synchronized,
 * or even better, implement it to be re-entrant. A synchronization inside {@code getSegment()}
 * method can reduce scalability of segmented cache in some circumstances.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of key.
 */
public interface CacheSegmenter<K> {

	/**
	 * Returns segment number for a given key. Segments numbering is zero-based.
	 * This method have to return integer from zero (including) to a value returned
	 * by {@code maxSegments()} method (excluding). Implementation of this method
	 * have to be thread-safe, ideally re-entrant.
	 *
	 * @param key an analyzed key.
	 *
	 * @return segment number for a given key.
	 */
	int getSegment(K key);

	/**
	 * Gets number of segments. The value returned by this method
	 * must be constant during the whole object lifecycle and it
	 * have to be a non-negative integer.
	 *
	 * @return number of segments.
	 */
	int maxSegments();

}
