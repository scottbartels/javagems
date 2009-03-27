package gems;

import java.util.EnumMap;

public final class StaticLimits<E extends Enum<E>> implements Limits<E> {

	private final EnumMap<E, Number> store;

	public StaticLimits(final Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		store = new EnumMap<E, Number>(type);
	}

	public void setLimit(final E e, final Number value) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (value == null) {
			throw new IllegalArgumentException();
		}
		store.put(e, value);
	}

	public Number getLimit(final E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (store.containsKey(e)) {
			return store.get(e);
		}
		return 0;
	}

}
