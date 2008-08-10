package gems.cli;

import gems.Identifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An actuator represents one recognized command line option and its values in a running application.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliActuator implements Identifiable<String> {

	/**
	 * An identifier of the actuator. This is the same identifier
	 * as an identifier of the option, for which was this identifier
	 * created.
	 */
	private final String id;

	/**
	 * Values.
	 */
	private final List<String> values = new LinkedList<String>();

	/**
	 * Creates a new actuator with a given ID. The ID is the same identifier
	 * as an identifier of the option, for which was this identifier created.
	 *
	 * @param id an actuator identifier.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null} or if it is an empty string.
	 */
	public CliActuator(final String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (id.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * {@inheritDoc} This method always returns non-empty string.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Removes all values of the actuator.
	 */
	public void clearValues() {
		values.clear();
	}

	/**
	 * Adds a new value to the actuator.
	 *
	 * @param value an added value.
	 *
	 * @throws IllegalArgumentException if {@code value} is {@code null}.
	 */
	public void addValue(final String value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		values.add(value);
	}

	/**
	 * Returns a value of the actuator, or {@code null} if the actuator has no values.
	 * <em>It is not defined which value is returned if the actuator has more values.</em>
	 *
	 * @return a value of the actuator, or {@code null} if the actuator has no values.
	 */
	public String getValue() {
		return values.isEmpty() ? null : values.get(0);
	}

	/**
	 * Returns an unmodifiable view to stored values. Values are ordered
	 * according its insertion into actuator. This method never returns
	 * {@code null}, at least an empty list is returned if there are no
	 * values in the actuator.
	 *
	 * @return a list of stored values.
	 */
	public List<String> getValues() {
		return Collections.unmodifiableList(values);
	}

	/**
	 * Defines an equality of two actuators. Two actuators are considered to be equal if and only if their IDs are the same.
	 *
	 * @param o a compared actuator.
	 *
	 * @return {@code true} if {@code o} is an actuator with the same ID, {@code false} otherwise.
	 */
	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final CliActuator actuator = (CliActuator) o;
		return id.equals(actuator.id);
	}

	/**
	 * Returns a hash code value for the option.
	 *
	 * @return a hash code value for the option.
	 */
	@Override public int hashCode() {
		return id.hashCode();
	}

}
