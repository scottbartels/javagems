package gems.io.mime;

/**
 * A filename extensions detector for local filenames analysis. Please
 * note that this class do not touch a local filesystem anyhow. It simply
 * consider the context to be an ordinary path. 
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class LocalPathExtensionDetector implements ExtensionDetector<String> {

	/**
	 * A path separator.
	 */
	private final String pathSeparator;

	/**
	 * An extension separator.
	 */
	private final String extensionSeparator;

	/**
	 * Creates a new detector using given separators.
	 *
	 * @param pathSeparator a path separator.
	 * @param extensionSeparator an extension separator.
	 *
	 * @throws IllegalArgumentException if any of argumens is {@code null} or empty string.
	 */
	LocalPathExtensionDetector(final String pathSeparator, final String extensionSeparator) {
		if (pathSeparator == null) {
			throw new IllegalArgumentException();
		}
		if (extensionSeparator == null) {
			throw new IllegalArgumentException();
		}
		if (pathSeparator.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (extensionSeparator.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.pathSeparator = pathSeparator;
		this.extensionSeparator = extensionSeparator;

	}

	/**
	 * {@inheritDoc} In this case, a context is considered to be a filesystem path.
	 *
	 * @throws IllegalArgumentException if {@code context} is {@code null}.
	 */
	@Override public String detect(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		return name2extension(path2name(context));
	}

	/**
	 * Returns a name detected from given path.
	 *
	 * @param path a path.
	 *
	 * @return a name detected from given path.
	 */
	private String path2name(final String path) {
		return lastFragment(path, pathSeparator);
	}

	/**
	 * Returns an extension detected from given name.
	 *
	 * @param name a name.
	 *
	 * @return an extension detected from given name.
	 */
	private String name2extension(final String name) {
		return lastFragment(name, extensionSeparator);
	}

	/**
	 * Returns the last fragment of {@code source} splitting it by {@code separator}.
	 *
	 * @param source a source string.
	 * @param separator a delimiting separator.
	 *
	 * @return the last fragment of {@code source} splitting it by {@code separator}.
	 */
	private static String lastFragment(final String source, final String separator) {
		return source.substring(source.lastIndexOf(separator) + separator.length());
	}

}
