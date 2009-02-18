package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

abstract class AbstractMimeTypeDetector implements MimeTypeDetector {
	
	private final ContentMimeTypeDetector detector = new MagicMimeTypeDetector();

	@Override public final Option<MimeType> detect(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		return detector.detect(content);
	}
	
}
