package gems.logging.filters;

import gems.filtering.Filter;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;

/**
 * A filter of logging records allowing for a further processing
 * logging records containg logging tag metadata of at least particular
 * severity for any facility.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SeverityFilter implements Filter<LoggingRecord> {

	/**
	 * A severity treshold.
	 */
	private final LoggingSeverity treshold;

	/**
	 * Creates a new severity filter with a given severity treshold.
	 * Logging records containing at least a given severity level for
	 * any facility will be allowed for a further processing.
	 *
	 * @param treshold a severity treshold.
	 *
	 * @throws IllegalArgumentException if {@code treshold} is {@code null}.
	 */
	public SeverityFilter(final LoggingSeverity treshold) {
		if (treshold == null) {
			throw new IllegalArgumentException();
		}
		this.treshold = treshold;
	}

	/**
	 * Checks whether a given logging record has logging tag
	 * metadata with the filtered treshold for any facility.
	 *
	 * @param record a filtered logging record.
	 *
	 * @return {@code true} if a given logging record has logging tag metadata
	 *         at least with the filtered treshold, {@code false} otherwise.
	 *
	 * @throws IllegalArgumentException if {@code record} is {@code null}.
	 */
	public boolean allows(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		return record.getTags().isSevere(treshold);
	}

}
