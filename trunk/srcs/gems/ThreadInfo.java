package gems;

/**
 * Encapsulation of thread information. It is immutable after creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ThreadInfo extends AbstractIdentifiable<Long> {

	/**
	 * A thread name.
	 */
	private final String name;

	/**
	 * Creates a new thread info object.
	 */
	public ThreadInfo() {
		super(Thread.currentThread().getId());
		name = Thread.currentThread().getName();
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
