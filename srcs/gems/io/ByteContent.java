package gems.io;

import gems.Bounds;

/**
 * Objects implementing this interface encapsulates raw byte content.
 * This interface and its implementations are designed to avoid a raw
 * content copying among objects. Different views to only one real
 * content are provided.
 *
 * @author Jozef BABJAK
 */
public interface ByteContent {

	/**
	 * Empty content.
	 */
	ByteContent EMPTY_CONTENT = new BasicByteContent(new byte[0]);

	/**
	 * Returns length of stored content in bytes.
	 *
	 * @return length of stored content in bytes.
	 */
	int length();

	/**
	 * Returns stored content as a byte array.
	 *
	 * @return stored content as a byte array.
	 */
	byte[] getBytes();

	/**
	 * Retuns a byte with a given index. Implementations
	 * should throw {@code IndexOutOfBoundException} if
	 * a given index has inappropriate value.
	 *
	 * @param index an index of requested byte.
	 * @return a byte with a given index.
	 */
	byte getByteAt(int index);

	/**
	 * Adds an additional content. This is optional operation and
	 * implementations which do not support this operation may
	 * throw runtime exception.
	 *
	 * @param content added content.
	 */
	void addContent(ByteContent content);

	/**
	 * Returns a subcontent of the content specified by given bounds.
	 *
	 * @param bounds required bounds of a subcontent.
	 * @return a subcontent of the content specified by given bounds.
	 */
	ByteContent getSubcontent(Bounds bounds);

}
