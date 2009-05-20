package gems;

/**
 * A factory-like interface defining a type-safe object provider, which optionally provides
 * objects of certain type {@code T} according to some kind of optional key of type {@code K}.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of provided objects.
 * @param <K> type of provided objects identifiers.
 */
@Experimental public interface ObjectProvider<T, K> {

	/**
	 * A null-implementation of the interface. It does not provide anything.
	 */
	public ObjectProvider<Object, Object> NULL_PROVIDER = new ObjectProvider<Object, Object>() {

		/**
		 * Provides nothing. An empty {@code Option} is always returned.
		 *
		 * @param key ignored.
		 *
		 * @return always an empty {@code Option} object.
		 *
		 * @throws IllegalArgumentException if {@code key} is {@code null}.
		 */
		@Override public Option<Object> get(final Option<Object> key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return new Option<Object>(null);
		}

	};

	/**
	 * Returns an object, likely according to a given key. The returned
	 * object is encapsulated to {@code Option} object, so the client
	 * has to check whether a required object was provided or not.
	 * Because of this, the method should never return {@code null}.
	 * The key is also optional and implementation have to be ready for
	 * situation when empty {@code Option} object is given for the key.
	 * It is up to implementation whether to provide or not any obejct
	 * in that situation. Implementation should not throw an exception
	 * in this case; an empty {@code Option} should be returned as the
	 * return value.
	 *
	 * @param key an optional key identifying requested object.
	 *
	 * @return an optional value holding a provided object or empty
	 *         option if required object cannot be provided.
	 */
	Option<T> get(Option<K> key);

}
