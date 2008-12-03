package gems.caching;

import gems.Identifiable;

abstract class AbstractLimitedCache<O extends Identifiable<K>, K> implements Cache<O, K> {

	private final CacheLimits limits;

	AbstractLimitedCache(final CacheLimits limits) {
		if (limits == null) {
			throw new IllegalArgumentException();
		}
		this.limits = limits;
	}

	protected CacheLimits getLimits() {
		return limits;
	}

}
