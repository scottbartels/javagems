package gems.filtering;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code FilterChain} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilterChain {

	/**
	 * A filtered object.
	 */
	private static final Object OBJ = new Object();

	/**
	 * hecks whether a {@code null} chaining policy is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNull() {
		new FilterChain<Object>(null);
	}

	/**
	 * Checks whether an empty chain with satisfy all policy returns {@code false}.
	 */
	@Test public void emptyStatisfyAllReturnsFalse() {
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL).allows(OBJ));
	}

	/**
	 * Checks whether an empty chain with satisfy any policy returns {@code false}.
	 */
	@Test public void emptyStatisfyAnyReturnsFalse() {
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY).allows(OBJ));
	}

	/**
	 * Checks whether adding a {@code null} filter to the chain is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void addingNullFilterIsForbidden() {
		new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL).add(null);
	}

	/**
	 * Checks whether the chain with satisfy any policy contiaing only denying filters itself denies.
	 */
	@Test public void satisfyAnyRefuses() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.DENY_ALL);
		final Filter<Object> filter = chain;
		Assert.assertFalse(filter.allows(OBJ));
	}

	/**
	 * Checks whether the chain with satisfy all policy containing only allowing filters itself allows.
	 */
	@Test public void satisfyAllAllows() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL);
		chain.add(Filter.ALLOW_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertTrue(filter.allows(OBJ));
	}

	/**
	 * Checks whether the chain with satisfy any policy containing a mixture of filters itself allows.
	 */
	@Test public void satisfyAny() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertTrue(filter.allows(OBJ));
	}

	/**
	 * Checks wheter the chain with satisfy all policy containing a mixture of filters itself denies.
	 */
	@Test public void satisfyAll() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertFalse(filter.allows(OBJ));
	}

}
