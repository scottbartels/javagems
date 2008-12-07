package gems.io;

import gems.Bounds;

/**
 * Common parts of the {@code ByteContent} interface implementation. It holds
 * content length and implements 'unsupported' version of optional operation
 * {@code addContent(ByteContent)}.
 *
 * @author Jozef BABJAK
 */
abstract class AbstractByteContent implements ByteContent {

	/**
	 * Content length.
	 */
	private int length;

	/**
	 * Sets a content length.
	 *
	 * @param length a new length.
	 */
	protected void setLength(final int length) {
		this.length = length;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int length() {
		return length;
	}

	/**
	 * {@inheritDoc}
	 */
	public final byte[] getBytes() {
		final byte[] result = new byte[length];
		for (int index = 0; index < length; index++) {
			result[index] = getByteAt(index);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ByteContent getSubcontent(final Bounds bounds) {
		return new ByteSubContent(this, bounds);
	}

	/**
	 * Provides a human-readable representation of stored content; suitable only for debug purpose.
	 *
	 * @return a human-readable representation of stored content
	 */
	@Override public String toString() {
		return new String(getBytes()).replaceAll("\r", "<CR>").replaceAll("\n", "<LF>").replaceAll("\t", "<TAB>");
	}

}
