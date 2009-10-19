package gems.collections;

import java.io.Serializable;
import java.util.Iterator;

final class PersistentCollectionIterator<E extends Serializable> implements Iterator<E> {

	private final Iterator<Head<E>> heads;

	PersistentCollectionIterator(final Iterator<Head<E>> heads) {
		if (heads == null) {
			throw new IllegalArgumentException();
		}
		this.heads = heads;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public boolean hasNext() {
		return heads.hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public E next() {
		return heads.next().getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void remove() {
		heads.remove();
	}

}
