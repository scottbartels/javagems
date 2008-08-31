package gems.cli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCliParserImpl {

	/**
	 * Testing options setup.
	 */
	private static final CliOptions OPTIONS = new CliOptions();

	static {
		OPTIONS.add(new CliOption("verbose", CliOptionType.NONE));
		OPTIONS.add(new CliOption("speed", CliOptionType.SINGLE));
		OPTIONS.add(new CliOption("filter", CliOptionType.MULTIPLE));
	}

	/**
	 * A tested parser.
	 */
	private CliParser parser;

	/**
	 * Initializes a tested parser.
	 */
	@Before public void initParser() {
		parser = new CliParserImpl("-", "--");
	}

	@Test(expected = IllegalArgumentException.class) public void nullAsPrefixIsForbidden() {
		new CliParserImpl(null, "");
	}

	@Test(expected = IllegalArgumentException.class) public void nullAsStopwordIsForbidden() {
		new CliParserImpl("", null);
	}

	@Test(expected = IllegalArgumentException.class) public void nullAsArgsIsForbidden() {
		parser.parse(null, OPTIONS);
	}

	@Test(expected = IllegalArgumentException.class) public void nullAsOptionsIsForbidden() {
		parser.parse(new String[0], null);
	}

	@Test public void gracefullyEndsForEmptyInputs() {
		Assert.assertNotNull(parser.parse(new String[0], new CliOptions()));
	}

	@Test public void completeUsage() {
		final String[] args = new String[] {"-verbose", "-speed", "4", "-filter", "a", "-filter", "b", "x", "y", "z"};
		final CliActuators actuators = parser.parse(args, OPTIONS);
		// -verbose
		Assert.assertNotNull(actuators.getActuatorById("verbose"));
		// -speed
		Assert.assertEquals("4", actuators.getActuatorById("speed").getValue());
		// -filter
		final CliActuator filter = actuators.getActuatorById("filter");
		final List<String> filters = filter.getValues();
		Assert.assertEquals(2, filters.size());
		Assert.assertEquals("a", filters.get(0));
		Assert.assertEquals("b", filters.get(1));
		// rests
		final List<String> rests = actuators.getRests();
		Assert.assertEquals(3, rests.size());
		Assert.assertEquals("x", rests.get(0));
		Assert.assertEquals("y", rests.get(1));
		Assert.assertEquals("z", rests.get(2));
	}

	@Test public void stopwordWorks() {
		final String[] args = new String[] {"--", "-verbose"};
		final CliActuators actuators = parser.parse(args, OPTIONS);
		Assert.assertNull(actuators.getActuatorById("verbose"));
		Assert.assertEquals("-verbose", actuators.getRests().get(0));
	}

	@Test public void unknownOptionStopsProcessing() {
		final String[] args = new String[] {"-unknown"};
		final CliActuators actuators = parser.parse(args, OPTIONS);
		Assert.assertEquals("-unknown", actuators.getRests().get(0));
	}

	@Test public void lastWins() {
		final String[] args = new String[] {"-speed", "4", "-speed", "8"};
		final CliActuators actuators = parser.parse(args, OPTIONS);
		Assert.assertEquals("8", actuators.getActuatorById("speed").getValue());
	}

	@Test public void atLeastEmptyValueIsForced() {
		final String[] args = new String[] {"-speed"};
		final CliActuators actuators = parser.parse(args, OPTIONS);
		Assert.assertEquals("", actuators.getActuatorById("speed").getValue());
	}

}
