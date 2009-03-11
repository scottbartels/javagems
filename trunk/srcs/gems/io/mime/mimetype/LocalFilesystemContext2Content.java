package gems.io.mime.mimetype;

import gems.Option;
import gems.io.ByteContent;
import gems.io.IOUtils;

import java.io.File;

/**
 * Gets byte content by reading a local file specified by a context.
 * Context is interpreted as a path to a file.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LocalFilesystemContext2Content implements Context2Content<String> {

	/**
	 * Get content by reading a local file. The {@code context} is interpreted as a path.
	 * If a file resource given by {@code context} cannot be found, cannot be read or is
	 * not a regural file, an empty option is returned.
	 *
	 * @param context a path to a local file.
	 *
	 * @return content of a given file.
	 * @throws IllegalArgumentException if {@code context} is {@code null}.
	 */
	@Override public Option<ByteContent> context2content(final String context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}

		final File file = new File(context);
		if (!file.exists() || !file.canRead() || !file.isFile()) {
			return new Option<ByteContent>(null);
		}

		return new Option<ByteContent>(IOUtils.read(IOUtils.asInputStream(file)));
	}

}
