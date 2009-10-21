package gems;

/**
 * Provide a pluggable way how to specify various numeric thresholds,
 * where thresholds are identified by an enumeration. Implementations
 * may provide not only static thresholds, but a sort of self-tuning
 * thresholds are also possible.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <E> an enumeration specifying thresholds.
 */
@Experimental public interface Limits<E extends Enum<E>> {

	/**
	 * A null-implementation of the interface. It always returns zero for any required limit.
	 */
	Limits ZERO_LIMITS = new Limits() {

		/**
		 * Always returns zero.
		 *
		 * @param e ignored.
		 * @return always zero.
		 *
		 * @throws UnexpectedNullException if {@code e} is {@code null}. 
		 */
		public Number getLimit(final Enum e) {
			if (e == null) {
				throw new UnexpectedNullException();
			}
			return 0;
		}

	};

	/**
	 * Returns a limit identified by a given enumeration constant.
	 * Please note that client code should not cache returned value,
	 * because implementation may provide a sort of self-adaptation
	 * and a returned value may vary over time.
	 *
	 * @param e an enumeration constant specifying a required threshold.
	 *
	 * @return a limit identified by a given enumeration constant.
	 */
	Number getLimit(E e);

}

