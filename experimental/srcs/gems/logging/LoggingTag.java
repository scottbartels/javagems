package gems.logging;

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
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public LoggingTag(final LoggingFacility facility, final LoggingSeverity severity) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		this.facility = facility;
		this.severity = severity;
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
