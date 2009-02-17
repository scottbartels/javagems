package gems.io.mime;

import gems.io.ByteContent;

final class MimeUtils {

	static ByteContent context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		return ByteContent.EMPTY_CONTENT;
	}

}
