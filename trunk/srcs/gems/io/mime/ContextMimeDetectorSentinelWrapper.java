package gems.io.mime;

import gems.Option;

@Deprecated public final class ContextMimeDetectorSentinelWrapper<T> extends AbstractSentinelWrapper implements ContextMimeTypeDetector<T> {

	private final ContextMimeTypeDetector<? super T> detector;

	public ContextMimeDetectorSentinelWrapper(final ContextMimeTypeDetector<T> detector) {
		this(detector, MimeType.DEFAULT_MIME_TYPE);
	}

	public ContextMimeDetectorSentinelWrapper(final ContextMimeTypeDetector<? super T> detector, final MimeType defaultType) {
		super(defaultType);
		if (detector == null) {
			throw new IllegalArgumentException();
		}
		this.detector = detector;
	}

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	@Override public Option<MimeType> detect(final T context) {
		return ensureDefault(detector.detect(context));
	}

}