package gems.cli;

import gems.UnexpectedNullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@code CliActuators} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCliActuators {

	/**
	 * A tested object.
	 */
	private CliActuators actuators;

	/**
	 * Initialize a tested object.
	 */
	@Before public void init() {
		actuators = new CliActuators();
	}

	/**
	 * Checks whether the empty actuators instance returns an empty list of resting arguments.
	 */
	@Test public void emptyReturnsEmptyRests() {
		Assert.assertEquals(0, actuators.getRests().size());
	}

	/**
	 * Checks whether a list of resting arguments is unmodifiable.
	 */
	@Test(expected = UnsupportedOperationException.class) public void restsAreUnmodifiable() {
		actuators.getRests().add("");
	}

	/**
	 * Checks whether a {@code null} value as a resting argument is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsRestIsForbidden() {
		actuators.addRest(null);
	}

	/**
	 * Checks whether rests handling works.
	 */
	@Test public void addingRests() {
		actuators.addRest("a");
		actuators.addRest("c");
		actuators.addRest("b");
		final List<String> rests = actuators.getRests();
		Assert.assertEquals(3, rests.size());
		Assert.assertEquals("a", rests.get(0));
		Assert.assertEquals("c", rests.get(1));
		Assert.assertEquals("b", rests.get(2));
	}

	/**
	 * Checks whether {@code null} value as an actuator is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsActuatorIsForbidden() {
		actuators.addActuator(null);
	}

	/**
	 * Checks whether double adding of the same actuator is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void doubleAddingActuatorIsForbidden() {
		actuators.addActuator(new CliActuator("id"));
		actuators.addActuator(new CliActuator("id"));
	}

	/**
	 * Checks whether {@code null} value as a required actuator name is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullActuatorIdIsForbidden() {
		actuators.getActuatorById(null);
	}

	/**
	 * Checks whether a getting of an unknown actuator returns {@code null}.
	 */
	@Test public void gettingUnknownActuatorReturnsNull() {
		Assert.assertNull(actuators.getActuatorById("id"));
	}

	/**
	 * Checks whether actuators handling works.
	 */
	@Test public void addingActuators() {
		actuators.addActuator(new CliActuator("x"));
		actuators.addActuator(new CliActuator("y"));
		Assert.assertNotNull(actuators.getActuatorById("x"));
		Assert.assertNotNull(actuators.getActuatorById("y"));
	}

}
