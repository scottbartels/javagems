package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

public abstract class AbstractMimeDetector implements MimeDetector {
	
	private final ContentMimeDetector detector = new MagicMimeDetector();

	@Override public final Option<MimeType> detect(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		return detector.detect(content);
	}
	
}
