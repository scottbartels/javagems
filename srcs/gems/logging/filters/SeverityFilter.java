package gems.logging.filters;

import gems.filtering.Filter;
import gems.logging.LoggingRecord;
import gems.logging.LoggingSeverity;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class SeverityFilter implements Filter<LoggingRecord> {

	private final LoggingSeverity treshold;

	public SeverityFilter(final LoggingSeverity treshold) {
		if (treshold == null) {
			throw new IllegalArgumentException();
		}
		this.treshold = treshold;
	}

	public boolean allows(final LoggingRecord record) {
		if (record == null) {
			throw new IllegalArgumentException();
		}
		return record.getTags().isSevere(treshold);
	}

}
