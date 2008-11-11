package gems.caching;

import gems.Identifiable;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface CacheStorage<V extends Identifiable<K>, K> {

	void store(CacheItem<V, K> item);

	CacheItem<V, K> load(K id);

	void remove(K id);
	
}
