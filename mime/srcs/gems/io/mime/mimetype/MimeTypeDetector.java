package gems.io.mime.mimetype;

/**
 * Provides an convenient union of context-based and content-based MIME type detectors.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface MimeTypeDetector<T> extends ContextMimeTypeDetector<T>, ContentMimeTypeDetector {
	// really nothing here
}
