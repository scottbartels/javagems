package gems.filtering;

/**
 * A wrapper negating a decision of a wrapped filter.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class NegatingFilter<T> implements Filter<T> {

	/**
	 * A wrapped filter.
	 */
	private final Filter<T> filter;

	/**
	 * Creates a new negating wrapper around a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	public NegatingFilter(final Filter<T> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		this.filter = filter;
	}

	/**
	 * Returns a negated decision of the wrapped filter.
	 *
	 * @param object a filtered object.
	 *
	 * @return a negated decision of the wrapped filter.
	 */
	public boolean allows(final T object) {
		return !filter.allows(object);
	}

}
