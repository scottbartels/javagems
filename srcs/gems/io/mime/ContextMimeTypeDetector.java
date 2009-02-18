package gems.io.mime;

import gems.Option;

public interface ContextMimeTypeDetector {

	Option<MimeType> detect(String context);

}
