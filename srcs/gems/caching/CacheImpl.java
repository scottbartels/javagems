package gems.caching;

import gems.Identifiable;
import gems.filtering.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CacheImpl<O extends Identifiable<K>, K> implements Cache<O, K> {

	private final List<CacheFragment<O, K>> fragments = new ArrayList<CacheFragment<O, K>>();

	private final Filter<O> filter;

	private final CacheFragmenter<K> fragmenter;

	public CacheImpl(final CacheFragmenter<K> fragmenter, final Filter<O> filter) {
		if (fragmenter == null) {
			throw new IllegalArgumentException();
		}
		if (filter == null) {
			throw new IllegalArgumentException();
		}
		this.fragmenter = fragmenter;
		for (int i = 0; i < fragmenter.fragments(); i++) {
			fragments.add(new CacheFragment<O, K>());
		}
		this.filter = filter;
	}

	public void offer(final O object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		if (filter.allows(object)) {
			fragments.get(fragmenter.getFragment(object.getId())).put(object);
		}
	}

	public O get(final K id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return fragments.get(fragmenter.getFragment(id)).get(id);
	}

}
