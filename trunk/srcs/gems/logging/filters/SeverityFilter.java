package gems.logging.filters;

import gems.Checks;
import gems.UnexpectedNullException;
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
	 * A severity threshold.
	 */
	private final LoggingSeverity threshold;

	/**
	 * Creates a new severity filter with a given severity threshold.
	 * Logging records containing at least a given severity level for
	 * any facility will be allowed for a further processing.
	 *
	 * @param threshold a severity threshold.
	 *
	 * @throws UnexpectedNullException if {@code treshold} is {@code null}.
	 */
	public SeverityFilter(final LoggingSeverity threshold) {
		this.threshold = Checks.ensureNotNull(threshold);
	}

	/**
	 * Checks whether a given logging record has logging tag
	 * metadata with the filtered threshold for any facility.
	 *
	 * @param record a filtered logging record.
	 *
	 * @return {@code true} if a given logging record has logging tag metadata
	 *         at least with the filtered threshold, {@code false} otherwise.
	 *
	 * @throws UnexpectedNullException if {@code record} is {@code null}.
	 */
	@Override public boolean allows(final LoggingRecord record) {
		return Checks.ensureNotNull(record).getTags().isSevere(threshold);
	}

}
