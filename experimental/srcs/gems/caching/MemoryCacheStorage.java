package gems.caching;

import gems.Identifiable;

import java.util.HashMap;
import java.util.Map;

/**
 * A cache storage implementation holding objects in memory.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class MemoryCacheStorage<O extends Identifiable<K>, K> implements CacheStorage<O, K> {

	/**
	 * The storage.
	 */
	private final Map<K, O> storage = new HashMap<K, O>();

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code object} is {@code null}.
	 */
	public synchronized void store(final O object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		storage.put(object.getId(), object);
	}


	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 * @throws CacheStorageConsistencyException if no object with a given key is found in the storage.
	 */
	public synchronized O load(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (!storage.containsKey(id)) {
			throw new CacheStorageConsistencyException();
		}
		return storage.get(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 * @throws CacheStorageConsistencyException if no object with a given key is found in the storage.
	 */
	public synchronized void remove(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (!storage.containsKey(id)) {
			throw new CacheStorageConsistencyException();
		}
		storage.remove(id);
	}

}
