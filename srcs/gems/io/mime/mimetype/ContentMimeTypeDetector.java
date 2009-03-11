package gems.io.mime.mimetype;

import gems.io.ByteContent;
import gems.Option;

/**
 * A detector of MIME type performing an analysis of a content.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a> 
 */
public interface ContentMimeTypeDetector {

	/**
	 * Analyses given content and tries to determine its MIME type.
	 * If it cannot determine a MIME type, returned option may not
	 * contain a value.
	 *
	 * @param content analysed content.
	 * @return a determined MIME type.
	 */
	Option<MimeType> detect(ByteContent content);

}
