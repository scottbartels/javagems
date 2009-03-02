package gems.io.mime;

import gems.io.ByteContent;
import gems.Option;

/**
 * Converts a context of type {@code T} to a byte content. Because the
 * conversion is quite sensitive operation which may not be possible at
 * all, resulting byt content is encapsulated in {@code Option} wrapper
 * forcing client code to check a content presence.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of context
 */
public interface Context2Content<T> {

	/**
	 * A null-implementation of the interface. The conversion operation is
	 * always unsuccessful, i.e. an empty {@code Option} is always returned.
	 */
	Context2Content<Object> NULL_CONVERTOR = new Context2Content<Object>() {

		/**
		 * Always returns an empty {@code Option} for any non-null context.
		 *
		 * @param context converted context.
		 * @return an empty {@code Option}, always.
		 *
		 * @throws IllegalArgumentException if {@code context} is {@code null}. 
		 */
		@Override public Option<ByteContent> context2content(final Object context) {
			if (context == null) {
				throw new IllegalArgumentException();
			}
			return new Option<ByteContent>(null);
		}
	};

	/**
	 * Converts given context to byte content, if possible.
	 *
	 * @param context converted context.
	 * @return byte content encapsulated in {@code Option} wrapper. 
	 */
	Option<ByteContent> context2content(T context);

}
