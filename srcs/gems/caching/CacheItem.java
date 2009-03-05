package gems.caching;

/**
 * @deprecated due to incomplete implementation.
 */
@Deprecated public final class CacheItem<T> {

	private T value;

	CacheItem(T value) {
		
	}

	boolean isValid() {
		return false;
	}

	T getValue() {
		return value;
	}

	void recordAccess() {

	}

	void updateValue(T object) {

	}
}
