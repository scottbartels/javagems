package gems.caching;

import gems.Identifiable;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class CacheFragment<O extends Identifiable<K>, K> extends AbstractLimitedCache<O, K> {

	private final CacheStorage<O, K> storage = new MemoryCacheStorage<O,K>();

	CacheFragment(final CacheLimits limits) {
		super(limits);
	}

	public void offer(final O object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		storage.store(object);
	}

	public O get(final K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		return storage.load(key);
	}

}
