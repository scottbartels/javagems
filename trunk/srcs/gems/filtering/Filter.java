package gems.filtering;

/**
 * A <em>filter</em> gives a possibility to decide about processing
 * of any particular object. A type of checked object is specified
 * using generics. The interface also provides two own implementations
 * for denying or allowing of any given object processing. These
 * null-implementations forbid a {@code null} value by throwing
 * {@code IllegalArgumentException} encouraging clients to ensure
 * that only not-null values are passed to filter.
 *
 * @param <T> a type of filtered objects.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface Filter<T> {

	/**
	 * Null-implementation of a filter allowing all objects for a processing. This
	 * filter forbids {@code null} values by an {@code IllegalArgumentException} throwing.
	 */
	Filter<Object> ALLOW_ALL = new Filter<Object>() {

		/**
		 * Always returns {@code true}, but a sanity check for {@code null} argument is still performed.
		 *
		 * @param object a checked object.
		 *
		 * @return always {@code true}.
		 *
		 * @throws IllegalArgumentException if {@code object} is {@code null}.
		 */
		public boolean allows(final Object object) {
			if (object == null) {
				throw new IllegalArgumentException();
			}
			return true;
		}

	};

	/**
	 * Null-implementation of a filter denying all objects for a processing. This
	 * filter forbids {@code null} values by an {@code IllegalArgumentException} throwing.
	 */
	Filter<Object> DENY_ALL = new Filter<Object>() {

		/**
		 * Always returns {@code false}, but a sanity check for {@code null} argument is still performed.
		 *
		 * @param object a checked object.
		 *
		 * @return always {@code false}.
		 *
		 * @throws IllegalArgumentException if {@code object} is {@code null}.
		 */
		public boolean allows(final Object object) {
			if (object == null) {
				throw new IllegalArgumentException();
			}
			return false;
		}

	};

	/**
	 * Checks if a given object is allowed for a processing.
	 *
	 * @param object a checked object.
	 *
	 * @return {@code true} if a given object is allowed for a processing, {@code false} othrewise.
	 */
	boolean allows(T object);

}
