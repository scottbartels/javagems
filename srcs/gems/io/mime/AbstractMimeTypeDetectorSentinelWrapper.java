package gems.io.mime;

import gems.Option;
import gems.ShouldNeverHappenException;

abstract class AbstractMimeTypeDetectorSentinelWrapper {
	
	private final MimeType sentinel;

	protected AbstractMimeTypeDetectorSentinelWrapper(final MimeType sentinel) {
		if (sentinel == null) {
			throw new IllegalArgumentException();
		}
		this.sentinel = sentinel;
	}

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	protected final Option<MimeType> ensureDefault(final Option<MimeType> type) {
		assert type != null;
		if (type.hasValue()) {
			return type;
		}
		final Option<MimeType> result = new Option<MimeType>(sentinel);
		if (result.hasValue()) {
			return result;
		}
		throw new ShouldNeverHappenException();
	}
	
}
