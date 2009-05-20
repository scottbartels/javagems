package gems.caching;

import gems.Identifiable;
import gems.Option;

/**
 * Common parts of cache implementations.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractCache<V extends Identifiable<K>, K> implements Cache<V, K> {

	/**
	 * Returns a cached object or an empty {@code Option} if no object is found.
	 * This method never returns {@code null}. If the passed {@code key} does not
	 * hold any value, an empty {@code Option} is retuned.
	 *
	 * @param key a key identifying a requested object.
	 *
	 * @return a cached object or an empty {@code Option} if no object is found.
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public final Option<V> provide(final Option<K> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (!key.hasValue()) {
			return new Option<V>(null);
		}
		return retrieve(key);

	}

	/**
	 * Retrives a cached object or returns an empty {@code Option} if no object is found.
	 * This method is called by abstract cache implementation. When called, the passed
	 * {@code Option} object holding a key is already checked for a value presence and
	 * it is non-empty. The {@code null} value is never passed as a {@code key}.
	 *
	 * @param key a key identifying a requested object.
	 *
	 * @return a cached object or returns an empty {@code Option} if no object is found.
	 */
	protected abstract Option<V> retrieve(Option<K> key);

}
