package gems;

/**
 * A factory-like interface defining a type-safe object provider, which optionally provides
 * objects of certain type {@code T} according to some kind of key of type {@code K}.
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
		public Option<Object> get(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return new Option<Object>(null);
		}

	};

	/**
	 * Returns an object according to a given key. The returned
	 * object is encapsulated to {@code Option} object, so the client
	 * has to check whether a required object was provided or not.
	 * Because of this, the method should never return {@code null}.
	 *
	 * @param key a key identifying requested object.
	 *
	 * @return an optional value holding a provided object or empty
	 *         option if required object cannot be provided.
	 */
	Option<T> get(K key);

}
