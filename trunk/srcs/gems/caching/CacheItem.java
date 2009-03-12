package gems.caching;

import gems.AbstractIdentifiable;

/**
 * @deprecated due to incomplete implementation.
 */
@Deprecated public final class CacheItem<K> extends AbstractIdentifiable<K> {

	private long access = System.currentTimeMillis();

	private long hits;

	private long size;

	CacheItem(final K key) {
		super(key);
	}

}
