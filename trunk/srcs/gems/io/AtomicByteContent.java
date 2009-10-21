package gems.io;

import gems.UnexpectedNullException;

/**
 * The basic implementation of {@code ByteContent}
 * interface. It holds raw byte content data.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class AtomicByteContent extends AbstractByteContent {

	/**
	 * Content.
	 */
	private final byte[] content;

	/**
	 * Creates a new instance storing the whole given content.
	 *
	 * @param content a content.
	 *
	 * @throws IllegalArgumentException if {@code content} is {@code null}.
	 */
	public AtomicByteContent(final byte[] content) {
		this(content, content.length);
	}

	/**
	 * Creates a new instance storing a given content. Argument {@code length}
	 * specifies how many bytes from content are valid. Only valid bytes will
	 * be stored in the created instance. A defense copy of the given array is
	 * created internally, so any subsequent changes of the given array wont
	 * affect created object.
	 *
	 * @param content a content.
	 * @param length a valid lenght.
	 *
	 * @throws UnexpectedNullException if {@code content} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code length} is negative or greater than a {@code content} length.
	 */
	public AtomicByteContent(final byte[] content, final int length) {
		if (content == null) {
			throw new UnexpectedNullException();
		}
		if (length < 0 || length > content.length) {  // todo: Checks (?)
			throw new IndexOutOfBoundsException(String.valueOf(length));
		}
		this.content = new byte[length];
		System.arraycopy(content, 0, this.content, 0, length);
		setLength(length);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to content length.
	 */
	@Override public synchronized byte getByteAt(final int index) {
		if (index < 0 || index >= length()) {  // todo: Checks (?)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		return content[index];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to content length.
	 */
	@Override public synchronized void setByteAt(final int index, final byte b) {
		if (index < 0 || index >= length()) {   // todo: Checks (?)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		content[index] = b;
	}

}
