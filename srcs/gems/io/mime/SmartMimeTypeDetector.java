package gems.io.mime;

import gems.Option;

public final class SmartMimeTypeDetector extends AbstractMimeTypeDetector {

	private final ContextMimeTypeDetector detector = new ExtensionMimeTypeDetector();

	@Override public Option<MimeType> detect(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		final Option<MimeType> type = detect(MimeUtils.context2content(context));
		if (type.hasValue()) {
			return new Option<MimeType>(type.getValue());
		}
		return detector.detect(context);
	}

}