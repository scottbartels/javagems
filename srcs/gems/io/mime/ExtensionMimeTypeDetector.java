package gems.io.mime;

import gems.Option;

import java.io.File;

/**
 * In this case, a context is considered to be a path of a file or a fileaname.
 * This detector extracts a filename extension and compares it to a database of
 * known filename extensions. The extension is considered to be ... (complete this, 
 * it is quite tricky, but it should be described here).
 * If a file separator is not given during a detector creation, a platform
 * specific default will be used instead.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@Deprecated public final class ExtensionMimeTypeDetector implements ContextMimeTypeDetector<String> {

	private static final String DOT = ".";

	private final String separator;

	// TODO: INJECT OPTIONAL FUNCTINALITY FOR LOCAL FILESYSTEM DETECTION OF DIRECTORIES.

	public ExtensionMimeTypeDetector() {
		this(File.separator);
	}

	public ExtensionMimeTypeDetector(final String separator) {
		if (separator == null) {
			throw new IllegalArgumentException();
		}
		this.separator = separator;
	}

	@Override public Option<MimeType> detect(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		if (context.isEmpty()) {
			return new Option<MimeType>(null);
		}
		if (context.endsWith(separator)) {
			return new Option<MimeType>(MimeType.getMimeType("application/directory"));
		}

		// TODO: Here are use cases:
		//                          INPUT    FILENAME            EXTENSION      WHAT TO DO      MIME
		//                             "" ->            (handle as a special case)           -> _unknown_
		//                            "/" -> _empty_          -> _unknown_   -> directory    -> application/directory
		//                          "var" -> "var"            -> "var"       -> _detect_     -> _unknown_
		//                         "/var" -> "var"            -> "var"       -> _detect_     -> _unknown_
		//                         "var/" -> _empty_          -> _unknown_   -> directory    -> application/directory
		//                         ".svn" -> "svn"            -> "svn"       -> _detect_     -> _unknown_
		//                     "Makefile" -> "Makefile"       -> "Makefile"  -> _detect_     -> text/x-makefile
		//               "Linux.Makefile" -> "Linux.Makefile" -> "Makefile"  -> _detect_     -> text/x-makefile
		//        "/usr/home/user/my.doc" -> "my.doc"         -> "doc"       -> _detect_     -> application/msword
		//       "/usr/home/user/my.doc." -> "my.doc."        -> _empty_     -> _unknown_    -> _unknown_
		//       "/usr/home/user/my.doc/" -> _empty_          -> unknown     -> directory    -> application/directory
		// "/usr/home/user/my.doc/me.png" -> "me.png"         -> "png"       -> _detect_     -> image/png

		return new Option<MimeType>(null);
	}

}
