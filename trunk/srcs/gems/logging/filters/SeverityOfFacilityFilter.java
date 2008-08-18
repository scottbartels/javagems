package gems.logging.filters;

import gems.filtering.Filter;
import gems.logging.LoggingFacility;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SeverityOfFacilityFilter implements Filter<LoggingRecord> {

	private final LoggingSeverity treshold;

	private final LoggingFacility facility;

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

	public boolean allows(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		return record.getTags().isSevere(treshold, facility);
	}
	
}
