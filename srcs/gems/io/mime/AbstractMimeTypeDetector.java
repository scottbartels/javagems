package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

/**
 * A common part of composite MIME type detectors. It holds a content-based detector and use it for a content analysis.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractMimeTypeDetector<T> implements MimeTypeDetector<T> {

	/**
	 * A content-based detector.
	 */
	private final ContentMimeTypeDetector detector;

	/**
	 * A coverter converting context to content.
	 */
	private final Context2Content<? super T> c2c;

	/**
	 * Creates a new abstract detector using given detector for content analysis
	 * and given converter for context-to-content transformation.
	 *
	 * @param detector a content-based detector.
	 * @param c2c a context-to-content converter.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	protected AbstractMimeTypeDetector(final ContentMimeTypeDetector detector, final Context2Content<? super T> c2c) {
		if (detector == null) {
			throw new IllegalArgumentException();
		}
		if (c2c == null) {
			throw new IllegalArgumentException();
		}
		this.detector = detector;
		this.c2c = c2c;
	}

	/**
	 * {@inheritDoc} Client code must always check a value presence before usage.
	 *
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	@Override public final Option<MimeType> detect(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		return detector.detect(content);
	}

	/**
	 * Converts given context to byt content.
	 *
	 * @param context converted context. 
	 * @return byte content encapsulated in {@code Option} wrapper.
	 */
	protected final Option<ByteContent> c2c(final T context) {
		assert context != null;
		return c2c.context2content(context);
	}

}
