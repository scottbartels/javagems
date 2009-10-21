package gems;

import java.util.EnumMap;

// TODO: 1) CREATE THREAD-SAFENESS TEST; IT SHOULD FAIL
// TODO: 2) SYNCHRONIZE SOMEHOW:
// TODO: 2.1) SYNCHRONIZE PUBLIC METHODS
// TODO: 2.2) SYNCHRONIZE KEY SECTIONS ON map
// TODO: 2.3) WRAP map INTO Collections.synchronizedMap();
// TODO: 2.4) USE java.util.concurrent.locks.ReadWriteLock

public final class StaticLimits<E extends Enum<E>> implements Limits<E> {

	private final EnumMap<E, Number> map;

	public StaticLimits(final Class<E> type) {
		map = new EnumMap<E, Number>(Checks.ensureNotNull(type));
	}

	public void setLimit(final E e, final Number value) {
		map.put(Checks.ensureNotNull(e), Checks.ensureNotNull(value));
	}

	@Override public Number getLimit(final E e) {
		if (map.containsKey(Checks.ensureNotNull(e))) {
			return map.get(e);
		}
		return 0;
	}

}
