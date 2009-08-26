package gems.caching;

import gems.Identifiable;
import gems.ObjectProvider;
import gems.filtering.Filter;

import java.util.Collection;

interface Storage<K, V extends Identifiable<K>> extends Iterable<V>, ObjectProvider<V, K> {

	/**
	 * Inserts a given value into the storage. Implementation have to gurantee that a return
	 * value returned by the {@code get()} method will contain a value inserted by the last
	 * invokation of the {@code put()} method <em>happened-before</em>. Please note that it
	 * is not required that a particular implementation of {@code Storage} interface ensures
	 * this <em>happen-before</em> semantic. Some implementations may delegate this semantic
	 * to the client code itself. Similarly, there is no contract defined for replacing
	 * existing values; it is up to implementation what to do when an object identified by
	 * the same key already exists in the storage. 
	 *
	 * @param value an inserted value.
	 */
	void put(V value);

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
	Collection<V> search(Filter<? super V> filter);
	
}
