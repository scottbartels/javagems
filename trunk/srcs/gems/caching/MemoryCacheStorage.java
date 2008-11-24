package gems.caching;

import gems.Identifiable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class MemoryCacheStorage<O extends Identifiable<K>, K> implements CacheStorage<O, K> {

	private final Map<K, O> map = new HashMap<K, O>();

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code object} is {@code null}.
	 */
	public synchronized void store(final O object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		map.put(object.getId(), object);
	}


	public synchronized O load(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (!map.containsKey(id)) {
			throw new RuntimeException(); // TODO: REPLACE BY CUSTOM EXCEPTION.
		}
		return map.get(id);
	}

	public synchronized void remove(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (!map.containsKey(id)) {
			throw new RuntimeException(); // TODO: REPLACE BY CUSTOM EXCEPTION.
		}
		map.remove(id);
	}
	
}
