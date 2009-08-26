package gems.caching;

import gems.Identifiable;
import gems.Option;

/**
 * This is a type-safe null-implementation of {@code Cache} interface.
 * It effectively does nothing except sanity checks of input arguments,
 * serving as a mock object. It can be created directly for this purpose,
 * or it is returned by {@code gems.caching.CacheFactory} when no cache
 * properties is specified.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
public final class NullCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	/**
	 * Does nothing.
	 *
	 * @param object ignored except {@code null} sanity check.
	 *
	 * @throws IllegalArgumentException if {@code object} is {@code null}.
	 */
	public void offer(final V object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns an empty option.
	 *
	 * @param id ignored except {@code null} sanity check.
	 *
	 * @return always a new empty option.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 */
	public Option<V> get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return new Option<V>(null);
	}

}
