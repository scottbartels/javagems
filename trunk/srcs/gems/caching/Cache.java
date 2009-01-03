package gems.caching;

import gems.Identifiable;

/**
 * A cache abstraction. It provides type-safe operations for storing
 * indentifiable objects in a cache and retriving objects from that
 * cache according their identificators.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <O> a type of objects.
 */
public interface Cache<O extends Identifiable<K>, K> {

	/**
	 * Offers a given object for a caching. The object may or
	 * may not be cached, depending on a cache implementation.
	 *
	 * @param object an object offered for a caching.
	 */
	void offer(O object);

	/**
	 * Returns an object with the given ID from the cache or {@code null}
	 * if no such object is fournd in the cache. Please note that a client
	 * should always expect {@code null} value as a result of cached object
	 * retrieving.
	 *
	 * @param id an ID of required object.
	 *
	 * @return a cached object with a given ID or {@code null} if no such object is currently cached.
	 */
	O get(K id);

}
