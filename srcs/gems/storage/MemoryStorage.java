package gems.storage;

import gems.Identifiable;
import gems.Option;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MemoryStorage<K, V extends Identifiable<K>> extends AbstractStorage<K, V> {

	private final Map<K, V> map = new HashMap<K,V>();

	@Override public void put(final V value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		map.put(value.getId(), value);
	}

	@Override public Option<V> get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		return new Option<V>(map.get(key));
	}

	@Override public void remove(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		map.remove(key);
	}

	@Override public Iterator<V> iterator() {
		return Collections.unmodifiableCollection(map.values()).iterator();
	}
}
