package gems.io.mime.mimetype;

import gems.Option;

/**
 * This class contains common parts of sentinel wrappers: it holds the default MIME type (sentinel)
 * and provides functionality ensuring that the default type is used instead of an unkonwn type.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractSentinelWrapper {

	/**
	 * The value used instead of unknown MIME type.
	 */
	private final MimeType sentinel;

	/**
	 * Initiates a new wrapper using a given type as a default type for unknown MIME type.
	 *
	 * @param sentinel the value used instead of MIME type.
	 *
	 * @throws IllegalArgumentException if {@code sentinel} is {@code null}.
	 */
	protected AbstractSentinelWrapper(final MimeType sentinel) {
		if (sentinel == null) {
			throw new IllegalArgumentException();
		}
		this.sentinel = sentinel;
	}

	/**
	 * Ensures that returned Option always contains a value. If given Option does
	 * not contain a value, a new Opition containing a sentinel value is returned.
	 *
	 * @param type a possibly empty Option.
	 *
	 * @return a non-empty Option.
	 */
	protected final Option<MimeType> ensureCheckedDefault(final Option<MimeType> type) {
		assert type != null;
		if (type.hasValue()) {
			return new Option<MimeType>(type.getValue());
		}
		return new Option<MimeType>(sentinel);
	}

}
