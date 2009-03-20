package gems.storage;

import gems.Identifiable;
import gems.filtering.Filter;

import java.util.Collection;

public interface Storage<K, V extends Identifiable<K>> extends Iterable<V> {

	void put(V value);

	V get(K key);  // todo: maybe storage IS object provider. 

	/**
	 * Guarantees that object identified by a givne key is not in the storage
	 * after successful return. No contract for pre-conditions is specified by
	 * the interface, it is up to implementation what to do when no object with
	 * that key is found in the storage.
	 *
	 * @param key a key of removed object.
	 */
	void remove(K key); 

	/**
	 * Returns a collection of stored values which satisfy a condistion specified by a given filter.
	 * This method should never return {@code null}, at least empty collection should be returned.
	 *
	 * @param filter a filter of returned values.
	 * @return  a collection of stored values which satisfy a condistion specified by a given filter.
	 */
	Collection<V> search(Filter<V> filter);
	
}
