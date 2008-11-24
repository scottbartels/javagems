package gems.caching;

import gems.Identifiable;

/**
 * Defines a physical storage of cached objects.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> a type of keys.
 * @param <O> a type of objects.
 */
public interface CacheStorage<O extends Identifiable<K>, K> {

	/**
	 * Accepts a given object for a storage.
	 *
	 * @param object an object to store.
	 */
	void store(O object);

	/**
	 * Returns an object identified by a given ID. This method should never
	 * return {@code null}. If no object with a given ID is found in the storage
	 * a runtime exception should be thrown, indicating buggy client code
	 * storing objects into the storage and retrieving them back.
	 *
	 * @param id an ID of required object.
	 *
	 * @return a stored object with a given ID.
	 */
	O load(K id);

	/**
	 * Removes an object identified by a given ID from storage. If no object
	 * with a given ID is found in the storage a runtime exception should be
	 * thrown, indicating buggy client code storing objects into the storage
	 * and removing them.
	 *
	 * @param id an ID of the object to remove.
	 */
	void remove(K id);

}
