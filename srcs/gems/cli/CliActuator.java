package gems.cli;

import gems.AbstractIdentifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An actuator represents one recognized command line option and its values in a running application.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliActuator extends AbstractIdentifiable<String> {

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
		super(id);
		if (id.isEmpty()) {
			throw new IllegalArgumentException();
		}
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

}
