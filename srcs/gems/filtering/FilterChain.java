package gems.filtering;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements a chaining of more processing filters and aggregates
 * their filtering according to a selected chaining policy. Filter
 * chain can be used as a normal processing filter, if necessary.
 * The empty chain does not allow object processing regardless of
 * a chaning policy.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FilterChain<T> implements Filter<T> {

	/**
	 * A chaining policy.
	 */
	private final FilterChainingPolicy policy;

	/**
	 * Underlaying filters.
	 */
	private final List<Filter<T>> filters = new LinkedList<Filter<T>>();

	/**
	 * Creates a new empty filter chain with a given aggregation policy.
	 * Policy must not be {@code null} and it is immutable after a filter
	 * chain creation.
	 *
	 * @param policy a chaining policy.
	 *
	 * @throws IllegalArgumentException if {@code policy} is {@code null}.
	 */
	public FilterChain(final FilterChainingPolicy policy) {
		if (policy == null) {
			throw new IllegalArgumentException();
		}
		this.policy = policy;
	}

	/**
	 * Adds a new filter to the chain. It is not allowed {@code null} value here.
	 *
	 * @param filter an added filter.
	 *
	 * @throws IllegalArgumentException if {@code filter} is {@code null}.
	 */
	public void add(final Filter<T> filter) {
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		filters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean allows(final T object) {
		/*
		 * A check for null object SHOULD NOT be performed here, because underlaying
		 * filters can be designed to handle null value as a valid input.
		 */
		if (isEmpty()) {
			return false; // A fast success for an empty chain.
		}
		switch (policy) {
			case SATISFY_ALL:
				return allowsBySatisfyAllPolicy(object);
			case SATISFY_ANY:
				return allowsBySatisfyAnyPolicy(object);
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * Implements 'satisfy any' chaining policy. The {@code true} is returned
	 * if and only if at least one underlaying filter allows processing for a
	 * given object. It implies that {@code false} is returned when the chain
	 * is empty.
	 *
	 * @param object a checked object.
	 *
	 * @return {@code true} iff at least one underlaying filter allows processing for a given object, {@code false} otherwise.
	 */
	private boolean allowsBySatisfyAnyPolicy(final T object) {
		for (final Filter<T> filter : filters) {
			if (filter.allows(object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements 'satisfy all' chaining policy. The {@code true} is returned
	 * if and only if all underlaying filters allow processing for a given
	 * object. It implies that {@code false} is returned when the chain is empty.
	 *
	 * @param object an intercepted object.
	 *
	 * @return {@code true} iff all underlaying filters allow processing for a given object, {@code false} otherwise.
	 */
	private boolean allowsBySatisfyAllPolicy(final T object) {
		assert !isEmpty();
		for (final Filter<T> filter : filters) {
			if (!filter.allows(object)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the chain is empty, i.e. it does not contain any filter.
	 *
	 * @return {@code true} if the chain does not contain any filter, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return filters.isEmpty();
	}

}
