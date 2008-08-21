package gems.filtering;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilterChain {

	private static final Object OBJ = new Object();

	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNull() {
		new FilterChain<Object>(null);
	}

	@Test public void emptyStatisfyAllReturnsFalse() {
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL).allows(OBJ));
	}

	@Test public void emptyStatisfyAnyReturnsFalse() {
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY).allows(OBJ));
	}

	@Test(expected = IllegalArgumentException.class) public void addingNullFilterisForbidden() {
		new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL).add(null);
	}

	@Test public void satisfyAnyRefuses() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.DENY_ALL);
		final Filter<Object> filter = chain;
		Assert.assertFalse(filter.allows(OBJ));
	}

	@Test public void satisfyAllAllows() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL);
		chain.add(Filter.ALLOW_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertTrue(filter.allows(OBJ));
	}

	@Test public void satisfyAny() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertTrue(filter.allows(OBJ));
	}

	@Test public void satisfyAll() {
		final FilterChain<Object> chain = new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL);
		chain.add(Filter.DENY_ALL);
		chain.add(Filter.ALLOW_ALL);
		final Filter<Object> filter = chain;
		Assert.assertFalse(filter.allows(OBJ));
	}

}
