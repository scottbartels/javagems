package gems.io.mime;

import gems.AbstractIdentifiable;

public final class MimeType extends AbstractIdentifiable<String> {

	private MimeType(final String spec) {
		super(spec);
	}

	static MimeType getMimeType(final String spec) {
		if (spec == null) {
			throw new IllegalArgumentException();
		}
		// todo: check specification
		// todo: add chaching
		return new MimeType(spec);
	}

	public String getType() {
		return getId().split("/")[0];
	}

	public String getSubtype() {
		return getId().split("/")[1];
	}

	@Override public String toString() {
		return getId();
	}
}
