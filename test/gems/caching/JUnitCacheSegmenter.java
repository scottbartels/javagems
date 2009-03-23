package gems.caching;

import org.junit.Test;
import org.junit.Assert;

/**
 * Unit tests for the {@code CacheSegmenter} interface null-implementation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCacheSegmenter {

	/**
	 * Specifies how many different objects are used for segmentation testing.
	 */
	private static final int REPETITIONS = 100;

	/**
	 * Checks whether the null-implementation forbids a {@code null} key.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullImplementationRefusesNullKey() {
		CacheSegmenter.NULL_SEGMENTER.getSegment(null);
	}

	/**
	 * Checks whether the null-implementation always returns zero.
	 */
	@Test public void nullImplementationAlwaysReturnsZero() {
		for (int i = 0; i < REPETITIONS; i++) {
			Assert.assertEquals(0, CacheSegmenter.NULL_SEGMENTER.getSegment(new Object()));
		}
	}
	
}
