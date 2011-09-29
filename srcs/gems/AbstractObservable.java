package gems;

import javax.swing.*;
import java.util.Observable;

/**
 * Just a syntactic sugar class containing core repeating in all {@code Observable} implementations.
 *
 * @author Jozef BABJAK
 */
public abstract class AbstractObservable extends Observable {

	/**
	 * Sets the changed status and alerts observers. Implementation
	 * ensures that all observers are notified by Event Dispatcher
	 * Thread.
	 */
	protected final void alert() {
		setChanged();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers();
			}
		});
	}

}
