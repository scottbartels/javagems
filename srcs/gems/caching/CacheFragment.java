package gems.caching;

import gems.Identifiable;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class CacheFragment<O extends Identifiable<K>, K> {

	void put(final CacheItem<O, K> item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		// todo: implement this.
	}

	O get(final K key) {
		return null; // todo: implement this. 
	}

}
