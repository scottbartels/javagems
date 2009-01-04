package gems.io;

/**
 * A common part of {@code ByteContent} interface implementations not supporting
 * optional {@code append(ByteContent)} and {@code prepend(ByteContent)} operations.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
abstract class AbstractImmutableByteContent extends AbstractByteContent {

	/**
	 * An 'unsupported' implementation of the method. It
	 * always throws {@code UnsupportedOperationException}.
	 *
	 * @param content ignored.
	 *
	 * @throws UnsupportedOperationException always.
	 */
	@Override public final void append(final ByteContent content) {
		throw new UnsupportedOperationException();
	}

	/**
	 * An 'unsupported' implementation of the method. It
	 * always throws {@code UnsupportedOperationException}.
	 *
	 * @param content ignored.
	 *
	 * @throws UnsupportedOperationException always.
	 */
	@Override public final void prepend(final ByteContent content) {
		throw new UnsupportedOperationException();
	}

}
