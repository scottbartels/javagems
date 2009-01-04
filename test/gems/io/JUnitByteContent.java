package gems.io;

import static gems.io.ByteContent.EMPTY_CONTENT;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code ByteContent} interface.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitByteContent {

	/**
	 * Checks empty content's length.
	 */
	@Test public void checkEmptyContentLength() {
		Assert.assertEquals(0, EMPTY_CONTENT.length());
		Assert.assertEquals(0, EMPTY_CONTENT.getBytes().length);
	}

	/**
	 * Checks whether the emtpy content is immutable.
	 */
	@Test(expected = UnsupportedOperationException.class) public void checkEmptyContentImmutability() {
		EMPTY_CONTENT.append(EMPTY_CONTENT);
	}

}
