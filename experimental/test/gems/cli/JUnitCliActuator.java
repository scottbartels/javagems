package gems.cli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@code CliActuator} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCliActuator {

	/**
	 * An empty string.
	 */
	private static final String EMPTY_STRING = "";

	/**
	 * A non-empty actuator name.
	 */
	private static final String NAME = "option";

	/**
	 * A tested instance.
	 */
	private CliActuator actuator;

	/**
	 * Initializes a tested instnace.
	 */
	@Before public void init() {
		actuator = new CliActuator(NAME);
	}

	/**
	 * Checks whether a {@code null} value is as an id is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullIdIsForbidden() {
		new CliActuator(null);
	}

	/**
	 * Checks whether an empty string as an id is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void emptyIdIsForbidden() {
		new CliActuator(EMPTY_STRING);
	}

	/**
	 * Checks whether getting values returns an empty list for the empty actuator.
	 */
	@Test public void emptyReturnsEmptyListOfValues() {
		Assert.assertEquals(0, actuator.getValues().size());
	}

	/**
	 * Checks whether a {@null value} is returned as a value of the empty actuator.
	 */
	@Test public void emptyReturnsNullValue() {
		Assert.assertNull(actuator.getValue());
	}

	/**
	 * Checks whether a {@code null} value as a value is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void addingNullValueIsForbidden() {
		actuator.addValue(null);
	}

	/**
	 * Checks whether a returned list of values is unmodifiable.
	 */
	@Test(expected = UnsupportedOperationException.class) public void returnedValuesListIsUnmodifiable() {
		actuator.getValues().add(EMPTY_STRING);
	}

	/**
	 * Checks whether a single stored value is correctly returned.
	 */
	@Test public void storedValueIsReturned() {
		actuator.addValue(EMPTY_STRING);
		Assert.assertEquals(EMPTY_STRING, actuator.getValue());
		Assert.assertEquals(1, actuator.getValues().size());
	}

	/**
	 * Checks whether a values cleaning works.
	 */
	@Test public void clearingValuesWorks() {
		actuator.addValue(EMPTY_STRING);
		actuator.clearValues();
		Assert.assertNull(actuator.getValue());
		Assert.assertEquals(0, actuator.getValues().size());
	}

	/**
	 * Checks whether a storing of multiple equal values is supported.
	 */
	@Test public void addingMultipleEqualValuesIsAllowed() {
		actuator.addValue(EMPTY_STRING);
		actuator.addValue(EMPTY_STRING);
		final List<String> values = actuator.getValues();
		Assert.assertEquals(2, values.size());
		Assert.assertEquals(EMPTY_STRING, values.get(0));
		Assert.assertEquals(EMPTY_STRING, values.get(1));
	}

	/**
	 * Checks whether a storing of multiple values works correctly, including their ordering.
	 */
	@Test(expected = IndexOutOfBoundsException.class) public void storingMultipleValuesWorks() {
		actuator.addValue("c");
		actuator.addValue("b");
		actuator.addValue("a");
		final List<String> values = actuator.getValues();
		Assert.assertEquals(3, values.size());
		Assert.assertEquals("c", values.get(0));
		Assert.assertEquals("b", values.get(1));
		Assert.assertEquals("a", values.get(2));
		values.get(3);
	}

}
