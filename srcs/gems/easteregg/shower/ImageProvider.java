package gems.easteregg.shower;

import gems.ObjectProvider;
import gems.Option;

final class ImageProvider implements ObjectProvider<IdentifiableImage, String> {

	public Option<IdentifiableImage> provide(final Option<String> key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		if (key.hasValue()) {
			final String path = key.getValue();
			return new Option<IdentifiableImage>(new IdentifiableImage(path, Shower.loadImage(path)));
		}
		return new Option<IdentifiableImage>(null);
	}

}
