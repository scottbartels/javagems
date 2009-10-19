package gems;

/**
 * A skeleton implementation of {@code Identifiable} interface. It provides
 * an easy way how to implement {@code Identifiable} interface correctly. A
 * responsibility to use a suitable type of the ID is still up to implementing
 * subclass.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of the object ID.
 */
public abstract class AbstractIdentifiable<T> implements Identifiable<T> {

	/**
	 * An object ID.
	 */
	private final T id;

	/**
	 * Creates a new object with a given ID. The ID is immutable after the object creation.
	 *
	 * @param id an ID.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 */
	protected AbstractIdentifiable(final T id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * {@inheritDoc} This method never returns {@code null}.
	 */
	@Override public final T getId() {
		return id;
	}

	/**
	 * Defines an equality of two identifiable objects. Two objects are considered to
	 * be equal if and only if they are the same type and their IDs are the same.
	 *
	 * @param o a compared object.
	 *
	 * @return {@code true} if {@code o} is an objects of the same type and with the same ID, {@code false} otherwise.
	 */
	@Override @SuppressWarnings({"unchecked"}) public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Identifiable<T> that = (Identifiable<T>) o;
		return this.id.equals(that.getId());
	}

	/**
	 * Returns a hash code of the object.
	 *
	 * @return a hash code of the object.
	 */
	@Override public final int hashCode() {
		return id.hashCode();
	}

}
