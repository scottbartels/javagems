package gems.filtering;

/**
 * A wrapper negating a decision of a wrapped filter.
 *
 * @param <T> a type of filtered objects.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class NegatingFilter<T> implements Filter<T> {

	/**
	 * A wrapped filter.
	 */
	private final Filter<? super T> filter;

	/**
	 * Creates a new negating wrapper around a given filter.
	 *
	 * @param filter a filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	public NegatingFilter(final Filter<? super T> filter) {
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
	@Override public boolean allows(final T object) {
		/*
		 * A check for null object SHOULD NOT be performed here, because wrapped
		 * filter can be designed to handle null value as a valid input.
		 */
		return !filter.allows(object);
	}

}
