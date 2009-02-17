package gems.io.mime;

import gems.Option;
import gems.ShouldNeverHappenException;

abstract class AbstractSentinelWrapper {
	
	private final MimeType defaultType;

	protected AbstractSentinelWrapper(final MimeType defaultType) {
		if (defaultType == null) {
			throw new IllegalArgumentException();
		}
		this.defaultType = defaultType;
	}

	// TODO: EXPLAINT IN JavaDoc THAT RETURNED Option IS ALREADY CHECKED.
	protected final Option<MimeType> ensureDefault(final Option<MimeType> type) {
		assert type != null;
		if (type.hasValue()) {
			return type;
		}
		final Option<MimeType> result = new Option<MimeType>(defaultType);
		if (result.hasValue()) {
			return result;
		}
		throw new ShouldNeverHappenException();
	}
	
}
