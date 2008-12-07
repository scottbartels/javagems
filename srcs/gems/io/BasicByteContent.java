package gems.io;

/**
 * The 'leaf' implementation of content storage. It uses a byte array as a backend storage
 * and it is unmodifiable after a creation. Optional operation of adding additional content
 * is not supported by this implementation.
 *
 * @author Jozef BABJAK
 */
public final class BasicByteContent extends AbstractNonAggregatedByteContent {

	/**
	 * Content.
	 */
	private final byte[] content;

	/**
	 * Creates a new instance storing a whole given content.
	 *
	 * @param content a content.
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	public BasicByteContent(final byte[] content) {
		this(content, content.length);
	}

	/**
	 * Creates a new instance storing a given content. Argument {@code length}
	 * specifies how many bytes from content are valid.
	 *
	 * @param content a content.
	 * @param length a valid lenght.
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code length} is less than zero or greater than a {@code content} length.
	 */
	public BasicByteContent(final byte[] content, final int length) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		if (length < 0 || length > content.length) {
			throw new IndexOutOfBoundsException("Wrong length: " + length);
		}
		this.content = new byte[length];
		System.arraycopy(content, 0, this.content, 0, length);
		setLength(length);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte getByteAt(final int index) {
		if (index < 0 || index >= length()) {
			throw new IndexOutOfBoundsException("Wrong index: " + index);
		}
		return content[index];
	}

}
