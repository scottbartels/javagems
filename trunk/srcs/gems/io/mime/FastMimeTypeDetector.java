package gems.io.mime;

import gems.Option;

/**
 * If a context-based MIME type detection is requested, tries to determine the type
 * as fast as possible. It means that content analysis is performed only if context
 * analysis does not give any result. Please note that if a content-based MIME type
 * detection is requested, the content is always analyzed.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FastMimeTypeDetector extends AbstractMimeTypeDetector {

	/**
	 * A context-based detector.
	 */
	private final ContextMimeTypeDetector detector = new ExtensionMimeTypeDetector();

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
		return detect(MimeUtils.context2content(context));
	}

}
