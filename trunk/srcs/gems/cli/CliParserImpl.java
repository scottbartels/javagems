package gems.cli;

import gems.Checks;
import gems.UnexpectedNullException;

/**
 * A basic inplementation of {@code CliParser} interface with the following features:
 * <ul>
 * <li><p>A given <em>stopword</em> is supported to force options recognition termination.</p></li>
 * <li><p>The first non-option argument on place where option is expected terminates options recognision.</p></li>
 * <li><p>The first non-expected option terminates options recognition, the option fails to rest silently.</p></li>
 * <li><p>If multiple values are recognized for non-multivalue option, the last value wins.</p></li>
 * <li><p>It the last argument is a recognized option requiring a value but there is no value for it,
 * it gets an empty string as a value.</p></li>
 * </ul>
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliParserImpl implements CliParser {

	/**
	 * An empty string.
	 */
	private static final String EMPTY_STRING = "";

	/**
	 * Stopword.
	 */
	private final String stopword;

	/**
	 * Option prefix.
	 */
	private final String prefix;

	/**
	 * Creates a new parser with a given stopword.
	 *
	 * @param prefix an options' prefix.
	 * @param stopword a parser stopword.
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 */
	public CliParserImpl(final String prefix, final String stopword) {
		this.prefix = Checks.ensureNotNull(prefix);
		this.stopword = Checks.ensureNotNull(stopword);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 */
	@Override public CliActuators parse(final String[] args, final CliOptions options) {
		return parseImpl(Checks.ensureNotNull(args), Checks.ensureNotNull(options));
	}

	/**
	 * Implements parsing.
	 *
	 * @param args command-line arguments.
	 * @param options program options.
	 *
	 * @return recognized actuators.
	 */
	private CliActuators parseImpl(final String[] args, final CliOptions options) {
		final CliActuators result = new CliActuators();
		int offset = 0;
		while (offset < args.length) {
			final String current = args[offset];
			if (current.equals(stopword)) { // stopword found; terminating
				offset++; // skip stopword itself
				break;
			}
			if (!current.startsWith(prefix)) { // non-option found; terminating
				break;
			}
			final CliOption option = options.getOptionById(current.replaceFirst(prefix, EMPTY_STRING));
			if (option == null) { // current string seems like option, but it does not have expected name; terminating
				break;
			}
			offset++; // ok, we found an option
			final CliActuator actuator = getOrCreateAndAddActuator(result, option.getId());
			if (option.getType().equals(CliOptionType.NONE)) { // nothing more to do
				continue;
			}
			if (!option.getType().equals(CliOptionType.MULTIPLE)) { // clear any previously set values
				actuator.clearValues();
			}
			actuator.addValue(offset < args.length ? args[offset++] : EMPTY_STRING);
		}
		for (int i = offset; i < args.length; i++) {
			result.addRest(args[i]);
		}
		return result;
	}

	/**
	 * Tries to found an actuator with a given ID among given actuators. If such actuator
	 * is found, it is returned. If actuators don't contain an actuator with a given ID,
	 * a new actuator is created and subsequently added to actuators and finally returned.
	 * So, this method never returns {@code null}.
	 *
	 * @param actuators actuators.
	 * @param id an ID of requested actuator.
	 *
	 * @return a found or newly created actuator.
	 */
	private static CliActuator getOrCreateAndAddActuator(final CliActuators actuators, final String id) {
		final CliActuator existing = actuators.getActuatorById(id);
		if (existing != null) {
			return existing;
		}
		final CliActuator created = new CliActuator(id);
		actuators.addActuator(created);
		return created;
	}

}
