package gems.storage;

import gems.Identifiable;
import gems.filtering.Filter;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedList;

public final class MemoryStorage<K, V extends Identifiable<K>> implements Storage<K, V> {

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

	@Override public Collection<V> search(final Filter<V> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		final Collection<V> result = new LinkedList<V>();
		for (final V value : map.values()) {
			if (filter.allows(value)) {
				result.add(value);
			}
		}
		return result;
	}

	@Override public Iterator<V> iterator() {
		return Collections.unmodifiableCollection(map.values()).iterator();
	}
}
