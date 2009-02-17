package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

public final class MimeDetectorSentinelWrapper extends AbstractSentinelWrapper implements MimeDetector {

	private final MimeDetector detector;

	public MimeDetectorSentinelWrapper(final MimeDetector detector) {
		this(detector, MimeUtils.GLOBAL_DEFAULT_MIME_TYPE);
	}

	public MimeDetectorSentinelWrapper(final MimeDetector detector, final MimeType defaultType) {
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

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	@Override public Option<MimeType> detect(final ByteContent content) {
		return ensureDefault(detector.detect(content));
	}

}
