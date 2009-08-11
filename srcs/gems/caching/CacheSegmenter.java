package gems.caching;

/**
 * Provides support for segmented cache. It defines maximum number of segments
 * and maps IDs of cached value to proper segment. Please note that <em>implementations
 * have to be thread-safe</em>. This is pretty straightforward for {@code maxSegments()}
 * method, but take care about {@code getSegment()}: make it properly synchronized,
 * or even better, implement it to be re-entrant. A synchronization inside {@code getSegment()}
 * method can reduce scalability of segmented cache in some circumstances.
 * <p/>
 * <em>Segmented cache</em> is a cache which is  internally splitted into segments.
 * A {@code CacheSegmenter} implementation provides an identification of segment used
 * for storing a object identified by a particular key. The consequence is that only
 * a part of cached objects has to be searched for cached object, which improves
 * performance. Additionally, two operations - including eviction - can be always
 * done concurrently on two different cache segments, which improves scalability
 * mostly when a cache is accessed by more than one threads at the same time. On
 * the other hand, any cache limits (for example maximal number of items kept in
 * the cache) are also splitted among cache segments. As a consequence, the efficient
 * use of segmented cache requires a cache segmenter spreading possible keys to segments
 * more or less uniformly. You are strongly advised test a performance of segmented cache
 * you designed using real data and compare its performance with a <em>flat</em> cache,
 * i.e. with a cache <em>without</em> internal segmentation. Please note that not only
 * real data should be used, but also real-like load simulating possible concurrent
 * clients and the right mixture of read and write operations is necessary to get
 * good judgements.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of key.
 */
public interface CacheSegmenter<K> {

	/**
	 * A null-implementation for the interface. It defines the signle cache segment.
	 */
	CacheSegmenter<Object> NULL_SEGMENTER = new AbstractCacheSegmenter<Object>(1) {

		/**
		 * Always retuns 0.
		 *
		 * @param key an analyzed key.
		 *
		 * @return always 0.
		 *
		 * @throws IllegalArgumentException if {@code key} is {@code null}.
		 */
		@Override public int getSegment(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return 0;
		}

	};

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
	 * must be constant during the whole object life-cycle and it
	 * have to be an integer greater than zero.
	 *
	 * @return number of segments.
	 */
	int maxSegments();

}
