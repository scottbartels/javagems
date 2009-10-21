package gems.logging;

import gems.Checks;
import gems.UnexpectedNullException;

/**
 * A logging tag consists of a logging facility and its corresponding severity.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingTag {

	/**
	 * A facility.
	 */
	private final LoggingFacility facility;

	/**
	 * A severity.
	 */
	private final LoggingSeverity severity;

	/**
	 * Creates a new logging tag with given facility and severity.
	 *
	 * @param facility a facility.
	 * @param severity a severity.
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 */
	public LoggingTag(final LoggingFacility facility, final LoggingSeverity severity) {
		this.facility = Checks.ensureNotNull(facility);
		this.severity = Checks.ensureNotNull(severity);
	}

	/**
	 * Returns a facility of the tag. This method never returns {@code null}.
	 *
	 * @return a facility of the tag.
	 */
	public LoggingFacility getFacility() {
		return facility;
	}

	/**
	 * Returns a severity of the tag. This method never returns {@code null}.
	 *
	 * @return a severity of the tag.
	 */
	public LoggingSeverity getSeverity() {
		return severity;
	}

}
