package gems.caching;

/**
 * Defines an operation evaluating a size of an object to be cached.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of values.
 */
public interface CacheSizeEvaluator<T> {

	/**
	 * A null-implementation of the interface.
	 */
	CacheSizeEvaluator NULL_EVALUATOR = new CacheSizeEvaluator() {

		/**
		 * Always returns 0.
		 *
		 * @param object an evaluated object.
		 *
		 * @return always 0.
		 *
		 * @throws IllegalArgumentException if {@code object} is {@code null}.
		 */
		public long evaluate(final Object object) {
			if (object == null) {
				throw new IllegalArgumentException();
			}
			return 0;
		}

	};

	/**
	 * Evaluates size of a given object. The returned value must not be negative.
	 *
	 * @param object an evaluated object.
	 *
	 * @return a size evaluation of a given object.
	 */
	long evaluate(T object);

}
