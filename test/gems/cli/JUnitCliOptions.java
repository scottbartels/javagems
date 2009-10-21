package gems.cli;

import gems.UnexpectedNullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code CliOptions} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCliOptions {

	/**
	 * An option name.
	 */
	private static final String NAME = "option";

	/**
	 * An option.
	 */
	private static final CliOption OPTION = new CliOption(NAME, CliOptionType.NONE);

	/**
	 * A tested options object.
	 */
	private CliOptions options;

	/**
	 * Initialize a tested options object and puts one option into.
	 */
	@Before public void init() {
		options = new CliOptions();
		options.add(OPTION);
	}

	/**
	 * Checks whether an adding of a {@code null} value as an option is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void addingNullOptionIsForbidden() {
		options.add(null);
	}

	/**
	 * Checks whether and adding of a duplicit option is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void addingDuplicitOptionIsForbidden() {
		options.add(OPTION);
	}

	/**
	 * Checks whether a {@code null} value is forbidden as id for getting an option.
	 */
	@Test(expected = UnexpectedNullException.class) public void getForbidsNullId() {
		options.getOptionById(null);
	}

	/**
	 * Checks whether a contained option is returned by its id.
	 */
	@Test public void containingOptionsIsReturned() {
		Assert.assertEquals(OPTION, options.getOptionById(NAME));
	}

	/**
	 * Checks whether a lookup for a not contained option returns {@code null}.
	 */
	@Test public void nonContainingIdLeadsToNullReturnValue() {
		Assert.assertNull(options.getOptionById("nonexists"));
	}

}
