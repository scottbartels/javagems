package gems.io.mime;

import gems.io.ByteContent;

@Deprecated final class MimeUtils {

	// TODO: THIS MIGHT NOT BE HERE. BUT: WHERE? PROBABLY IN MimeType. 
	static final MimeType GLOBAL_DEFAULT_MIME_TYPE = MimeType.getMimeType("application/octet-stream");

	static ByteContent context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		// TODO: THIS OPERATION IS QUITE SENSITIVE. THERE MIGHT BE MORE REASONABLE STRATEGIES HOW TO DO THIS, INCLUDING NONE.
		return ByteContent.EMPTY_CONTENT; // TODO: RETURN Option<ByteContent>
	}

}
