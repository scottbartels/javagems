package gems.logging;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@Deprecated public final class TagsHolder implements Iterable<LoggingTag> {

	/**
	 * A maximal severity.
	 */
	private final LoggingSeverity maximalSeverity;

	/**
	 * Tags.
	 */
	private final Map<LoggingFacility, LoggingSeverity> tags = new HashMap<LoggingFacility, LoggingSeverity>();

	TagsHolder(final LoggingTag[] tags) {
		if (tags == null) {
			throw new IllegalArgumentException();
		}
		if (tags.length == 0) {
			throw new IllegalArgumentException();
		}
		LoggingSeverity auxMax = null;
		for (final LoggingTag tag : tags) {
			if (tag == null) {
				throw new IllegalArgumentException();
			}
			putConditionally(tag.getFacility(), tag.getSeverity());
			if (auxMax == null || tag.getSeverity().compareTo(auxMax) > 0) {
				auxMax = tag.getSeverity();
			}

		}
		maximalSeverity = auxMax;
	}

	private void putConditionally(final LoggingFacility facility, final LoggingSeverity severity) {
		final LoggingSeverity known = this.tags.get(facility);
		if (known == null) {
			this.tags.put(facility, severity);
		} else {
			if (severity.compareTo(known) > 0) {
				this.tags.put(facility, severity);
			}
		}
	}

	public LoggingSeverity getSeverity(final LoggingFacility facility) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		return tags.get(facility);
	}

	/**
	 * Returns a maximal severity of tags. This method never returns {@code null}.
	 *
	 * @return a maximal severity of tags.
	 */
	public LoggingSeverity getMaximalSeverity() {
		return maximalSeverity;
	}

	/**
	 * Checks whether tags contains a tag for a given facility.
	 *
	 * @param facility a checked facility.
	 *
	 * @return {@code true} if tags contain a tag for a given facility, {@code false} otherwise.
	 */
	public boolean hasFacility(final LoggingFacility facility) {
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		return tags.containsKey(facility);
	}

	/**
	 * Checks whether tags' maximal severity is at least at a given level.
	 *
	 * @param level a checked level.
	 *
	 * @return {@code true} if tags' maximal severity is equal to or greater than a given level,
	 *         {@code false} otherwise.
	 *
	 * @throws IllegalArgumentException if {@code level} is {@code null}.
	 */
	public boolean isSevere(final LoggingSeverity level) {
		if (level == null) {
			throw new IllegalArgumentException();
		}
		return maximalSeverity.compareTo(level) >= 0;
	}

	/**
	 * Checks whether tags' severity for a given facility is at least at the given level.
	 * It implies that a {@code false} value is returned if tags do not contain any tag
	 * for a given facility.
	 *
	 * @param level a checked level.
	 * @param facility a checked facility.
	 *
	 * @return {@code true} if tags contain a tag for a given facility with a severity equal to or
	 *         greater than a given level, {@code false} otherwise.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public boolean isSevere(final LoggingSeverity level, final LoggingFacility facility) {
		if (level == null) {
			throw new IllegalArgumentException();
		}
		if (facility == null) {
			throw new IllegalArgumentException();
		}
		final LoggingSeverity found = tags.get(facility);
		return found != null && found.compareTo(level) >= 0;
	}

	/**
	 * Checks whether tags contain at least one tag with a given severity.
	 *
	 * @param severity a checked severity.
	 *
	 * @return {@code true} if tags contain at least one tag with a given severity, {@code false} otherwise.
	 *
	 * @throws IllegalArgumentException if {@code severity} is {@code null}.
	 */
	public boolean hasSeverity(final LoggingSeverity severity) {
		if (severity == null) {
			throw new IllegalArgumentException();
		}
		return severity.equals(maximalSeverity) || tags.values().contains(severity);
	}

	public Iterator<LoggingTag> iterator() {
		return new LinkedList<LoggingTag>().iterator(); // TODO: COMPLETE THIS SOMEHOW
	}
}
