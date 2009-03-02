package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

/**
 * If a context-based MIME type detection is requested, tries to determine the type
 * as fast as possible. It means that content analysis is performed only if context
 * analysis does not give any result. Please note that if a content-based MIME type
 * detection is requested, the content is always analyzed.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FastMimeTypeDetector extends AbstractMimeTypeDetector<String> {

	/**
	 * A context-based detector.
	 */
	private final ContextMimeTypeDetector<? super String> detector;

	public FastMimeTypeDetector() {
		this(new MagicMimeTypeDetector(), new ExtensionMimeTypeDetector(), new LocalFilesystemContext2Content());
	}

	public FastMimeTypeDetector(final ContentMimeTypeDetector contentDetector,
								   final ContextMimeTypeDetector<? super String> contextDetector,
								   final Context2Content<? super String> c2c) {
		super(contentDetector, c2c);
		if (contextDetector == null) {
			throw new IllegalArgumentException();
		}
		detector = contextDetector;
	}

	/**
	 * Analyses a givne context and tries to determine its MIME type. If this fails,
	 * tries to acquire a content for a given context and analyzes the content. Please
	 * note that the return value is encapsulated in a {@code Option} object. Client
	 * code must always check a value presence before usage.
	 *
	 * @param context analysed context.
	 * @return a determined MIME type.
	 *
	 * @throws IllegalArgumentException if {@code context} is {@code null}. 
	 */
	@Override public Option<MimeType> detect(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		final Option<MimeType> type = detector.detect(context);
		if (type.hasValue()) {
			return new Option<MimeType>(type.getValue());
		}
		final Option<ByteContent> content = c2c(context);
		if (content.hasValue()) {
			return detect(content.getValue());
		}
		return new Option<MimeType>(null);
	}

}
