package gems;

/**
 * An implementation of <em>Option</em> design pattern. An object of this
 * class is immutable and encapsulates an optional value. A client code which
 * has retrieved he object is forced to check the presence of that optional
 * value before its usage.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of stored value.
 * @since 2009.01
 */
public final class Option<T> {

	/**
	 * A value.
	 */
	private final T value;

	/**
	 * Flag indicating that value presence was already checked.
	 */
	private volatile boolean checked;

	/**
	 * Creates a new option for a given value.
	 * This method accepts {@code null} value.
	 *
	 * @param value a held value.
	 */
	public Option(final T value) {
		this.value = value;
	}

	/**
	 * Checks whether the option holds a real value. This method
	 * have to be called before value retrieval from the option.
	 *
	 * @return {@code true} if the option holds not-null value, {@code false} otherwise.
	 */
	public boolean hasValue() {
		checked = true;
		return value != null;
	}

	/**
	 * Returns a value held by the option. This method returns {@code null}
	 * if and only if previous call of {@code hasValue()} method returned
	 * {@code false}.
	 *
	 * @return a value held by the option.
	 *
	 * @throws IllegalStateException if a value presence was not checked by {@code hasValue()} call yet.
	 */
	public T getValue() {
		if (!checked) {
			throw new IllegalStateException();
		}
		return value;
	}

}
