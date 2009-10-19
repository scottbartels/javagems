package gems.easteregg.shower;

import gems.AbstractIdentifiable;

import java.awt.*;

final class IdentifiableImage extends AbstractIdentifiable<String> {

	private final Image image;

	IdentifiableImage(final String path, final Image image) {
		super(path);
		if (image == null) {
			throw new IllegalArgumentException();
		}
		this.image = image;
	}

	Image getImage() {
		return image;
	}

}
