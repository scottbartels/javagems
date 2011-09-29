package gems.gui;

import gems.Checks;

import javax.swing.*;
import java.awt.*;

/**
 * Syntax sugar for common view components.
 *
 * @author Jozef BABJAK
 */
public abstract class AbstractView<T extends Component> {

	/**
	 * The component itself.
	 */
	private final T view;

	/**
	 * Creates a view for the given main component.
	 *
	 * @param component the main component.
	 *
	 * @throws gems.UnexpectedNullException if {@code component} is {@code null}.
	 */
	protected AbstractView(final T component) {
		this.view = Checks.ensureNotNull(component);
	}

	/**
	 * Returns the main component of the view. This method never returns {@code null}.
	 *
	 * @return the main component of the view.
	 */
	public final T getView() {
		return view;
	}

	/**
	 * Shows the main component of the given view as the only component of application frame; for debug purpose only.
	 *
	 * @param view an instance of {@code AbstractView} to be shown.
	 */
	protected static void show(final AbstractView view) {
		final JFrame frame = new JFrame(view.getClass().getName());
		frame.add(view.getView());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
