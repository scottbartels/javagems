package gems.io.mime;

import gems.Option;

public final class SmartMimeDetector extends AbstractMimeDetector {

	private final ContextMimeDetector detector = new ExtensionMimeDetector();

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
