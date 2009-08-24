package gems.caching;

import gems.Identifiable;
import gems.Option;

/**
 * A cache is a likely transient storage of identifiable objects which can be retrieved back
 * according their identifiers. Stored objects may be evicted meantime by cache internal processes,
 * so the client have to be ready for the situation when previously stored object is not in the cache
 * anymore. If you are using a cache, probably the {@code gesm.caching.CachingObjectProvider} wrapper
 * can do your life even easier.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
public interface Cache<V extends Identifiable<K>, K> {

	/**
	 * Offers a given object for a caching. The object may or may not be cached, depending on a cache implementation.
	 *
	 * @param object an object offered for a caching.
	 */
	void offer(V object);

    Option<V> provide(K key);

}
