package gems;

import gems.filtering.Condition;
import gems.io.RuntimeIOException;

import java.io.IOException;

/**
 * Launcher for separate Java application.
 *
 * @author Jozef BABJAK
 */
public final class JavaApplicationLauncher implements Runnable {

	/**
	 * A class containing {@code main()} method to be launched.
	 */
	private final Class main;

	/**
	 * A condition which needs to be satisfied to launch application.
	 */
	private final Condition condition;

	/**
	 * What to do if pre-launch condition is not satisfied.
	 */
	private final Runnable handler;

	/**
	 * Creates a new launcher.
	 *
	 * @param main a class containing {@code main()} method to be launched.
	 *
	 * @throws gems.UnexpectedNullException if any of arguments is {@code null}.
	 */
	public JavaApplicationLauncher(final Class main) {
		this(main, Condition.ALWAYS_SATISFIED);
	}

	/**
	 * Creates a new launcher.
	 *
	 * @param main a class containing {@code main()} method to be launched.
	 * @param condition a condition which needs to be satisfied to launch application.
	 *
	 * @throws gems.UnexpectedNullException if any of arguments is {@code null}.
	 */
	public JavaApplicationLauncher(final Class main, final Condition condition) {
		this(main, condition, DoNothing.INSTANCE);
	}

	/**
	 * Creates a new launcher.
	 *
	 * @param main a class containing {@code main()} method to be launched.
	 * @param condition a condition which needs to be satisfied to launch application.
	 * @param handler executed if pre-launch condition is not satisfied.
	 *
	 * @throws gems.UnexpectedNullException if any of arguments is {@code null}.
	 */
	public JavaApplicationLauncher(final Class main, final Condition condition, final Runnable handler) {
		this.main = Checks.ensureNotNull(main);
		this.condition = Checks.ensureNotNull(condition);
		this.handler = Checks.ensureNotNull(handler);
	}

	/**
	 * Checks pre-launch condition first; if the condition is satisfied,
	 * launches the target application, otherwise executes handler code.
	 */
	@Override public void run() {
		if (condition.isSatisfied()) {
			try {
				final ProcessBuilder builder = new ProcessBuilder("java", main.getName());
				builder.redirectError(ProcessBuilder.Redirect.INHERIT);
				builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
				builder.start();
			} catch (final IOException e) {
				throw new RuntimeIOException(e);
			}
		} else {
			handler.run();
		}
	}

}
