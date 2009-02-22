package gems.io.mime;

import gems.io.ByteContent;

final class Context2ContentImpl implements Context2Content<String> {

	public ByteContent context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		return ByteContent.EMPTY_CONTENT; // TODO: Implement this.
	}

}
