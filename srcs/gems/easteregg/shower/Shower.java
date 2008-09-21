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

	private static final String APP_NAME = "Shower";

	private static final JLabel IMAGE = new JLabel();

	static {
		IMAGE.setHorizontalAlignment(SwingConstants.CENTER);
		IMAGE.setDoubleBuffered(true);
	}

	private static final JScrollPane SCROLL = new JScrollPane(IMAGE,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	static {
		SCROLL.setDoubleBuffered(true);
		SCROLL.getViewport().setBackground(Color.BLACK);
	}

	private static final JFrame FRAME = new JFrame(APP_NAME);

	static {
		FRAME.add(SCROLL);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setUndecorated(true);
		FRAME.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		FRAME.setVisible(true);
		FRAME.addKeyListener(new ChangeImageListener());
		FRAME.addKeyListener(new MoveOversizedListener());
	}

	private static File[] files;

	private static int index;

	public static void main(final String[] args) throws Exception {
		files = getFilenames(args[0]);
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		if (files.length > 0) {
			showFile(files[0]);
		}
	}

	private static void showNextFile() {
		if (files.length == 0) {
			return; // nothing to show;
		}
		if (++index >= files.length) {
			index = 0; // rewind
		}
		showFile(files[index]);
	}

	private static void showFile(File file) {
		IMAGE.setIcon(new ImageIcon(file.getPath()));
		FRAME.setTitle(APP_NAME + " - " + file.getPath());
		resetScrollbars();
	}

	private static void resetScrollbars() {
		centerScrollbar(SCROLL.getHorizontalScrollBar());
		centerScrollbar(SCROLL.getVerticalScrollBar());
	}

	private static void centerScrollbar(final JScrollBar bar) {
		final int min = bar.getMinimum();
		final int max = bar.getMaximum();
		final int ext = bar.getModel().getExtent();
		bar.setValue((min + (max - ext)) / 2);
	}

	private static void showPreviousFile() {
		if (files.length == 0) {
			return; // nothing to do
		}
		if (--index < 0) {
			index = files.length - 1;
		}
		showFile(files[index]);
	}

	private static File[] getFilenames(final String directory) {
		return new File(directory).listFiles(new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".png") || name.endsWith(".jpg");
			}
		});
	}

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

	private static final class MoveOversizedListener extends AbstractKeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
				final JScrollBar bar = SCROLL.getHorizontalScrollBar();
				final int current = bar.getValue();
				final int extent = (int) (bar.getModel().getExtent() * 0.382);
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					bar.setValue(current + extent);
				} else {
					bar.setValue(current - extent);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
				final JScrollBar bar = SCROLL.getVerticalScrollBar();
				final int current = bar.getValue();
				final int extent = (int) (bar.getModel().getExtent() * 0.382);
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					bar.setValue(current - extent);
				} else {
					bar.setValue(current + extent);
				}
			}
		}

	}

	/**
	 * Moves up and down in list of images when up and down arrows are pressed. 
	 */
	private static final class ChangeImageListener extends AbstractKeyListener {

		/**
		 * Goes to previous or next picture if up or down keys were pressed, respectivelly.
		 *
		 * @param e a key event.
		 */
		public void keyPressed(final KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				showPreviousFile();
			} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
				showNextFile();
			}
		}

	}

}
