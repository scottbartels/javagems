package gems.io.mime;

import gems.Option;
import gems.io.ByteContent;

/**
 * If a context-based MIME type detection is requested, tries to determine the type
 * as well as possible. It means that context analysis is performed only if content
 * analysis does not give any result. Please note that if a content-based MIME type
 * detection is requested, only the content is analyzed.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SmartMimeTypeDetector extends AbstractMimeTypeDetector<String> {

	/**
	 * A context-based detector.
	 */
	private final ContextMimeTypeDetector<? super String> detector;

	public SmartMimeTypeDetector(final ContentMimeTypeDetector contentDetector,
								 final ContextMimeTypeDetector<? super String> contextDetector,
								 final Context2Content<? super String> c2c) {
		super(contentDetector, c2c);
		if (contextDetector == null) {
			throw new IllegalArgumentException();
		}
		detector = contextDetector;

	}

	/**
	 * For a given context, tries to acquire its content and analyze it first.
	 * If this fails, uses a context analysis as a fallback option.  Please note
	 * that the return value is encapsulated in a {@code Option} object. Client
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
		final Option<ByteContent> content = c2c(context);
		if (content.hasValue()) {
			final Option<MimeType> type = detect(content.getValue());
			if (type.hasValue()) {
				return new Option<MimeType>(type.getValue());
			}
		}
		return detector.detect(context);
	}

}
