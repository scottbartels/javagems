package gems.io.mime.mimetype;

import gems.Option;
import gems.io.ByteContent;

/**
 * Provides a wrapper ensuring a default MIME type usage when a wrapped detector is not able to determine it.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class MimeTypeDetectorSentinelWrapper<T> extends AbstractSentinelWrapper implements MimeTypeDetector<T> {

	/**
	 * An uderlaying detector.
	 */
	private final MimeTypeDetector<? super T> detector;

	/**
	 * Creates a new sentinel wrapper around a given detector.
	 *
	 * @param detector a wrapped detector.
	 *
	 * @throws IllegalArgumentException if {@code detector} is {@code null}.
	 */
	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector<? super T> detector) {
		this(detector, MimeType.DEFAULT_MIME_TYPE);
	}

	/**
	 * Creates a new sentinel wrapper around a given detector using {@code defaultType}
	 * as a sentinel value when the wrapped detector is not able to determine MIME type.
	 *
	 * @param detector a wrapped detector.
	 * @param defaultType a default type.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public MimeTypeDetectorSentinelWrapper(final MimeTypeDetector<? super T> detector, final MimeType defaultType) {
		super(defaultType);
		if (detector == null) {
			throw new IllegalArgumentException();
		}
		this.detector = detector;
	}

	/**
	 * Analyses given context by the underlaying detector and ensures
	 * a default value if it does not detect any MIME type.
	 *
	 * @param context analysed content.
	 * @return a non-empty Option with MIME Type.
	 */
	@Override public Option<MimeType> detect(final T context) {
		// Do not test for null here; underlaying detector may be designed to handle such input
		return ensureCheckedDefault(detector.detect(context));
	}

	/**
	 * Analyses given content by the underlaying detector and ensures
	 * a default value if it does not detect any MIME type.
	 *
	 * @param content analysed content.
	 * @return a non-empty Option with MIME Type.
	 */
	@Override public Option<MimeType> detect(final ByteContent content) {
		// Do not test for null here; underlaying detector may be designed to handle such input
		return ensureCheckedDefault(detector.detect(content));
	}

}
