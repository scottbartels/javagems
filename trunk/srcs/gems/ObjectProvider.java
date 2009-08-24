package gems;

/**
 * A factory-like interface defining a type-safe object provider, which optionally
 * provides objects of certain type {@code T} according to some kind of optional
 * context of type {@code C}. Classes implementing this interface can serve as
 * factories providing properly customized objects for each caller's request.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of provided objects.
 * @param <C> type of context.
 */
@Experimental public interface ObjectProvider<T, C> {

	/**
	 * A null-implementation of the interface. It does not provide anything.
	 */
	public ObjectProvider<Object, Object> NULL_PROVIDER = new ObjectProvider<Object, Object>() {

		/**
		 * Provides nothing. A new empty {@code Option} is always returned.
		 *
		 * @param context ignored.
		 *
		 * @return always a new empty {@code Option} object.
		 *
		 * @throws IllegalArgumentException if {@code context} is {@code null}.
		 */
		@Override public Option<Object> provide(final Option<Object> context) {
			if (context == null) {
				throw new IllegalArgumentException();
			}
			return new Option<Object>(null);
		}

	};

	/**
	 * Returns an object, likely according to a given context. The resulting object can or
	 * cannot be provided, so it is  encapsulated to {@code Option} wrapper, so the client
	 * has to check whether a required object was provided or not. Because of this,
	 * the method should never return {@code null}; an empty {@code Option} object
	 * should be returned to indicate that no object can be provided. The context is
	 * also optional and implementation have to be ready for a situation when empty
	 * {@code Option} object is passed for the context. It is up to implementation
	 * to provide or not any obejct in that situation.
	 *
	 * @param context an optional context helping to create a properly customized object.
	 *
	 * @return an optional value holding a provided object or empty option if required object cannot be provided.
	 */
	Option<T> provide(Option<C> context);

}
