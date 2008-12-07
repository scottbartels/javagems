package gems.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class encapsulates a reading of raw content from an input stream.
 *
 * @author Jozef BABJAK
 */
public final class ByteContentReader {

	private static final int CHUNK_SIZE = 4096;

	private ByteContentReader() {
		throw new UnsupportedOperationException();
	}

	public static ByteContent read(final InputStream in) throws IOException { // TODO: THE PLAN IS TO AVOID IOException
		final ByteContent result = new AggregatedByteContent();

		try {
			final byte buffer[] = new byte[CHUNK_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) > 0) {
				result.addContent(new BasicByteContent(buffer, bytesRead));
			}
		} finally {
			in.close();
		}

		return result;
	}

}
