package gems.filtering;

import gems.Checks;
import gems.ShouldNeverHappenException;
import gems.UnexpectedNullException;

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
 * @param <T> a type of filtered objects.
 */
public final class FilterChain<T> implements Filter<T> {

	/**
	 * A chaining policy.
	 */
	private final FilterChainingPolicy policy;

	/**
	 * Underlaying filters.
	 */
	private final List<Filter<? super T>> filters = new LinkedList<Filter<? super T>>();

	/**
	 * Creates a new empty filter chain with a given aggregation policy.
	 * Policy must not be {@code null} and it is immutable after a filter
	 * chain creation.
	 *
	 * @param policy a chaining policy.
	 *
	 * @throws UnexpectedNullException if {@code policy} is {@code null}.
	 */
	public FilterChain(final FilterChainingPolicy policy) {
		this.policy = Checks.ensureNotNull(policy);
	}

	/**
	 * Adds a new filter to the chain. It is not allowed {@code null} value here.
	 *
	 * @param filter an added filter.
	 *
	 * @throws UnexpectedNullException if {@code filter} is {@code null}.
	 */
	public void add(final Filter<? super T> filter) {
		filters.add(Checks.ensureNotNull(filter));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public boolean allows(final T object) {
		/*
		 * A check for null object SHOULD NOT be performed here, because custom
		 * filters can be designed to handle null value as a valid input.
		 */
		if (isEmpty()) {  // A fast path for an empty chain.
			return false;
		}
		switch (policy) {
			case SATISFY_ALL:
				return allowsBySatisfyAllPolicy(object);
			case SATISFY_ANY:
				return allowsBySatisfyAnyPolicy(object);
			default:
				/*
				 * This occurs when someone adds a new policy to the
				 * enumeration but forgot to add its handling here. 
				 */
				throw new ShouldNeverHappenException();
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
		for (final Filter<? super T> filter : filters) {
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
		for (final Filter<? super T> filter : filters) {
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
	 *
	 * @deprecated This method is not a valid part of the class interface and your code should
	 *             not depend on it. If you think opposite, please let us know about your use case.
	 *             However, the method will not be removed before 2010.02.
	 */
	@Deprecated public boolean isEmpty() {
		return filters.isEmpty();
	}

}
