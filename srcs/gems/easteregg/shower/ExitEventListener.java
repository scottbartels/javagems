package gems.easteregg.shower;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Exits the application when {@code ESC} or {@code Q} is pressed.
 */
final class ExitEventListener extends AbstractKeyListener {

	/**
	 * A window to dispose when triggered.
	 */
	private final Window window;

	/**
	 * Creates a new listener disposing the given window when triggered.
	 *
	 * @param window a window to dispose.
	 *
	 * @throws IllegalArgumentException if {@code window} is {@code null}.
	 */
	ExitEventListener(final Window window) {
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.window = window;
	}

	/**
	 * Exits the application if {@code ESC} or {@code Q} key was pressed.
	 *
	 * @param e a key event.
	 */
	public void keyPressed(final KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_Q) {
			window.dispose();
		}
	}

}
