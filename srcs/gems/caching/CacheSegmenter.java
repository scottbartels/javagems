package gems.caching;

/**
 * Object implementing this interface is capable to determine cache
 * segmentation. It provides a number or required cache segments and
 * evaluates a number of segment for keys of cached objects.
 * 
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 */
public interface CacheSegmenter<K> {

	/**
	 * A null-implementation of the interface. It effectivelly disables a cache segmentation using the only one segment.
	 */
	CacheSegmenter NULL_SEGMENTER = new CacheSegmenter() {

		/**
		 * Always returns 1.
		 *
		 * @return always 1.
		 */
		public int segments() {
			return 1;
		}

		/**
		 * Always returns 0.
		 *
		 * @param key a key.
		 *
		 * @return always 0.
		 *
		 * @throws IllegalArgumentException if {@code key} is {@code null}. 
		 */
		public int segment(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return 0;
		}

	};

	/**
	 * Returns maximal number for cache segments. Implemntating object
	 * should return a constant value during whole its lifecycle. The
	 * returned value must be greater than zero.
	 *
	 * @return maximal number of cache segments.
	 */
	int segments();

	/**
	 * Returns the segment number for the given key. The returned
	 * value must not be negative and it also must be less than a
	 * value returned by {@code segments()} method.
	 *
	 * @param key a key.
	 *
	 * @return the segment number.
	 */
	int segment(K key);

}
