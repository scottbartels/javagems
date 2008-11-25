package gems.caching;

/**
 * This exception is thrown when inconsistent work with a cache storage is detected.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheStorageConsistencyException extends RuntimeException {

	/**
	 * Creates a new instance.
	 */
	CacheStorageConsistencyException() {
		super();
	}

	/**
	 * Creates a new instance with a given message.
	 *
	 * @param message an exception message.
	 */
	CacheStorageConsistencyException(final String message) {
		super(message);
	}

	/**
	 * Creates a new instance with given message and cause.
	 *
	 * @param message an exception message.
	 * @param cause an exception cause.
	 */
	CacheStorageConsistencyException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance with a given cause.
	 *
	 * @param cause an exception cause.
	 */
	CacheStorageConsistencyException(final Throwable cause) {
		super(cause);
	}

}
