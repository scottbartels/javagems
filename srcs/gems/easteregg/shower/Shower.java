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

	/**
	 * A name of the application.
	 */
	private static final String APPLICATION_NAME = "GEMS Shower";

	/**
	 * A label component displaying an image.
	 */
	private final JLabel image = new JLabel();

	{
		image.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * A scroll pane for viewing oversized images.
	 */
	private final JScrollPane scroll = new JScrollPane(image,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	{
		scroll.getViewport().setBackground(Color.BLACK);
	}

	/**
	 * The application frame.
	 */
	private final JFrame frame = new JFrame(APPLICATION_NAME);

	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.addKeyListener(new ExitOnEscapeKeyListener());
		frame.addKeyListener(new ChangeImageListener());
		frame.addKeyListener(new SlideshowControlKeyListener());
		frame.addKeyListener(new MoveOversizedListener());
		frame.add(scroll);
	}

	/**
	 * A list of images to display.
	 */
	private final File[] images;

	/**
	 * A current target.
	 */
	private int target;

	private Shower(final File[] images, final int target) {
		this.images = images.clone();
		this.target = target;
		show();
	}

	private void show() {
		final String path = images[target].getPath();
		image.setIcon(new ImageIcon(path)); // TODO: RESIZE IMAGE TO FIT IT INTO A SCREEN
		frame.setTitle(APPLICATION_NAME + " - " + path);
		if (!frame.isVisible()) {
			frame.setVisible(true);
		}
		resetScrollbars();
	}

	private void resetScrollbars() {
		centerScrollbar(scroll.getVerticalScrollBar());
		centerScrollbar(scroll.getHorizontalScrollBar());
	}

	private static void centerScrollbar(final JScrollBar bar) {
		final int min = bar.getMinimum();
		final int max = bar.getMaximum();
		final int ext = bar.getModel().getExtent();
		bar.setValue((min + (max - ext)) / 2);
	}

	private synchronized void showFirstImage() {
		target = 0;
		show();
	}

	private synchronized void showLastImage() {
		target = images.length - 1;
		show();
	}

	private synchronized void showNextImage() {
		if (++target >= images.length) {
			target = 0;
		}
		show();
	}

	private synchronized void showPreviousImage() {
		if (--target < 0) {
			target = images.length - 1;
		}
		show();
	}

	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			return;
		}

		final File givenPath = new File(args[0]);

		if (!givenPath.exists() || !givenPath.canRead()) {
			return;
		}

		final File targetDirectory;
		if (givenPath.isDirectory()) {
			targetDirectory = givenPath;
		} else {
			final File auxParent = givenPath.getParentFile();
			targetDirectory = auxParent != null ? auxParent : new File(System.getProperty("user.dir"));
		}

		final File[] images = getFilenames(targetDirectory);

		if (images == null || images.length == 0) {
			return;
		}

		Arrays.sort(images, new FilenamesComparator());

		final File targetImage;
		if (givenPath.isDirectory()) {
			targetImage = images[0];
		} else {
			targetImage = givenPath;
		}

		final int idx = seachForTarget(images, targetImage);

		if (idx < 0) {
			return;
		}

		new Shower(images, idx);
	}

	private static int seachForTarget(File[] images, File target) {
		for (int i = 0; i < images.length; i++) {
			if (images[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	private static File[] getFilenames(final File directory) {
		return directory.listFiles(new FilenameFilter() {
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
	private final class MoveOversizedListener extends AbstractKeyListener {

		/**
		 * Handles arrow keys and moves oversized image in a scroll pane.
		 * {@code Shift} and {@code Ctrl} modifiers increase scrolling speed 2 and 3 times, respectivelly.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) { // todo: maybe also vi-like moving?
			final int code = e.getKeyCode();
			final JScrollBar bar;
			switch (code) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_LEFT:
					bar = scroll.getHorizontalScrollBar();
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
					bar = scroll.getVerticalScrollBar();
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
	private final class ChangeImageListener extends AbstractKeyListener {

		/**
		 * Goes to previous or next picture if page-up or page-down keys were pressed, respectivelly.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_PAGE_DOWN:
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_N:
					showNextImage();
					break;
				case KeyEvent.VK_PAGE_UP:
				case KeyEvent.VK_BACK_SPACE:
				case KeyEvent.VK_B:
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
	 * Starts and stops a slideshow.
	 */
	private static final class SlideshowControlKeyListener extends AbstractKeyListener {
		public void keyPressed(final KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_S) {
				// TODO: CONTINUE HERE
			}
		}
	}

	/**
	 * Exits the application when {@code ESC} is pressed.
	 */
	private static final class ExitOnEscapeKeyListener extends AbstractKeyListener {

		/**
		 * Exits the application if {@code ESC} key was pressed.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_Q) {
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
