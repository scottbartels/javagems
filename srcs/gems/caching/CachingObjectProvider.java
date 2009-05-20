package gems.caching;

import gems.Identifiable;
import gems.ObjectProvider;
import gems.Option;
import gems.filtering.Filter;

/**
 * A service providing objects and caching them. It implements quite common use
 * case when client code wants to get an object from the cache or retrieve a new
 * one, if the cache does not provide it. This is a simple composition of a cache
 * and an object provider; client code have to provide the both of them.
 * Additionally, two filters can be provided. The both of them approves caching of
 * a newly created object: the first one filters object according their keys, the
 * second one filters objects themselves. If there is only one of them reasonable,
 * simply use {@code gems.filtering.Filter.ALLOW_ALL} for the resting one.
 * Even if this filtering can be always done by analysis of those {@code Identifiable}
 * objects, because keys are accessible through {@code getId()} method, using
 * a keys-based filter provides performance improvement: if a particular key is not
 * allowed to be cached, it is not searched in the cache at all and it is
 * created immediatelly via the underaying object provider. Please note that
 * the both filter are recommended to be idempotempt, i.e. providing the same
 * result for the same key/object when invoked for it many times. If you are about
 * implementing non-idempotent filters, consult source codes of this class and
 * consider possible consequences.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> type of provided objects.
 * @param <K> type of object keys.
 */
public final class CachingObjectProvider<V extends Identifiable<K>, K> implements ObjectProvider<V, K> {

	/**
	 * An underlaying cache.
	 */
	private final Cache<V, K> cache;

	/**
	 * An underlaying object provider.
	 */
	private final ObjectProvider<V, K> provider;

	/**
	 * A caching-acceptance filter based on object keys analysis.
	 */
	private final Filter<? super K> keyFilter;

	/**
	 * A caching-acceptance filter based on object analysis.
	 */
	private final Filter<? super V> valueFilter;

	/**
	 * Creates a new caching object provider using given attributes. All created objects are accepted for caching.
	 *
	 * @param cache an uderlaying cache used for caching objects.
	 * @param provider an uderlaying object provider used for creating new objects.
	 *
	 * @throws IllegalArgumentException if any of attributes is {@code null}.
	 */
	public CachingObjectProvider(final Cache<V, K> cache, final ObjectProvider<V, K> provider) {
		this(cache, provider, Filter.ALLOW_ALL, Filter.ALLOW_ALL);

	}

	/**
	 * Creates a new caching object provider using given attributes.
	 *
	 * @param cache an uderlaying cache used for caching objects.
	 * @param provider an uderlaying object provider used for creating new objects.
	 * @param keyFilter a caching-acceptance filter based on object keys analysis.
	 * @param valueFilter a caching-acceptance filter based on objects analysis.
	 *
	 * @throws IllegalArgumentException if any of attributes is {@code null}.
	 */
	public CachingObjectProvider(
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

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if {@code key} is {@code null}.
	 */
	@Override public Option<V> get(final Option<K> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (key.hasValue()) {
			if (keyFilter.allows(key.getValue())) {
				final Option<V> cached = cache.get(key);
				if (cached.hasValue()) {
					return new Option<V>(cached.getValue());
				}
			}
			final Option<V> provided = provider.get(key);
			if (provided.hasValue()) {
				final V value = provided.getValue();
				if (keyFilter.allows(key.getValue()) && valueFilter.allows(value)) {
					cache.offer(value);
				}
				return new Option<V>(value);

			}
		}
		return new Option<V>(null);
	}

}
