package gems.logging.filters;

import gems.Checks;
import gems.UnexpectedNullException;
import gems.filtering.Filter;
import gems.logging.LoggingFacility;
import gems.logging.LoggingRecord;

/**
 * A filter of logging records allowing for a further processing
 * logging records containing logging tag metadata for a particular
 * facility.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FacilityFilter implements Filter<LoggingRecord> {

	/**
	 * A filtered facility.
	 */
	private final LoggingFacility facility;

	/**
	 * Creates a new filter for a given facility.
	 *
	 * @param facility a filtered facility.
	 *
	 * @throws UnexpectedNullException if {@code facility} is {@code null}.
	 */
	public FacilityFilter(final LoggingFacility facility) {
		this.facility = Checks.ensureNotNull(facility);
	}

	/**
	 * Checks whether a given logging record contains logging
	 * tag metadata for the filtered logging facility.
	 *
	 * @param record a filtered logging record.
	 *
	 * @return {@code true} if a given record contains logging tag metadata for the filtered facility, {@code false} otherwise.
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	@Override public boolean allows(final LoggingRecord record) {
		return Checks.ensureNotNull(record).getTags().hasFacility(facility);
	}

}
