package gems.cli;

import gems.AbstractIdentifiable;
import gems.Checks;
import gems.UnexpectedNullException;

/**
 * An option represents a command line interface option as designed by
 * a programmer and expected by an application. It does not say anything
 * about a real option usage on a command line during an application startup.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliOption extends AbstractIdentifiable<String> {

	/**
	 * A type of the option.
	 */
	private final CliOptionType type;

	/**
	 * Creates a new option with a given identifier and type.
	 *
	 * @param id an option identifier.
	 * @param type a type of the option.
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code id} is an empty string.
	 */
	public CliOption(final String id, final CliOptionType type) {
		super(id);
		if (id.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.type = Checks.ensureNotNull(type);
	}

	/**
	 * Returns a type of the option. This method never returns {@code null}.
	 *
	 * @return a type of the option.
	 */
	public CliOptionType getType() {
		return type;
	}

}
