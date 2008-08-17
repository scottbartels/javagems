package gems.logging.filters;

import gems.logging.LoggingRecord;
import gems.logging.LoggingFacility;
import gems.filtering.Filter;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class FacilityFilter implements Filter<LoggingRecord> {

	private final LoggingFacility facility;

	public FacilityFilter(final LoggingFacility facility) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		this.facility = facility;
	}

	public boolean allows(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		return record.getTags().hasFacility(facility);
	}
	
}
