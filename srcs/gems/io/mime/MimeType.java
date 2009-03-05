package gems.io.mime;

import gems.AbstractIdentifiable;
import gems.Option;
import gems.caching.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * An object representation of MIME type.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class MimeType extends AbstractIdentifiable<String> {

	/**
	 * A default MIME type useful for an unknown type.
	 */
	static final MimeType DEFAULT_MIME_TYPE = getMimeType("application/octet-stream");

	/**
	 * Internal cache.
	 */
	private static final Cache<MimeType, String> cache = new CacheAllForever();

	/**
	 * A main type of the MIME type.
	 */
	private final String type;

	/**
	 * A subtype of the MIME type.
	 */
	private final String subtype;

	private MimeType(final String spec) {
		super(spec);
		// todo: check specification
		final String[] fragments = spec.split("/");
		type = fragments[0];
		subtype = fragments[1]; // todo: what about additional parameters?
	}

	/**
	 * Creates a MIME type given by specification {@code spec}.
	 *
	 * @param spec a MIME type specification.
	 *
	 * @return a MIME type given by specification {@code spec}.
	 * @throws IllegalArgumentException if {@code spec} is {@code null}.
	 */
	static MimeType getMimeType(final String spec) {
		if (spec == null) {
			throw new IllegalArgumentException();
		}
		final Option<MimeType> cached = cache.get(spec);
		if (cached.hasValue()) {
			return cached.getValue();
		}

		final MimeType result = new MimeType(spec);
		cache.offer(result);
		return result;
	}

	/**
	 * Returns a main type of the MIME type.
	 *
	 * @return a main type of the MIME type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns a subtype of the MIME type.
	 *
	 * @return a subtype of the MIME type.
	 */
	public String getSubtype() {
		return subtype;
	}

	@Override public String toString() {
		return getId();
	}

	@Deprecated private static final class CacheAllForever implements Cache<MimeType, String> {

		private final Map<String, MimeType> store = new HashMap<String, MimeType>();

		@Override public synchronized void offer(final MimeType object) {
			assert object != null;
			assert !store.containsKey(object.getId());
			store.put(object.getId(), object);
		}

		@Override public synchronized Option<MimeType> get(final String id) {
			assert id != null;
			return new Option<MimeType>(store.get(id));
		}
	}

}
