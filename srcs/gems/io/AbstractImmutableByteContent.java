package gems.io;

/**
 * A common part of {@code ByteContent} interface implementations
 * not supporting {@code addContent(ByteContent)} operation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractImmutableByteContent extends AbstractByteContent {

	/**
	 * An 'unsupported' implementation of the method. It
	 *  always throws {@code UnsupportedOperationException}.
	 *
	 * @param content ignored.
	 * @throws UnsupportedOperationException always.
	 */
	public final void addContent(final ByteContent content) {
		throw new UnsupportedOperationException();
	}

}
