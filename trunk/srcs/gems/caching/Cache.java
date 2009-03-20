package gems.caching;

import gems.Identifiable;
import gems.ObjectProvider;

/**
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <V> a type of objects.
 */
public interface Cache<V extends Identifiable<K>, K> extends ObjectProvider<V, K> { // todo: Cache IS Storage

	/**
	 * Offers a given object for a caching. The object may or
	 * may not be cached, depending on a cache implementation.
	 *
	 * @param object an object offered for a caching.
	 */
	void put(V object);

}
