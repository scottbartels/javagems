package gems.io.mime;

import gems.io.ByteContent;
import gems.Option;

public interface ContentMimeTypeDetector {

	Option<MimeType> detect(ByteContent content);

}
