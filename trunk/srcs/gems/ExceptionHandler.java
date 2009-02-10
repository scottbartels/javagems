package gems;

/**
 * Defines an action handling an exception. It is suitable where pluggable execpion handling is desired.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of accepted exception.
 * @since 2009.02
 */
@Experimental public interface ExceptionHandler<T extends Throwable> {

	/**
	 * Null-implementation of {@code ExceptionHandler} interface. It does nothing.
	 */
	ExceptionHandler<Throwable> NULL_HANDLER = new ExceptionHandler<Throwable>() {

		/**
		 * Does nothing.
		 *
		 * @param t ignored.
		 */
		@Override public void handle(final Throwable t) {
			// really nothing here
		}

	};

	/**
	 * Handles a given exception.
	 *
	 * @param t a handled exception.
	 */
	void handle(T t);

}
