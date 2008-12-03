package gems.caching;

public final class CacheLimits {

	private int count = Integer.MAX_VALUE;

	private long size = Long.MAX_VALUE;

	public CacheLimits() {
		// really nothing here
	}

	CacheLimits(final CacheLimits limits, final int fragment) {
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		if (fragment < 1) {
			throw new IllegalArgumentException();
		}
		this.count = limits.count / fragment;
		this.size = limits.size / fragment;
	}


	public void setCount(final int count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setSize(final long size) {
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
	}

	public long getSize() {
		return size;
	}

}
