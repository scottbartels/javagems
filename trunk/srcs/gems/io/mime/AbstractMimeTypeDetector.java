package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

/**
 * A common part of composite MIME type detectors. It holds a content-based detector and use it for a content analysis.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractMimeTypeDetector implements MimeTypeDetector {

	/**
	 * A content-based detector.
	 */
	private final ContentMimeTypeDetector detector = new MagicMimeTypeDetector();

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
	
}
