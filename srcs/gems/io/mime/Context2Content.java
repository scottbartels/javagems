package gems.io.mime;

import gems.io.ByteContent;

interface Context2Content<T> {

	ByteContent context2content(T context);

}
