package gems.io.mime;

import gems.AbstractIdentifiable;

public final class MimeType extends AbstractIdentifiable<String> {

	private MimeType(final String spec) {
		super(spec);
		// todo: check specification
	}

	static MimeType getMimeType(final String spec) {
		if (spec == null) {
			throw new IllegalArgumentException();
		}
		// todo: add chaching
		return new MimeType(spec);
	}

	@Deprecated public String getType() {
		return getId().split("/")[0]; // TODO: TO BE LAZY OR NOT TO BE LAZY?
	}

	@Deprecated public String getSubtype() {
		return getId().split("/")[1]; // TODO: TO BE LAZY OR NOT TO BE LAZY?
	}

	@Override public String toString() {
		return getId();
	}
}
