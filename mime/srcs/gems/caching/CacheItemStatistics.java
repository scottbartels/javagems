package gems.caching;

import gems.AbstractIdentifiable;

public final class CacheItemStatistics<T> extends AbstractIdentifiable<T> { // TODO: FIELDS DO NOT NEED TO BE VOLATILE ANYMORE

	/**
	 * A timestamp indicating when the item was added to acache.
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

	CacheItemStatistics(final T id) {
		this(id, System.currentTimeMillis());
	}

	private CacheItemStatistics(final T id, final long dateOfBirth) {
		super(id);
		this.dateOfBirth = dateOfBirth;
		this.lastAccess = dateOfBirth;
	}

	synchronized CacheItemStatistics<T> getSnapshot() {
		final CacheItemStatistics<T> result = new CacheItemStatistics<T>(getId(), dateOfBirth);
		result.lastAccess = this.lastAccess;
		result.hits = this.hits;
		result.misses = this.misses;
		result.evictions = this.evictions;
		result.size = this.size;
		return result;
	}

	synchronized void recordSize(final long size) {
		if (size < 0) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		this.size = size;
	}

	synchronized void recordAccess(final boolean hit) {
		if (hit) {
			hits++;
		} else {
			misses++;
		}
		lastAccess = System.currentTimeMillis();
	}

	synchronized void recordEviction() {
		this.size = 0L;
		evictions++;
	}

	// PUBLIC FACADE.

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public long getAge() {
		return System.currentTimeMillis() - dateOfBirth;
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public long getSize() {
		return size;
	}

	public long getHits() {
		return hits;
	}

	public long getMisses() {
		return misses;
	}

	public long getAccesses() {
		return hits + misses;
	}

	public long getEvictions() {
		return evictions;
	}

}
