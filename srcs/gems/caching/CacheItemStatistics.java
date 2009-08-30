package gems.caching;

import gems.AbstractIdentifiable;

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

	synchronized void recordSize(final long size) {
		if (size < 0) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		this.size = size;
		invalidateSnapshot();
	}

	synchronized void recordAccess(final boolean hit) {
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		if (hit) {
			hits++;
		} else {
			misses++;
		}
		lastAccess = System.currentTimeMillis();
		invalidateSnapshot();
	}

	synchronized void recordEviction() {
		if (isSnapshot) {
			throw new IllegalStateException();
		}
		this.size = 0L;
		evictions++;
		invalidateSnapshot();
	}

	/**
	 * Destroys cached snaspshot, because item state was modified.
	 */
	private void invalidateSnapshot() {
		snapshot = null;
	}

	public boolean isSnapshot() {
		return isSnapshot;
	}

	public long getDateOfBirth() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return dateOfBirth;
	}

	public long getAge() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return System.currentTimeMillis() - dateOfBirth;
	}

	public long getLastAccess() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return lastAccess;
	}

	public long getSize() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return size;
	}

	public long getHits() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return hits;
	}

	public long getMisses() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return misses;
	}

	public long getAccesses() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return hits + misses;
	}

	public long getEvictions() {
		if (!isSnapshot) {
			throw new IllegalStateException();
		}
		return evictions;
	}

}