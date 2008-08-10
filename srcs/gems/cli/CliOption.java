package gems.cli;

import gems.Identifiable;

/**
 * An option represents a command line interface option as designed by
 * a programmer and expected by an application. It does not say anything
 * about a real option usage on a command line during an application startup.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CliOption implements Identifiable<String> {

	/**
	 * A unique immutable identifier of the option.
	 */
	private final String id;

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
	 * @throws IllegalArgumentException if any of arguments is {@code null} or if {@code id} is an empty string.
	 */
	public CliOption(final String id, final CliOptionType type) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (id.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (type == null) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.type = type;
	}

	/**
	 * {@inheritDoc} This method always returns non-empty string.
	 */
	public String getId() {
		return id;
	}


	/**
	 * Returns a type of the option. This method never returns {@code null}.
	 *
	 * @return a type of the option.
	 */
	public CliOptionType getType() {
		return type;
	}

	/**
	 * Defines an equality of two options. Two options are considered to be equal if and only if their IDs are the same.
	 *
	 * @param o a compared option.
	 *
	 * @return {@code true} if {@code o} is an option with the same ID, {@code false} otherwise.
	 */
	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final CliOption option = (CliOption) o;
		return id.equals(option.id);
	}

	/**
	 * Returns a hash code value for the option.
	 *
	 * @return a hash code value for the option.
	 */
	@Override public int hashCode() {
		return id.hashCode();
	}

}
