package gems.caching;

import gems.Checks;
import gems.Identifiable;
import gems.UnexpectedNullException;

/**
 * A cache component is holding an internal defense copy of cache properties and provides them for subclasses.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <V> a type of cached objects.
 * @param <K> a type of keys identifying cached objects.
 */
abstract class AbstractCacheComponent<V extends Identifiable<K>, K> {

	/**
	 * Cache properties.
	 */
	private final CacheProperties<V, K> properties;

	/**
	 * Initiates a new cache component holding a given properties for it.
	 *
	 * @param properties a cache properties.
	 *
	 * @throws UnexpectedNullException if {@code properties} is {@code null}.
	 */
	protected AbstractCacheComponent(final CacheProperties<V, K> properties) {
		this.properties = Checks.ensureNotNull(properties);
	}

	/**
	 * Returns cache properties for the cache component. This method never returns {@code null}.
	 *
	 * @return cache properties for the cache component.
	 */
	protected final CacheProperties<V, K> getProperties() {
		return properties;
	}

}
