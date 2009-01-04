package gems.io;

/**
 * Objects implementing this interface encapsulates raw byte content.
 * This interface and its implementations are designed to allow aggregating
 * byte contents or using a subcontent of the byte content without necessity
 * to copy the byte content itself back and forth. Please note that there
 * are many ways how to implement a behavior specified by this interface.
 * Consult documentation of a particular implementation when in doubts.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
interface ExpandableByteContent extends ByteContent {

	/**
	 * Appends an additional content at the end of this content. This
	 * is an optional operation and it may not be supported by all
	 * implementations.
	 *
	 * @param content added content.
	 */
	void append(ByteContent content);

	/**
	 * Prepends an additional content at the beginning of this content.
	 * This is an optional operation and it may not be supported by all
	 * implementations.
	 *
	 * @param content added content.
	 */
	void prepend(ByteContent content);

}
