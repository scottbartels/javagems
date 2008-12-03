package gems.caching;

import gems.Identifiable;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class CacheFragment<O extends Identifiable<K>, K> implements Cache<O, K> {

	public void offer(final O object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		// todo: implement this.
	}

	public O get(final K key) {
		return null; // todo: implement this. 
	}

}
