package gems.cli;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for {@code CliOption} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCliOption {

	/**
	 * A non-empty name.
	 */
	private static final String NAME = "option";

	/**
	 * Checks whether the constructor forbids a {@code null} value as an id.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNullId() {
		new CliOption(null, CliOptionType.NONE);
	}

	/**
	 * Checks whether the constructor forbids an empty string as an id.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsEmptyId() {
		new CliOption("", CliOptionType.NONE);
	}

	/**
	 * Checks whether the constructor forbids a {@code null} value as a type.
	 */
	@Test(expected = IllegalArgumentException.class) public void constructorForbidsNullType() {
		new CliOption(NAME, null);
	}

	/**
	 * Checks whether a correct type is returned for an option of type {@code NONE}.
	 */
	@Test public void getTypeReturnsCorrectTypeForTypeNone() {
		Assert.assertEquals(CliOptionType.NONE, new CliOption(NAME, CliOptionType.NONE).getType());
	}

	/**
	 * Checks whether a correct type is returned for an option of type {@code SINGLE}.
	 */
	@Test public void getTypeReturnsCorrectTypeForTypeSingle() {
		Assert.assertEquals(CliOptionType.SINGLE, new CliOption(NAME, CliOptionType.SINGLE).getType());
	}

	/**
	 * Checks whether a correct type is returned for an option of type {@code MULTIPLE}. 
	 */
	@Test public void getTypeReturnsCorrectTypeForTypeMultiple() {
		Assert.assertEquals(CliOptionType.MULTIPLE, new CliOption(NAME, CliOptionType.MULTIPLE).getType());
	}

}
