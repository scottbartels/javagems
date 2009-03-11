package gems.caching;

/**
 * @deprecated due to incomplete implementation.
 */
@Deprecated public final class CacheItem<T> {

	private T value;

	CacheItem(final T value) {
		updateValue(value);
	}

	boolean isValid() { // todo: how to invalidate objects? See that example with weather conditions buoy. 
		return false;
	}

	T getValue() {
		recordAccess();
		return value;
	}

	private void recordAccess() {
		// todo: implemnt
	}

	void updateValue(final T value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}
}
