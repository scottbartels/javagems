package gems.io.mime;

import gems.io.ByteContent;

@Deprecated final class MimeUtils {

	static ByteContent context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		// TODO: THIS OPERATION IS QUITE SENSITIVE. THERE MIGHT BE MORE REASONABLE STRATEGIES HOW TO DO THIS, INCLUDING NONE.
		return ByteContent.EMPTY_CONTENT; // TODO: RETURN Option<ByteContent>
	}

}
