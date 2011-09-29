package gems;

/**
 * A null-implementation of {@code Runnable} interface. It simply does nothing.
 *
 * @author Jozef BABJAK
 */
public final class DoNothing implements Runnable {

	/**
	 * The singleton instance of the interface. There is no reason to have more than one instance.
	 */
	public static final Runnable INSTANCE = new DoNothing();

	/**
	 * Just disables an instance creation outside the class.
	 */
	public DoNothing() {
		// really nothing here
	}

	/**
	 * Does nothing, returns immediately.
	 */
	@Override public void run() {
		// really nothing here
	}

}
