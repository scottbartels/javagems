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
	private final ContentMimeTypeDetector detector = new MagicMimeTypeDetector();

	private final Context2Content<T> c2c;

	protected AbstractMimeTypeDetector(final Context2Content<T> c2c) {
		if (c2c == null) {
			throw new IllegalArgumentException();
		}
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

	protected final Context2Content<T> getC2C() {
		return c2c;
	}
	
}
