package gems.easteregg.shower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class Shower {

	private static final String APP_NAME = "GEMS Shower";

	private static final JLabel IMAGE = new JLabel();

	static {
		IMAGE.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private static final JScrollPane SCROLL = new JScrollPane(IMAGE,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	static {
		SCROLL.getViewport().setBackground(Color.BLACK);
	}

	private static final JFrame FRAME = new JFrame(APP_NAME);

	static {
		FRAME.add(SCROLL);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setUndecorated(true);
		FRAME.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		FRAME.addKeyListener(new ExitOnEscapeKeyListener());

		FRAME.addKeyListener(new ChangeImageListener());
		FRAME.addKeyListener(new MoveOversizedListener());
	}

	private static File[] files;

	private static int index;

	public static void main(final String[] args) throws Exception {
		files = getFilenames(args[0]);
		if (files.length == 0) {
			return;
		}
		Arrays.sort(files, new FilenamesComparator());
		showFirstImage();
	}

	private static void show() {
		final String path = files[index].getPath();
		IMAGE.setIcon(new ImageIcon(path));
		FRAME.setTitle(APP_NAME + " - " + path);
		if (!FRAME.isVisible()) {
			FRAME.setVisible(true);
		}
		resetScrollbars();
	}

	private static void resetScrollbars() {
		centerScrollbar(SCROLL.getVerticalScrollBar());
		centerScrollbar(SCROLL.getHorizontalScrollBar());
	}

	private static void centerScrollbar(final JScrollBar bar) {
		final int min = bar.getMinimum();
		final int max = bar.getMaximum();
		final int ext = bar.getModel().getExtent();
		bar.setValue((min + (max - ext)) / 2);
	}

	private static void showFirstImage() {
		index = 0;
		show();
	}

	private static void showLastImage() {
		index = files.length - 1;
		show();
	}

	private static void showNextImage() {
		if (++index >= files.length) {
			index = 0; // rewind
		}
		show();
	}

	private static void showPreviousImage() {
		if (--index < 0) {
			index = files.length - 1;
		}
		show();
	}

	private static File[] getFilenames(final String directory) {
		return new File(directory).listFiles(new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".png") || name.endsWith(".jpg");
			}
		});
	}

	/**
	 * A skeleton implementation of {@code KeyListener} interface
	 * implementing only unused methods using empty bodies.
	 */
	private static abstract class AbstractKeyListener implements KeyListener {

		/**
		 * Does nothing.
		 *
		 * @param e ignored.
		 */
		public final void keyTyped(final KeyEvent e) {
			// really nothing here
		}

		/**
		 * Does nothing.
		 *
		 * @param e ignored.
		 */
		public final void keyReleased(final KeyEvent e) {
			// really nothing here
		}

	}

	/**
	 * Moves oversized image up, down, right, and left
	 */
	private static final class MoveOversizedListener extends AbstractKeyListener {

		/**
		 * Handles arrow keys and moves oversized image in a scroll pane.
		 * {@code Shift} and {@code Ctrl} modifiers increase scrolling speed 2 and 3 times, respectivelly.
		 *
		 * @param e a key event. 
		 */
		public void keyPressed(final KeyEvent e) {
			final int code = e.getKeyCode();
			final JScrollBar bar;
			switch (code) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_LEFT:
					bar = SCROLL.getHorizontalScrollBar();
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
					bar = SCROLL.getVerticalScrollBar();
					break;
				default:
					return; // nothing to do
			}

			double step = 0.1;

			if (e.isShiftDown()) {
				step *= 2;
			}

			if (e.isControlDown()) {
				step *= 3;
			}

			final int change = (int) (step * bar.getModel().getExtent());

			switch (code) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_DOWN:
					bar.setValue(bar.getValue() + change);
					break;
				default:
					bar.setValue(bar.getValue() - change);
					break;
			}

		}

	}

	/**
	 * Moves up and down in list of images when page-up and page-down keys are pressed.
	 */
	private static final class ChangeImageListener extends AbstractKeyListener {

		/**
		 * Goes to previous or next picture if page-up or page-down keys were pressed, respectivelly.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_PAGE_DOWN:
					showNextImage();
					break;
				case KeyEvent.VK_PAGE_UP:
					showPreviousImage();
					break;
				case KeyEvent.VK_END:
					showLastImage();
					break;
				case KeyEvent.VK_HOME:
					showFirstImage();
					break;
				default:
					break;
			}

		}

	}

	/**
	 * Exits the application when {@code ESC} is pressed.
	 */
	private static class ExitOnEscapeKeyListener extends AbstractKeyListener {

		/**
		 * Exits the application if {@code ESC} key was pressed.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

	}

	/**
	 * Compares files according their names.
	 */
	private static final class FilenamesComparator implements Comparator<File> {

		/**
		 * {@inheritDoc}
		 */
		public int compare(final File x, final File y) {
			return x.getName().compareTo(y.getName());
		}

	}

}
