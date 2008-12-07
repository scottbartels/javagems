package gems.io;

abstract class AbstractNonAggregatedByteContent extends AbstractByteContent {

	/**
	 * An 'unsupported' implementation of method; it always throws {@code UnsupportedOperationException}.
	 *
	 * @param content ignored.
	 * @throws UnsupportedOperationException always.
	 */
	public final void addContent(final ByteContent content) {
		throw new UnsupportedOperationException();
	}

}
