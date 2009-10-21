package gems.logging;

import gems.AbstractIdentifiable;
import gems.UnexpectedNullException;

import java.util.HashMap;
import java.util.Map;

/**
 * Logging record facility. It is simply a name. The only way how to get an instance
 * of facility is using a factory method. It is ensured that there is only one facility
 * object for a same name. Two facilities are considered to be equal if and only if
 * their names are same.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingFacility extends AbstractIdentifiable<String> {

	/**
	 * An empty logging facility, i.e. an ordinary logging facility with an empty name.
	 */
	public static final LoggingFacility NULL_FACILITY;

	/**
	 * An empty string.
	 */
	private static final String EMPTY_STRING = "";

	/**
	 * A storage of known facilities.
	 */
	private static final Map<String, LoggingFacility> KNOWN_FACILITIES = new HashMap<String, LoggingFacility>();

	static {
		NULL_FACILITY = new LoggingFacility(EMPTY_STRING);
		KNOWN_FACILITIES.put(EMPTY_STRING, NULL_FACILITY);
	}

	/**
	 * Creates a new logging facility with a given name.
	 *
	 * @param name a name of the facility.
	 */
	private LoggingFacility(final String name) {
		super(name);
	}

	/**
	 * Returns a logging facility with a given name. This method never returns {@code null}.
	 *
	 * @param name a name of the facility.
	 *
	 * @return a logging facility with a requested name.
	 *
	 * @throws UnexpectedNullException if {@code name} is {@code null}.
	 */
	public static synchronized LoggingFacility getFacility(final String name) {
		if (name == null) {
			throw new UnexpectedNullException();
		}
		final LoggingFacility known = KNOWN_FACILITIES.get(name);
		if (known != null) {
			return known;
		}
		final LoggingFacility unknown = new LoggingFacility(name);
		KNOWN_FACILITIES.put(name, unknown);
		return unknown;
	}

	/**
	 * Returns the name of the facility. This method never returns {@code null}.
	 *
	 * @return the name of the facility.
	 */
	@Override public String toString() {
		return getId();
	}

}
                   	