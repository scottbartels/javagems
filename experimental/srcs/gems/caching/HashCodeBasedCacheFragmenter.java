package gems.caching;

/**
 * A cache fragmentation algorithm implementation based on IDs' hash codes.
 * You can try to use this implementation when you would like to use
 * cache fragmentation, but you do not have any reasonable fragmentaion
 * algorithm. A quality of cache fragmentation effectiveness directly
 * depneds on quality of {@code hashCode()} function of given IDs.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class HashCodeBasedCacheFragmenter<K> implements CacheFragmenter<K> {

	/**
	 * Number of fragments.
	 */
	private final int fragments;

	/**
	 * Create a new instance supporting a given number of fragments.
	 *
	 * @param fragments a number of fragments.
	 *
	 * @throws IllegalArgumentException if {@code fragments} is less than 1.
	 */
	public HashCodeBasedCacheFragmenter(final int fragments) {
		if (fragments < 1) {
			throw new IllegalArgumentException();
		}
		this.fragments = fragments;
	}

	/**
	 * {@inheritDoc}
	 */
	public int fragments() {
		return fragments;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	public int getFragment(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		return key.hashCode() % fragments;
	}

}
