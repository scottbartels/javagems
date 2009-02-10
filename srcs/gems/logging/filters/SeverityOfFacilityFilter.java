package gems.logging.filters;

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
	 * A severity treshold.
	 */
	private final LoggingSeverity treshold;

	/**
	 * A filtered facility.
	 */
	private final LoggingFacility facility;

	/**
	 * Creates a new filter filtering logging records with logging tag metadata
	 * containing at least a given severity for a given facility.
	 *
	 * @param treshold a severity treshold.
	 * @param facility a filtered facility.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public SeverityOfFacilityFilter(final LoggingSeverity treshold, final LoggingFacility facility) {
		if (treshold == null) {
			throw new IllegalArgumentException();
		}
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		this.treshold = treshold;
		this.facility = facility;
	}

	/**
	 * Checks wheter a given logging record has logging tag metadata
	 * containing with at least checked treshold for the filtered
	 * facility.
	 *
	 * @param record a filtered logging record.
	 *
	 * @return {@code true} if a given logging record contains logging tag metadata with
	 *         at least checked treshold for the filtered facility, {@code false} otherwise.
	 */
	@Override public boolean allows(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		return record.getTags().isSevere(treshold, facility);
	}

}
