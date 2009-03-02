package gems.io.mime;

import gems.Option;
import gems.ShouldNeverHappenException;

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
	 * Ensures that returned Option always contains a value and it is already checked.
	 * If given Option contains a value, that option is returned. If givne Option does
	 * not contain a value, a new Opition containing a sentinel value is returned.
	 * Anyhow, returned Option object is already checked.
	 *
	 * @param type a possibly empty Option.
	 *
	 * @return an already checked and never empty Option.
	 */
	protected final Option<MimeType> ensureCheckedDefault(final Option<MimeType> type) {
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
