package gems.filtering;

/**
 * General interface defining condition which may or may not be satisfied.
 *
 * @author Jozef BABJAK
 */
public interface Condition {

	/**
	 * A null-implementation of the interface representing always satisfied condition.
	 */
	Condition ALWAYS_SATISFIED = new Condition() {

		/**
		 * Returns always {@code true}.
		 *
		 * @return always {@code true}.
		 */
		@Override public boolean isSatisfied() {
			return true;
		}

	};

	/**
	 * A null-implementation of interface representing never satisfied condition.
	 */
	Condition NEVER_SATISFIED = new Condition() {

		/**
		 * Returns always {@code false}.
		 *
		 * @return always {@code false}.
		 */
		@Override public boolean isSatisfied() {
			return false;
		}

	};

	/**
	 * Checks whether the condition is satisfied.
	 *
	 * @return {@code true} if the condition is actually satisfied, {@code false} otherwise.
	 */
	boolean isSatisfied();

}
