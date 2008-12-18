package gems.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregation of contents. It provides easy way how to concatenate partial
 * contents into one long content without a neccesity of copying them. The only
 * exception is {@code getBytes()} method, which still copies underlaying
 * contents and returns a defense copy.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class AggregatedByteContent extends AbstractByteContent {

	/**
	 * Aggregated content parts.
	 */
	private final List<ByteContent> parts = new ArrayList<ByteContent>();

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to subcontent length.
	 */
	public synchronized byte getByteAt(final int index) {
		if (index < 0 || index >= length()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		int offset = 0;
		for (final ByteContent content : parts) {
			if (index < offset + content.length()) {
				return content.getByteAt(index - offset);
			}
			offset += content.length();
		}
		throw new RuntimeException("You should never see this message.");
	}

	/**
	 * Adds an additional content to aggregation. Added content is put on the end of aggregation.
	 *
	 * @param content an added content.
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	@Override public synchronized void addContent(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		setLength(length() + content.length());
		parts.add(content);
	}

}
