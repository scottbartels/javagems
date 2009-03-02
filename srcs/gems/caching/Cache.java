package gems.caching;

import gems.Identifiable;
import gems.Option;

/**
 * A cache is a likely transient storage of indentifiable objects
 * which can be retrieved back according their identifiers.
 * Stored objects may be evicted meantime by cache internal
 * processes, so the client have to be ready for the situation
 * when previously stored object is not in cache anymore. This
 * interface provides an abstraction for a cache: it allows to
 * store indentifiable objects and to try to retrieve them back
 * in type-safe manner.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <V> a type of objects.
 */
public interface Cache<V extends Identifiable<K>, K> {

	/**
	 * Offers a given object for a caching. The object may or
	 * may not be cached, depending on a cache implementation.
	 *
	 * @param object an object offered for a caching.
	 */
	void offer(V object);

	/**
	 * Returns an object idnetified by the given key. The returned
	 * object is encapsulated to {@code Option} object, so the client
	 * has to check whether a required object was or was not found in
	 * the cache. This method should never return {@code null}.
	 *
	 * @param id an ID of required object.
	 *
	 * @return an optional value holding a cached object or holding
	 *         no value if no object with the given identifier  was
	 *         found in the cache.
	 */
	Option<V> get(K id);

}
