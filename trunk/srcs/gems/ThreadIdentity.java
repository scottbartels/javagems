package gems;

/**
 * Encapsulation of thread identity information. It is immutable after creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class ThreadIdentity extends AbstractIdentifiable<Long> {

	/**
	 * A thread name.
	 */
	private final String name;

	/**
	 * Creates a new thread info object.
	 */
	public ThreadIdentity() {
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
