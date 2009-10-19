package gems.caching;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code HashCodeBasedSegmenter} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitHashCodeBasedSegmenter {

	private static final int REPETITIONS = 512;

	private static final int MIN = -128;

	private static final int MAX = 256;

	/**
	 * Checks whether {@code null} is forbidden as key.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullKeyIsForbidden() {
		new HashCodeBasedSegmenter<Object>(1).getSegment(null);
	}

	/**
	 * Checks whether segment number are in range.
	 */
	@Test public void testSegmentation() {
		for (int segments = 1; segments <= REPETITIONS; segments++) {
			final CacheSegmenter<Integer> fixture = new HashCodeBasedSegmenter<Integer>(segments);
			for (int key = MIN; key <= MAX; key++) {
				final int segment = fixture.getSegment(key);
				Assert.assertTrue(segment >= 0);
				Assert.assertTrue(segment < segments);
			}
			final int segment = fixture.getSegment(Integer.MIN_VALUE);
			Assert.assertTrue(segment >= 0);
			Assert.assertTrue(segment < segments);
		}
	}

	/**
	 * Checks a behavior for a hash code of {@code Integer.MIN_VALUE}, which is quite tricky.
	 */
	@Test public void testExtramallCases() {
		for (int segments = 1; segments <= REPETITIONS; segments++) {
			final int segment = new HashCodeBasedSegmenter<Integer>(segments).getSegment(Integer.MIN_VALUE);
			Assert.assertTrue(segment >= 0);
			Assert.assertTrue(segment < segments);
		}
	}

}
