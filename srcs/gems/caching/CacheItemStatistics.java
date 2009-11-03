package gems.caching;

import gems.AbstractIdentifiable;
import gems.Checks;

/**
 * This class holds statistics about a cached item. The statistics object can be in
 * one of two states - 'live' and 'snapshot' - and provides a different interface
 * in each of them. 'Live' statistic object is created by package local constructor
 * and can be modified by various 'record' methods as particular events occur in the
 * caching subsystem. 'Snapshot' is created by calling {@code getSnapshot()} method.
 * The snapshot is immutable view holding the same values as a live object on time
 * of method call and it provides various getters accessing these values. The snapshot
 * cannot be changed anymore. Basically, 'record' interface of 'live' object is designed
 * for internal use of the caching subsystem, 'get' interface of 'snapshot' object is
 * designed for evictors investigating statistics. <em>The implementation is thread-safe
 * for both interfaces.</em>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheItemStatistics<T> extends AbstractIdentifiable<T> {

	/**
	 * A flag indicating that the object is snapshot.
	 */
	private final boolean isSnapshot;

	/**
	 * A timestamp indicating when the item was added to the cache.
	 */
	private final long dateOfBirth;

	/**
	 * A timestamp indicating the last access to the item's value.
	 */
	private volatile long lastAccess;

	/**
	 * A counter for successful requests of the items value.
	 */
	private volatile long hits;

	/**
	 * A counter for unsuccessful requests of the item size.
	 */
	private volatile long misses;

	/**
	 * A counter of evictions.
	 */
	private volatile long evictions;

	/**
	 * Actual size of item; should be zero for evicted item.
	 */
	private volatile long size;

	/**
	 * A snapshot is cached here until state is changed.
	 */
	private CacheItemStatistics<T> snapshot;

	/**
	 * Creates a new 'live' cache item statistic object for the cached item with given ID.
	 *
	 * @param id an ID of cached item this statistics object is related to.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 */
	CacheItemStatistics(final T id) {
		this(id, System.currentTimeMillis(), false);
	}

	/**
	 * Creates a new cache item statistics object with given attributes.
	 *
	 * @param id an id.
	 * @param dateOfBirth a creation timestamp.
	 * @param isSnapshot a flag indicating that the created item is a snapshot.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 */
	private CacheItemStatistics(final T id, final long dateOfBirth, final boolean isSnapshot) {
		super(id);
		this.dateOfBirth = dateOfBirth;
		this.lastAccess = dateOfBirth;
		this.isSnapshot = isSnapshot;
	}

	/**
	 * Returns an immutable snapshot of the current cache item statistics object.
	 * If the object is already a snapshot, the same object is returned. A snapshot
	 * is cached unti state of the live object is changed. In another words, the same
	 * snapshot object is returned if the live object is not changed meantime. This
	 * method never returns {@code null}.
	 *
	 * @return an immutable snapshot of the current cache item statistics object.
	 */
	synchronized CacheItemStatistics<T> getSnapshot() {
		if (isSnapshot) {
			return this;
		}
		if (snapshot != null) {
			return snapshot;
		}
		snapshot = new CacheItemStatistics<T>(getId(), dateOfBirth, true);
		snapshot.lastAccess = this.lastAccess;
		snapshot.hits = this.hits;
		snapshot.misses = this.misses;
		snapshot.evictions = this.evictions;
		snapshot.size = this.size;
		return snapshot;
	}

	/**
	 * Sets size of cached item.
	 *
	 * @param size a new size.
	 *
	 * @throws IllegalArgumentException if {@code size} is negative.
	 * @throws IllegalStateException if the object is a snapshot.
	 */
	synchronized void recordSize(final long size) {
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		this.size = Checks.ensureNonNegative(size);
		invalidateSnapshot();
	}

	/**
	 * Records an access to cached item.
	 *
	 * @param hit an argument indicating that the access was a hit.
	 *
	 * @throws IllegalStateException if the object is a snapshot.
	 */
	synchronized void recordAccess(final boolean hit) {
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		lastAccess = System.currentTimeMillis();
		if (hit) {
			hits++;
		} else {
			misses++;
		}
		invalidateSnapshot();
	}

	/**
	 * Rercords cached item's eviction. The side effect of this method is setting recorded size to zero.
	 *
	 * @throws IllegalStateException if the object is a snapshot.
	 */
	synchronized void recordEviction() {
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		this.size = 0L;
		evictions++;
		invalidateSnapshot();
	}

	/**
	 * Destroys cached snaspshot.
	 */
	private void invalidateSnapshot() {
		snapshot = null;
	}

	/**
	 * Returns {@code true} if  the object is 'snapshot', {@code false} otherwise.
	 *
	 * @return {@code true} if  the object is 'snapshot', {@code false} otherwise.
	 */
	public boolean isSnapshot() {
		return isSnapshot;
	}

	/**
	 * Returns a timestamp indicating when the cached object was added to cache.
	 *
	 * @return a timestamp indicating when the cached object was added to cache.
	 *
	 * @throws IllegalStateException if the object is not a snapshot.
	 */
	public long getDateOfBirth() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return dateOfBirth;
	}

	/**
	 * Returns a timestamp of the latest access.
	 *
	 * @return a timestamp of the latest access.
	 *
	 * @throws IllegalArgumentException if the object is not a snapshot.
	 */
	public long getLastAccess() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return lastAccess;
	}

	/**
	 * Returns size of the cached item. Zero is returned for evicted items.
	 *
	 * @return size of the cached item.
	 *
	 * @throws IllegalArgumentException if the object is not a snapshot.
	 */
	public long getSize() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return size;
	}

	/**
	 * Returns number of cache hits, i.e. how many times was cached item successfully retrieved from the cache.
	 *
	 * @return number of cache hits.
	 *
	 * @throws IllegalStateException if the object is not a snapsthot.
	 */
	public long getHits() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return hits;
	}

	/**
	 * Returns number of cache misses, i.e. how many times cached item was not found in the cache.
	 *
	 * @return number of cache misses.
	 *
	 * @throws IllegalStateException if the object is not a snapshot.
	 */
	public long getMisses() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return misses;
	}

	/**
	 * Returns total number of accesses - hits and misses - of the cached item.
	 *
	 * @return total number of accesses of the cached item.
	 *
	 * @throws IllegalStateException if the object is not a snapshot.
	 */
	public long getAccesses() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return hits + misses;
	}

	/**
	 * Returns number of evictions of the cached item.
	 *
	 * @return number of evictions of the cached item.
	 *
	 * @throws IllegalStateException if the object is not a snapshot.
	 */
	public long getEvictions() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return evictions;
	}

}
