package gems.cli;

import gems.Checks;
import gems.UnexpectedNullException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A holder for recognized actuators and unrecognized resting command line arguments.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliActuators {

	/**
	 * Actuators.
	 */
	private final List<CliActuator> actuators = new LinkedList<CliActuator>();

	/**
	 * Resting command line arguments.
	 */
	private final List<String> rests = new LinkedList<String>();

	/**
	 * Returns an unmodifiable view to resting command line arguments.
	 * This method never returns {@code null}, at least an empty
	 * list is returned. Items are ordered according to arguments'
	 * insertion order.
	 *
	 * @return an unmodifiable view to resting command line argumens.
	 */
	public List<String> getRests() {
		return Collections.unmodifiableList(rests);
	}

	/**
	 * Adds a new resting command line argument.
	 *
	 * @param rest an added resting command line argument.
	 *
	 * @throws UnexpectedNullException if {@code rest} is {@code null}.
	 */
	public void addRest(final String rest) {
		rests.add(Checks.ensureNotNull(rest));
	}

	/**
	 * Adds a new actuator.
	 *
	 * @param actuator an added actuator.
	 *
	 * @throws UnexpectedNullException if {@code actuator} is {@code null}.
	 * @throws IllegalStateException if an actuator with the same ID as a given actuator's ID is already contained in the object.
	 */
	public void addActuator(final CliActuator actuator) {
		if (actuators.contains(Checks.ensureNotNull(actuator))) {
			throw new IllegalStateException();
		}
		actuators.add(actuator);
	}

	/**
	 * Returns an actuator with a given ID or {@code null} if no such actuator is found.
	 *
	 * @param id a requested actuator's ID.
	 *
	 * @return an actuator with a given ID or {@code null} if no such actuator is found.
	 *
	 * @throws UnexpectedNullException if {@code id} is {@code null}.
	 */
	public CliActuator getActuatorById(final String id) {
		if (id == null) {
			throw new UnexpectedNullException();
		}
		for (final CliActuator actuator : actuators) {
			if (actuator.getId().equals(id)) {
				return actuator;
			}
		}
		return null;
	}

}
