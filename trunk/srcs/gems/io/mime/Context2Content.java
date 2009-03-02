package gems.io.mime;

import gems.io.ByteContent;
import gems.Option;

interface Context2Content<T> {

	Context2Content<Object> NULL_IMPLEMENTATION = new Context2Content<Object>() {
		@Override public Option<ByteContent> context2content(final Object context) {
			return new Option<ByteContent>(null);
		}
	};

	Option<ByteContent> context2content(T context);

}
