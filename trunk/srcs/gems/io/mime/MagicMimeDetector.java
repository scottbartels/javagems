package gems.io.mime;

import gems.io.ByteContent;
import gems.Option;

public final class MagicMimeDetector implements ContentMimeDetector {

	@Override public Option<MimeType> detect(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		return new Option<MimeType>(null);
	}
	
}
