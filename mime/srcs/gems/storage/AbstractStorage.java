package gems.storage;

import gems.filtering.Filter;
import gems.Identifiable;

import java.util.Collection;
import java.util.LinkedList;

abstract class AbstractStorage<K, V extends Identifiable<K>> implements Storage<K, V> {

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}. 
	 */
	@Override public final Collection<V> search(final Filter<? super V> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		final Collection<V> result = new LinkedList<V>();
		for (final V value : this) {
			if (filter.allows(value)) {
				result.add(value);
			}
		}
		return result;
	}

}
