package gems.io.mime;

public final class MimeType {

	private static final String DELIMITER = "/";

	private final String type;

	private final String subtype;

	private MimeType(final String type, final String subtype) {
		assert type != null;
		assert subtype != null;
		this.type = type;
		this.subtype = subtype;
	}

	static MimeType getMimeType(final String spec) {
		if (spec == null) {
			throw new IllegalArgumentException();
		}
		final String[] elements = spec.split(DELIMITER); // todo: use precompiled Pattern for better performance
		if (elements.length != 2) {
			throw new IllegalArgumentException(); // todo: custom exception?
		}
		return getMimeType(elements[0], elements[1]);
	}

	static MimeType getMimeType(final String type, final String subtype) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		if (subtype == null) {
			throw new IllegalArgumentException();
		}
		// TODO: ADD CACHING HERE
		return new MimeType(type, subtype);
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	@Override public String toString() {
		return type + DELIMITER + subtype;
	}

}
