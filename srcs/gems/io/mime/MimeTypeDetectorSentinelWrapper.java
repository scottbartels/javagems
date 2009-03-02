package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

@Deprecated public final class MimeTypeDetectorSentinelWrapper<T> extends AbstractSentinelWrapper implements MimeTypeDetector<T> {

	private final MimeTypeDetector<? super T> detector;

	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector<T> detector) {
		this(detector, MimeType.DEFAULT_MIME_TYPE);
	}

	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector<? super T> detector, final MimeType defaultType) {
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

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	@Override public Option<MimeType> detect(final ByteContent content) {
		return ensureDefault(detector.detect(content));
	}

}
