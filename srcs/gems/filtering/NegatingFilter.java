package gems.filtering;

import gems.Checks;
import gems.UnexpectedNullException;

/**
 * A wrapper negating a decision of a wrapped filter.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of filtered objects.
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
	 * @throws UnexpectedNullException if {@code filter} is {@code null}.
	 */
	public NegatingFilter(final Filter<? super T> filter) {
		this.filter = Checks.ensureNotNull(filter);
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
