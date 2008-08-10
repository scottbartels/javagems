package cli;

import gems.cli.CliActuator;
import gems.cli.CliActuators;
import gems.cli.CliOption;
import gems.cli.CliOptionType;
import gems.cli.CliOptions;
import gems.cli.CliParserImpl;

/**
 * Let say that we need to implement a command line CD burning tool. We want to support usage similar to the following:
 * </p>
 * <pre>
 * java CdBurner -verbose -speed 4 -filter normalization -filter noisereduction \
 *               -speed 8 -- -track1 -track2 -track3 -track4
 * </pre>
 * <p>Where:</p>
 * <ul>
 * <li>
 * <p/>
 * <code>-verbose</code> is a switch turning a verbose logging on; it does not require any value.
 * </p>
 * </li>
 * <li>
 * <p/>
 * <code>-speed</code> is a single-value option expecting a burning speed. This is typed two times
 * on command line, and so the second occurence with value 8 is going to be used.
 * </p>
 * </li>
 * <li>
 * <p/>
 * <code>-filter</code> is a multi-value option expecting some "track preprocessors". Two preprocessors
 * are provided, 'normalization' and 'noisereduction'. The both values will be used, in this order.
 * </p>
 * </li>
 * <li>
 * <p/>
 * <code>--</code> is a stopword. It simply stops a recognition of command line options and it is
 * ignored itself. All the subsequent strings '-track#' are <em>resting arguments</em> and they possibly
 * stands for CD track files.
 * </p>
 * </li>
 * </ul>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CdBurner {

	/**
	 * Command line argurment parser's stopword.
	 */
	private static final String STOPWORD = "--";

	/**
	 * Options' prefix.
	 */
	private static final String PREFIX = "-";

	/**
	 * Command line options as expected by developer.
	 */
	private static final CliOptions OPTIONS = new CliOptions();

	/**
	 * Command line option '-verbose', without any values.
	 */
	private static final CliOption OPTION_VERBOSE = new CliOption("verbose", CliOptionType.NONE);

	/**
	 * Command line option '-speed', with a single value.
	 */
	private static final CliOption OPTION_SPEED = new CliOption("speed", CliOptionType.SINGLE);

	/**
	 * Command line option '-filter', with multiple values.
	 */
	private static final CliOption OPTION_FILTER = new CliOption("filter", CliOptionType.MULTIPLE);

	static {
		OPTIONS.add(OPTION_VERBOSE);
		OPTIONS.add(OPTION_SPEED);
		OPTIONS.add(OPTION_FILTER);
	}

	/**
	 * The entry point for Smart Java CD Burner application.
	 *
	 * @param args command line arguments.
	 */
	public static void main(final String[] args) {

		final CliActuators actuators = new CliParserImpl(PREFIX, STOPWORD).parse(args, OPTIONS);

		// Ok, arguments were parsed; now it's up to you how to use them.

		if (actuators.getActuatorById(OPTION_VERBOSE.getId()) != null) {
			System.out.println("Using verbose output.");
		}

		final CliActuator speed = actuators.getActuatorById(OPTION_SPEED.getId());
		if (speed != null) {
			System.out.println("User requires this speed: " + speed.getValue());
		} else {
			System.out.println("Using a default speed.");
		}

		final CliActuator filters = actuators.getActuatorById(OPTION_FILTER.getId());
		if (filters != null) {
			System.out.print("User requires following filtes: ");
			for (final String filter : filters.getValues()) {
				System.out.print("'" + filter + "' ");
			}
			System.out.println();
		}

		System.out.print("The resting arguments are: ");
		for (final String resting : actuators.getRests()) {
			System.out.print("'" + resting + "' ");
		}
		System.out.println();

		// And real show starts here.
	}

}
