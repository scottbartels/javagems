package gems.io.mime;

final class LocalPathExtensionDetector implements ExtensionDetector<String> {

	private final String pathSeparator;

	private final String extensionSeparator;

	LocalPathExtensionDetector(final String pathSeparator, final String extensionSeparator) {
		if (pathSeparator == null) {
			throw new IllegalArgumentException();
		}
		if (extensionSeparator == null) {
			throw new IllegalArgumentException();
		}
		this.pathSeparator = pathSeparator;
		this.extensionSeparator = extensionSeparator;

	}

	@Override public String detect(final String path) {
		if (path == null) {
			throw new IllegalArgumentException();
		}
		return name2extension(path2name(path));
	}

	private String path2name(final String path) {
		return lastFragment(path, pathSeparator);
	}

	private String name2extension(final String name) {
		return lastFragment(name, extensionSeparator);
	}

	private static String lastFragment(final String source, final String separator) {
		return source.substring(source.lastIndexOf(separator) + 1);
	}

}
