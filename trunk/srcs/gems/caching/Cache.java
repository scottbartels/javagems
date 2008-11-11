package gems.caching;

import gems.Identifiable;

/**
 * A cache abstraction. It provides type-safe operations for storing
 * objects in a cache, checking if object is present in a cache and
 * retriving objects from a cache. A clearing capability is also provided.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <V> a type of values.
 */
public interface Cache<V extends Identifiable<K>, K> {

	/**
	 * Offers a given object for a caching. The object may or may not be cached, depending on a cache implementation.
	 *
	 * @param object an object offered for a caching.
	 */
	void offer(V object);

	/**
	 * Checks whether an object with a given ID is stored in the cache. This method is provided mainly
	 * for performance reasons in cases when a slow or remote medium is used for objects storing, but
	 * keys are still kept in memory. This method provides a separation of checking whether a required
	 * object is presented in the cache from its real retrieval.
	 *
	 * @param id an ID of checked object.
	 *
	 * @return {@code true} if an object with a given ID is stored in the cache, {@code false} otherwise.
	 */
	boolean has(K id);

	/**
	 * Returns an object with the given ID from the cache or {@code null} if no such object is cached.
	 * Please note that a client should expect {@code null} value even if previous calling of the method
	 * {@code has(Kid)} returned {@code true}, because particular object could be removed from the cache
	 * meantime.
	 *
	 * @param id an ID of required object.
	 *
	 * @return a cached object with a given ID or {@code null} if no such object is currently cached.
	 */
	V get(K id);

}
