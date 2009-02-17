package gems.io.mime;

import gems.io.ByteContent;

final class MimeUtils {

	static final MimeType GLOBAL_DEFAULT_MIME_TYPE = new MimeType();

	static ByteContent context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		return ByteContent.EMPTY_CONTENT;
	}

}
