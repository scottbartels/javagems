package gems.logging;

/**
 * Encapsulation of thread information. It is created as an internal
 * part of logging record and it is immutable after creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ThreadInfo {

	/**
	 * A thread ID.
	 */
	private final long id;

	/**
	 * A thread name.
	 */
	private final String name;

	/**
	 * Creates a new thread info object.
	 */
	ThreadInfo() {
		final Thread current = Thread.currentThread();
		id = current.getId();
		name = current.getName();
	}

	/**
	 * Returns a thread id.
	 *
	 * @return a thread id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns a thread name. This method never returns {@code null}.
	 *
	 * @return a thread name.
	 */
	public String getName() {
		return name;
	}

}
