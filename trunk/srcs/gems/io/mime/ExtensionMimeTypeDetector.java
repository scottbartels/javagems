package gems.io.mime;

import gems.Option;

public final class ExtensionMimeTypeDetector implements ContextMimeTypeDetector {

	@Override public Option<MimeType> detect(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		return new Option<MimeType>(null);
	}

}
