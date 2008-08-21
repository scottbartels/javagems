package gems.cli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	@Test(expected = IllegalArgumentException.class) public void nullAsRestIsForbidden() {
		actuators.addRest(null);
	}

	// TODO: TU SOM SKONCIL

}
