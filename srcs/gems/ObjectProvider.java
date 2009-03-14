package gems;

@Experimental public interface ObjectProvider<T, K> {

	public ObjectProvider<Object, Object> NULL_PROVIDER = new ObjectProvider<Object, Object>() {

		public Option<Object> provide(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return new Option<Object>(null);
		}

	};

	/**
	 * Returns an object idnetified by the given key. The returned
	 * object is encapsulated to {@code Option} object, so the client
	 * has to check whether a required object was provided or not.
	 * Because of this, the method should never return {@code null}.
	 *
	 * @param id an ID of required object.
	 *
	 * @return an optional value holding a provided object or empty
	 *         option if required object cannot be provided.
	 */
	Option<T> provide(K id);

}
