package gems.io.mime;

import gems.Option;

public final class ContextMimeDetectorSentinelWrapper extends AbstractSentinelWrapper implements ContextMimeDetector {

	private final ContextMimeDetector detector;

	public ContextMimeDetectorSentinelWrapper(final ContextMimeDetector detector) {
		this(detector, MimeUtils.GLOBAL_DEFAULT_MIME_TYPE);
	}

	public ContextMimeDetectorSentinelWrapper(final ContextMimeDetector detector, final MimeType defaultType) {
		super(defaultType);
		if (detector == null) {
			throw new IllegalArgumentException();
		}
		this.detector = detector;
	}

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	@Override public Option<MimeType> detect(final String context) {
		return ensureDefault(detector.detect(context));
	}

}