package gems.collections;

import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;
import java.util.RandomAccess;
import java.io.Serializable;

public final class FSBackedList<E extends Serializable> extends AbstractList<E> implements RandomAccess {

	private final List<Head<E>> heads = new ArrayList<Head<E>>();

	public int size() {
		return heads.size();
	}

	public E get(final int index) {
		return heads.get(index).getData();
	}

	private static final class Head<E extends Serializable> {

		Head(final E e) {
			if (e == null) {
				throw new IllegalArgumentException();
			}
		}

		E getData() {
			return null;
		}

	}

}
