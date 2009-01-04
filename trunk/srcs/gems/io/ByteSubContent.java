package gems.io;

import gems.Bounds;

/**
 * Represents content which is sub-part of another content. It allows
 * to split existing content to parts and use only a sub-part when
 * necessary. This sub-part is still backed by original content.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
final class ByteSubContent extends AbstractImmutableByteContent {

	/**
	 * Backend 'whole' content.
	 */
	private final ByteContent content;

	/**
	 * Offset of the begining of the sub-content.
	 */
	private final int offset;

	/**
	 * Creates a new subcontent of a given content specified by given bounds.
	 *
	 * @param content a 'whole' content.
	 * @param bounds requested bounds of a subcontent.
	 *
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 * @throws IndexOutOfBoundsException if it is not possible to get requested subcontent due to wrong bounds.
	 */
	ByteSubContent(final ByteContent content, final Bounds bounds) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		if (bounds == null) {
			throw new IllegalArgumentException();
		}
		if (bounds.end() > content.length()) {
			throw new IndexOutOfBoundsException(String.valueOf(bounds.end()));
		}
		this.content = content;
		this.offset = bounds.begin();
		setLength(bounds.range());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to subcontent length.
	 */
	@Override public synchronized byte getByteAt(final int index) {
		if (index < 0 || index >= length()) {
			throw new IllegalArgumentException(String.valueOf(index));
		}
		return content.getByteAt(offset + index);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IndexOutOfBoundsException if {@code index} is negative or
	 * if {@code index} is greater than or equal to subcontent length.
	 */
	@Override public synchronized void setByteAt(final int index, final byte b) {
		if (index < 0 || index >= length()) {
			throw new IllegalArgumentException(String.valueOf(index));
		}
		content.setByteAt(offset + index, b);
	}

}
