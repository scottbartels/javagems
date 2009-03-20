package gems.caching;

import gems.AbstractIdentifiable;
import gems.Identifiable;

/**
 * This is a holder for the unit of cached information. It encapsulates an identifier
 * of the cached object, cached object itself and various statistics like cache hits,
 * cache misses, last access time, expiration time, and so on.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <K> type of object identifiers.
 * @param <V> type of cached objects.
 */
public final class CacheItem<K, V extends Identifiable<K>> extends AbstractIdentifiable<K> { // TODO: MAKE READY FOR A PERSISTENT STORAGE

	/**
	 * A timestamp indicating when the item was added to acache.
	 */
	private final long dateOfBirth = System.currentTimeMillis();

	/**
	 * A timestamp indicating the last access to the item's value.
	 */
	private volatile long lastAccess = dateOfBirth;

	/**
	 * A counter for successful requests of the items value.
	 */
	private volatile long hits;

	/**
	 * A counter for unsuccessful requests of the item size. 
	 */
	private volatile long misses;

	/**
	 * Actual size of item; should be zero for evicted item.
	 */
	private volatile long size;

	/**
	 * A flag indicating that the cache item is expired. If a cache item is
	 * expired, no other business operation is allowed on it and it should
	 * be simply destroyed.
	 */
	private volatile boolean expired;

	/**
	 * A cached value.
	 */
	private V value;

	/*
	 * TODO: Do not hold value directly. An indirection between CacheItem and its value is necessary. Deja-vu?
	 *
	 * This is still tricky. Let's have 10GB of web pages cached in a persistent cache
	 * storage. Before evicting, we need to go throught all of them and check them for
	 * expiration and evictability. Whit a current implementation, it means to load 10GB
	 * of data into memory.
	 */

	CacheItem(final K key, final V value, final long size) {
		super(key);
		update(value, size);
	}

	// LOCAL INTERFACE (FOR CACHING INFRASTRUCTURE)

	synchronized void evict() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		checkInvariants();
		value = null;
		size = 0;
		checkInvariants();
	}

	synchronized void update(final V value, final long size) { // TODO: Document ItemAlreadyExpiredExpception for this method.
		checkInvariants();
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (!value.getId().equals(getId())) {
			throw new IllegalArgumentException();
		}
		if (size < 0) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		ensureNonExpiredStatus();
		this.value = value;
		this.size = size;
		checkInvariants();
	}

	synchronized V getValue() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		checkInvariants();

		ensureNonExpiredStatus();

		if (value == null) {
			misses++;
		} else {
			hits++;
		}
		lastAccess = System.currentTimeMillis();
		checkInvariants();
		return value;
	}

	private void ensureNonExpiredStatus() {
		// Do not call isExpired() here. At first, it has side effects
		// of re-evaluating expired status and it can cause a deadlock,
		// if lack of proper synchronization on upper levels.
		if (expired) {
			throw new ItemAlreadyExpiredExpception();
		}
	}

	synchronized boolean isEvictable() { // TODO: Document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return value != null;
	}

	synchronized boolean isExpired() {
		 // This is the only method which can be called after expired status was set.
		if (expired) {
			return true;
		}
		// TODO: RECOMPUTE EXPIRED STATUS HERE.
		// This is the only place where 'expired' may be set to 'true'.

		return expired;
	}

	private void checkInvariants() {
		if (size != 0 && value == null) { // todo: replace with assert later (after unit tests are ready for the class)
			throw new IllegalStateException("Non-zero reported size of an empty item: " + size +  " [" + getId() + "]");
		}
	}

	// PUBLIC INTERFACE (FOR EVICTERS)

	public long getDateOfBirth() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return dateOfBirth;
	}

	public long getLastAccess() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return lastAccess;
	}

	public long getSize() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return size;
	}

	public long getHits() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return hits;
	}

	public long getMisses() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return misses;
	}

	public long getAccesses() { // Do NOT document ItemAlreadyExpiredExpception for this method.
		ensureNonExpiredStatus();
		return hits + misses;
	}

	public static final class ItemAlreadyExpiredExpception extends IllegalStateException {

		private ItemAlreadyExpiredExpception() {
			// really nothing here
		}

	}

}
