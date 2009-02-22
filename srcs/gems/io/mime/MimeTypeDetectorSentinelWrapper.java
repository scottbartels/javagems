package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

public final class MimeTypeDetectorSentinelWrapper<T> extends AbstractMimeTypeDetectorSentinelWrapper implements MimeTypeDetector<T> {

	private final MimeTypeDetector<T> detector;

	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector detector) {
		this(detector, MimeUtils.GLOBAL_DEFAULT_MIME_TYPE);
	}

	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector<T> detector, final MimeType defaultType) {
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
