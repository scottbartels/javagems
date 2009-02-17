package gems.io.mime;

import gems.io.ByteContent;
import gems.Option;

public interface ContentMimeDetector {

	Option<MimeType> detect(ByteContent content);

}
