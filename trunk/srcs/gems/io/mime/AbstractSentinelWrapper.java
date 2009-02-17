package gems.io.mime;

import gems.Option;

abstract class AbstractSentinelWrapper {
	
	private final MimeType defaultType;

	protected AbstractSentinelWrapper(final MimeType defaultType) {
		if (defaultType == null) {
			throw new IllegalArgumentException();
		}
		this.defaultType = defaultType;
	}

	protected final Option<MimeType> ensureDefault(final Option<MimeType> type) {
		/*
		 * TODO: THIS METHOD MIGHT RETURN ALREADY CHECKED OPTION. 
		 */
		assert type != null;
		if (type.hasValue()) {
			return new Option<MimeType>(type.getValue());
		}
		return new Option<MimeType>(defaultType);
	}
}
