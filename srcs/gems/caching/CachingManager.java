package gems.caching;

import gems.Identifiable;
import gems.Option;
import gems.filtering.Filter;

public final class CachingManager<V extends Identifiable<K>, K> {

	public interface ObjectProvider<V, K> { // todo: seems pretty common
		Option<V> provide(K key);
	}

	private final Cache<V, K> cache;

	private final ObjectProvider<V, K> provider;

	private final Filter<? super K> keyFilter;

	private final Filter<? super V> valueFilter;

	public CachingManager(final Cache<V, K> cache, final ObjectProvider<V, K> provider) {
		this(cache, provider, Filter.ALLOW_ALL, Filter.ALLOW_ALL);

	}

	public CachingManager(
			final Cache<V, K> cache,
			final ObjectProvider<V, K> provider,
			final Filter<? super K> keyFilter,
			final Filter<? super V> valueFilter
	) {
		if (cache == null) {
			throw new IllegalArgumentException();
		}
		if (provider == null) {
			throw new IllegalArgumentException();
		}
		this.cache = cache;
		this.provider = provider;
		this.keyFilter = keyFilter;
		this.valueFilter = valueFilter;
	}

	public Option<V> get(final K id) {
		if (keyFilter.allows(id)) {
			final Option<V> cached = cache.get(id);
			if (cached.hasValue()) {
				return new Option<V>(cached.getValue());
			}
		}
		final Option<V> provided = provider.provide(id);
		if (provided.hasValue()) {
			final V value = provided.getValue();
			if (keyFilter.allows(id) && valueFilter.allows(value)) {
				cache.offer(value);
			}
			return new Option<V>(value);
			
		}
		return new Option<V>(null);
	}
	
}
