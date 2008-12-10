package gems.io;

import gems.Bounds;

/**
 * Common parts of the {@code ByteContent} interface implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractByteContent implements ByteContent {

	/**
	 * Content length.
	 */
	private int length;

	/**
	 * {@inheritDoc}
	 */
	public final synchronized int length() {
		return length;
	}

	/**
	 * {@inheritDoc} This method always returns a defense copy, i.e. it
	 * creates a new byte array. This method never returns {@code null};
	 * if the content is empty, an emtpy array is returned.
	 */
	public final synchronized byte[] getBytes() {
		final byte[] result = new byte[length];
		for (int index = 0; index < length; index++) {
			result[index] = getByteAt(index);
		}
		return result;
	}

	/**
	 * {@inheritDoc} The returned object uses the same backing store
	 * as this object. This method never returns {@code null}.
	 *
	 * @throws IllegalArgumentException if {@code bounds} is {@code null}. 
	 */
	public final synchronized ByteContent getSubcontent(final Bounds bounds) {
		if (bounds == null) {
			throw new IllegalArgumentException();
		}
		return new ByteSubContent(this, bounds);
	}

	/**
	 * Provides a string representation of stored content, using a platform's default charset.
	 *
	 * @return a string representation of stored content.
	 */
	@Override public String toString() {
		return new String(getBytes());
	}

	/**
	 * Sets a content length.
	 *
	 * @param length a new length.
	 */
	protected final synchronized void setLength(final int length) {
		if (length < 0) {
			throw new IllegalArgumentException(String.valueOf(length));
		}
		this.length = length;
	}

}
