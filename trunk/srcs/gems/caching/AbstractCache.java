package gems.caching;

import gems.Option;
import gems.Identifiable;

abstract class AbstractCache<V extends Identifiable<K>, K>  implements Cache<V, K> {
	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public final Option<V> provide(final Option<K> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (!key.hasValue()) {
			return new Option<V>(null);
		}
		return retrieve(key);

	}

	protected abstract Option<V> retrieve(Option<K> key);

}
