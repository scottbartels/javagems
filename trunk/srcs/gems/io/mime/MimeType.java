package gems.io.mime;

import gems.AbstractIdentifiable;
import gems.Option;
import gems.caching.Cache;

@Deprecated public final class MimeType extends AbstractIdentifiable<String> {

	static final MimeType DEFAULT_MIME_TYPE = getMimeType("application/octet-stream");

	private static final Cache<MimeType, String> cache = new CacheImpl();

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

	private static class CacheImpl implements Cache<MimeType, String> { // todo: change to CacheAlways
		@Override public void offer(MimeType object) {

		}

		@Override public Option<MimeType> get(String id) {
			return null;
		}
	}
	
}
