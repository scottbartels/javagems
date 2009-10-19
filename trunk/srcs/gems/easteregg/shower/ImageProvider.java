package gems.easteregg.shower;

import gems.ObjectProvider;
import gems.Option;
import gems.io.RuntimeIOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

final class ImageProvider implements ObjectProvider<IdentifiableImage, String> {

	public Option<IdentifiableImage> provide(final Option<String> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (key.hasValue()) {
			final String path = key.getValue();
			return new Option<IdentifiableImage>(new IdentifiableImage(path, loadImage(new File(path))));
		}
		return new Option<IdentifiableImage>(null);
	}

	private static Image loadImage(final File file) {
		try {
			final Image image = waitForLoading(ImageIO.read(file)); // todo: waitForLoad() is not necessary here
			// TODO: NOT SCALING WELL; WE NEED TO KEEP RATIO
			final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			if (image.getHeight(null) > screen.getHeight() || image.getWidth(null) > screen.getWidth()) {
				final Image result = image.getScaledInstance((int) screen.getWidth(), (int) screen.getHeight(), Image.SCALE_FAST);
				image.flush(); // TODO: TO DO OR NOT TO DO?
				return waitForLoading(result);
			}
			return image;
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	private static Image waitForLoading(final Image image) {
		while (image.getHeight(null) < 0) {
			// todo: do somehow better; this is waiting for image loading
			Thread.yield();
		}
		return image;
	}
}
