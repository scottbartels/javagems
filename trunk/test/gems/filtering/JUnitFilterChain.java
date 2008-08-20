package gems.filtering;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitFilterChain {

	private static final Object OBJ = new Object();

	@Test public void emptyReturnsFalse() {
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ALL).allows(OBJ));
		Assert.assertFalse(new FilterChain<Object>(FilterChainingPolicy.SATISFY_ANY).allows(OBJ));
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
