package gems;

/**
 * Provide a plugable way how to specify various numeric tresholds,
 * where thresholds are identified by an enumeration. Implementations
 * may provide not only static tresholds, but a sort of self-tuning
 * tresholds are also possible.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <E> an enumeration specifying tresholds.
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
		 * @throws IllegalArgumentException if {@code e} is {@code null}. 
		 */
		public Number getLimit(final Enum e) {
			if (e == null) {
				throw new IllegalArgumentException();
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
	 * @param e an enumeration constant specifying a required treshold.
	 *
	 * @return a limit identified by a given enumeration constant.
	 */
	Number getLimit(E e);

}

