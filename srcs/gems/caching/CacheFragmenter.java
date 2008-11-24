package gems.caching;

/**
 * A class implementing this interface is capable to determine <em>cache
 * fragmentation</em>. It provides a number or required cache fragments and
 * evaluates a number of fragment for keys of cached objects. A cache
 * fragmentation is a way how to avoid <em>cache trashing</em> when a high
 * object churn occurs. If cache fragmentation is used, the whole
 * cache is divided into <i>N</i> fragments. Each possible object ID has
 * statically assigned an index ranging from 0 to <i>N</i>-1, and the
 * index determine a cache fragment where the object is stored in. Thus,
 * one object can cause eviction of at most <i>N</i>th part of cache.
 * Please note that frarmenting the cache to <i>N</i> fragments has many
 * consequences, positive and negative:
 * <ul>
 * <li>The maximal size of cacheable object is <i>N</i> times reduced.</li>
 * <li>It is expected that cached objects are well distributed among fragments.
 * Poorly written fragmentation algorithm may cause a situation, when some
 * fragments are continually almost full and many of theirs cached items are
 * ofen evicted, but another fragments stays almost empty and they only
 * blocks allocated space.</li>
 * <li>Object lookup may be faster when cache fragmentation is used, because
 * each object is searched only in its fragment, all other fragments are
 * ignored.</li>
 * </ul>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 */
public interface CacheFragmenter<K> {

	/**
	 * A null-implementation of the interface. It effectivelly
	 * disables a cache segmentation using only one fragment.
	 */
	CacheFragmenter NULL_FRAGMENTER = new CacheFragmenter() {

		/**
		 * Always returns 1.
		 *
		 * @return always 1.
		 */
		public int fragments() {
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
		public int getFragment(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return 0;
		}

	};

	/**
	 * Returns maximal number for cache fragments. Implemntating object should
	 * return a constant value during whole its lifecycle. The returned value
	 * must be greater than zero.
	 *
	 * @return maximal number of cache fragments.
	 */
	int fragments();

	/**
	 * Returns the fragment number for the given key. The returned
	 * value must not be negative and it also must be less than a
	 * value returned by {@code fragments()} method.
	 *
	 * @param key a key.
	 *
	 * @return the getFragment number.
	 */
	int getFragment(K key);

}
