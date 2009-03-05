package gems.caching;

public final class CacheLimits {

	private int items;

	private long size;

	public CacheLimits() {
		// really nothing here
	}

	public CacheLimits(final CacheLimits limits) {
		this(limits, 1);
	}

	public CacheLimits(final CacheLimits limits, final int portion) {
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (portion <= 0) {
			throw new IllegalArgumentException(String.valueOf(portion));
		}
		this.items = limits.items / portion;
		this.size = limits.size / portion;
	}

	public void setItems(final int items) {
		if (items < 0) {
			throw new IllegalArgumentException(String.valueOf(items));
		}
		this.items = items;
	}

	public int getItems() {
		return items;
	}

	public void setSize(final long size) {
		if (size < 0L) {
			throw new IllegalArgumentException(String.valueOf(size));
		}
		this.size = size;
	}

	public long getSize() {
		return size;
	}

}
