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
final class AggregatedByteContent extends AbstractByteContent implements ExpandableByteContent {

	/**
	 * Aggregated content parts.
	 */
	private final List<ByteContent> parts = new ArrayList<ByteContent>();

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to content length.
	 */
	@Override public synchronized byte getByteAt(final int index) {
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
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to content length.
	 */
	@Override public synchronized void setByteAt(final int index, final byte b) {
		if (index < 0 || index >= length()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		int offset = 0;
		for (final ByteContent content : parts) {
			if (index < offset + content.length()) {
				content.setByteAt(index - offset, b);
			}
			offset += content.length();
		}
		throw new RuntimeException("You should never see this message.");
	}

	/**
	 * Appends an additional content to the aggregation. Added content is put on the
	 * end of the aggregation.
	 *
	 * @param content an added content.
	 *
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	@Override public synchronized void append(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		setLength(length() + content.length());
		parts.add(content);
	}

	/**
	 * Prepends an additional content to the aggregation. Added content is put on the
	 * beginning of the aggregation.
	 *
	 * @param content an added content.
	 *
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	@Override public synchronized void prepend(final ByteContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		setLength(length() + content.length());
		parts.add(0, content);
	}

}
