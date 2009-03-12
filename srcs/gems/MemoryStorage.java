package gems;

import java.util.Map;
import java.util.HashMap;

@Experimental public final class MemoryStorage<K, V extends Identifiable<K>> implements Storage<K, V> {

	private final Map<K, V> map = new HashMap<K,V>();

	@Override public void put(final V value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		map.put(value.getId(), value);
	}

	@Override public V get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		return map.get(key);
	}

	@Override public void remove(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		map.remove(key);
	}

}
