package gems.collections;

import java.io.Serializable;
import java.util.*;

public final class FSBackedList<E extends Serializable> implements Collection<E>, RandomAccess {

	private final List<Head<E>> heads = new ArrayList<Head<E>>();

	public int size() {
		return heads.size();
	}

	public boolean isEmpty() {
		return heads.isEmpty();
	}

	public boolean contains(final Object o) {
		for (final Head<E> head : heads) {
			if (head.getData().equals(o)) {
				return true;
			}
		}
		return false;
	}

	public Object[] toArray() {
		final Object[] result = new Object[heads.size()];
		for (int i = 0; i < heads.size(); i++) {
			result[i] = heads.get(i).getData();
		}
		return result;
	}

	public boolean add(final E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		return heads.add(new Head<E>(e));
	}

	public boolean remove(final Object o) {
		for (final Head<E> head : heads) {
			if (head.getData().equals(o)) {
				heads.remove(head);
				return true;
			}
		}
		return false;
	}

	public boolean containsAll(final Collection<?> c) {
		for (final Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public boolean addAll(final Collection<? extends E> c) {
		if (c.isEmpty()) {
			return false;
		}
		for (final E e : c) {
			heads.add(new Head<E>(e));
		}
		return true;
	}

	public boolean removeAll(final Collection<?> c) {
		boolean changed = false;
		for (final Object o : c) {
			final boolean removed = remove(o);
			if (removed) {
				changed = true;
			}
		}
		return changed;
	}

	public boolean retainAll(final Collection<?> c) {
		boolean changed = false;
		for (int i = heads.size() - 1; i >= 0; i--) {
			final Head<E> head = heads.get(i);
			if (c.contains(head.getData())) {
				heads.remove(i);
				changed = true;
			}
		}
		return changed;
	}

	public void clear() {
		heads.clear();
	}

	public E get(final int index) {
		return heads.get(index).getData();
	}

	// TODO: NOT YET IMPLEMENTED PART OF THE INTERFACE

	public Iterator<E> iterator() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public <T> T[] toArray(T[] a) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	// HEAD

	private static final class Head<E extends Serializable> {

		private final E fake;

		Head(final E e) {
			if (e == null) {
				throw new IllegalArgumentException();
			}
			fake = e;
		}

		E getData() {
			return fake;
		}

	}

}
