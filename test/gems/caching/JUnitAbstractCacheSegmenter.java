package gems.caching;

import gems.NumericValueOutOfRangeException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code AbstractCacheSegmenter} skeleton implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractCacheSegmenter {

	/**
	 * Specifies a max number of segments for which is segments limit tested.
	 */
	private static final int REPETITIONS = 100;

	/**
	 * Checks whether a negative number of segments is forbidden.
	 */
	@Test(expected = NumericValueOutOfRangeException.class) public void constructorRefusesNegativeArgument() {
		new MockAbstractCacheSegmenterImpl(-1);
	}

	/**
	 * Checks whether a zero as a number of segments is forbidden.
	 */
	@Test(expected = NumericValueOutOfRangeException.class) public void constructorRefusesZeroArgument() {
		new MockAbstractCacheSegmenterImpl(0);
	}

	/**
	 * Checks whether 1 is accepted as number of segments.
	 */
	@Test public void constructorAcceptsOne() {
		new MockAbstractCacheSegmenterImpl(1);
	}

	/**
	 * Checks whether the {@code maxSegments()} returns the proper number of segments.
	 */
	@Test public void returningMaxSegmentsWorks() {
		for (int i = 1; i <= REPETITIONS; i++) {
			Assert.assertEquals(i, new MockAbstractCacheSegmenterImpl(i).maxSegments());
		}
	}

	/**
	 * A mock implementation of the {@code AbstractCacheSegmenter} skeleton implementation.
	 */
	private static final class MockAbstractCacheSegmenterImpl extends AbstractCacheSegmenter<Object> {

		/**
		 * Creates a new segmenter with given number of segments.
		 *
		 * @param segments number of segments.
		 */
		private MockAbstractCacheSegmenterImpl(final int segments) {
			super(segments);
		}

		@Override public int getSegment(final Object key) {
			return 0; // Just a mock implementation; not used anywhere.
		}

	}

}
