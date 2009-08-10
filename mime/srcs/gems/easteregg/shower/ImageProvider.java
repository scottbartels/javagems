package gems.easteregg.shower;

import gems.ObjectProvider;
import gems.Option;

import java.awt.*;

final class ImageProvider implements ObjectProvider<IdentifiableImage, String> {

	public Option<IdentifiableImage> provide(final Option<String> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (key.hasValue()) {
			final String path = key.getValue();
			return new Option<IdentifiableImage>(new IdentifiableImage(path, loadImage(path)));
		}
		return new Option<IdentifiableImage>(null);
	}

	private static Image loadImage(final String path) {
		final Image image = Toolkit.getDefaultToolkit().getImage(path);
		// TODO: RESIZE IMAGE TO FIT IT INTO A SCREEN
//		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//		if (image.getHeight(null) > screen.getHeight() || image.getWidth(null) > screen.getWidth()) {
//			return image.getScaledInstance((int)screen.getWidth(), (int)screen.getHeight(), Image.SCALE_DEFAULT);
//		}
		return image;
	}
}
