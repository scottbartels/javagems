package gems.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregation of contents. It provides easy way how to concatenate partial
 * contents into one long content without a neccesity of copying them. The only
 * exception is {@code getBytes()} method, which still need to copy underlaying
 * contents.
 *
 * @author Jozef BABJAK
 */
public final class AggregatedByteContent extends AbstractByteContent {

	/**
	 * Default initial capacity for array list holding parts of this aggregated content.
	 */
	private static final int ARRAY_LIST_INITIAL_CAPACITY = 100;

	/**
	 * Aggregated content parts.
	 */
	private final List<ByteContent> parts = new ArrayList<ByteContent>(ARRAY_LIST_INITIAL_CAPACITY);

	/**
	 * {@inheritDoc}
	 */
	public synchronized byte getByteAt(final int index) {
		if (index < 0 || index >= length()) {
			throw new IndexOutOfBoundsException("Wrong index: " + index);
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
