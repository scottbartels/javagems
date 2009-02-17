package gems.io.mime;

import gems.Option;

public interface ContextMimeDetector {

	Option<MimeType> detect(String context);

}
