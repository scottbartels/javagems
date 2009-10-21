package gems.logging.filters;

import gems.Checks;
import gems.UnexpectedNullException;
import gems.filtering.Filter;
import gems.logging.LoggingFacility;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;

/**
 * A logging records filter allowing for a further processing
 * logging records containg at least particular severity for
 * a particular facility.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SeverityOfFacilityFilter implements Filter<LoggingRecord> {

	/**
	 * A severity threshold.
	 */
	private final LoggingSeverity threshold;

	/**
	 * A filtered facility.
	 */
	private final LoggingFacility facility;

	/**
	 * Creates a new filter filtering logging records with logging tag metadata
	 * containing at least a given severity for a given facility.
	 *
	 * @param threshold a severity threshold.
	 * @param facility a filtered facility.
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 */
	public SeverityOfFacilityFilter(final LoggingSeverity threshold, final LoggingFacility facility) {
		this.threshold = Checks.ensureNotNull(threshold);
		this.facility = Checks.ensureNotNull(facility);
	}

	/**
	 * Checks wheter a given logging record has logging tag metadata
	 * containing with at least checked threshold for the filtered
	 * facility.
	 *
	 * @param record a filtered logging record.
	 *
	 * @return {@code true} if a given logging record contains logging tag metadata with
	 *         at least checked threshold for the filtered facility, {@code false} otherwise.
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	@Override public boolean allows(final LoggingRecord record) {
		return Checks.ensureNotNull(record).getTags().isSevere(threshold, facility);
	}

}
