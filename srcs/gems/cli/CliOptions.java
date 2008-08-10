package gems.cli;

import java.util.HashSet;
import java.util.Set;

/**
 * A set of options.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliOptions {

	/**
	 * Options.
	 */
	private final Set<CliOption> options = new HashSet<CliOption>();

	/**
	 * Returns an option with a given ID. If no such option is found, {@code null} value is returned.
	 *
	 * @param id an ID of a required option.
	 *
	 * @return an option with a given ID or {@code null} if no such option is found.
	 *
	 * @throws IllegalArgumentException if {@code id} is {@code null}.
	 */
	public CliOption getOptionById(final String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		for (final CliOption option : options) {
			if (option.getId().equals(id)) {
				return option;
			}
		}
		return null;
	}

	/**
	 * Adds a given option. The {@code null} value is not allowed here.
	 * If the object already contains an option with the same ID as a given
	 * option's ID, {@code IllegalStateException} is thrown.
	 *
	 * @param option an added option.
	 *
	 * @throws IllegalArgumentException if {@code option} is {@code null}.
	 * @throws IllegalStateException if the object already contains an option with the same ID as a given option's ID.
	 */
	public void add(final CliOption option) {
		if (option == null) {
			throw new IllegalArgumentException();
		}
		if (options.contains(option)) {
			throw new IllegalStateException();
		}
		options.add(option);
	}

}
