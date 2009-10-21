package gems;

/**
 * Estimates size of objects of some type. Please note
 * that there is no contract about units of the result.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of estimated objects.
 */
@Experimental public interface SizeEstimator<T> {

	/**
	 * A null-iplementation of the interface. It does not do any estimation,
	 * it simply returns zero. However, sanity check for {@code null} object
	 * is still performed.
	 */
	SizeEstimator<Object> ZERO_ESTIMATOR = new SizeEstimator<Object>() {

		/**
		 * Always returns zero.
		 *
		 * @param object ignored.
		 *
		 * @return always zero.
		 *
		 * @throws UnexpectedNullException if {@code object} is {@code null}.
		 */
		@Override public long estimate(final Object object) {
			if (object == null) {
				throw new UnexpectedNullException();
			}
			return 0L;
		}

	};

	/**
	 * Estimates size of a given object. This method should always return a non-negative integer.
	 *
	 * @param object an object.
	 *
	 * @return estimated size of a given object.
	 */
	long estimate(T object);

}
