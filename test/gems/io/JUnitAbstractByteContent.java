package gems.io;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * JUnit tests for the {@code AbstractByteContent} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitAbstractByteContent {

	/**
	 * A tested fixture.
	 */
	private AbstractByteContent fixture;

	/**
	 * Creates a new tested fixture.
	 */
	@Before public void setUp() {
		fixture = new AbstractByteContentMock();
	}

	/**
	 * Checks whether a {@code null} value is not accepted as subcontent boudns.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullBoundsAreForbidden() {
		fixture.getSubcontent(null);
	}

	/**
	 * Checks whether a negative length is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void negativeLengthIsForbidden() {
		fixture.setLength(-1);
	}

	/**
	 * Checks whether a storing of length works.
	 */
	@Test public void lengthStoringWorks() {
		Assert.assertEquals(0, fixture.length());
		fixture.setLength(1);
		Assert.assertEquals(1, fixture.length());
		fixture.setLength(2);
		Assert.assertEquals(2, fixture.length());
	}

	/**
	 * A mock implemenation of the {@code AbstractByteContent} abstract class.
	 */
	private static final class AbstractByteContentMock extends AbstractByteContent {

		@Override public byte getByteAt(final int index) {
			return (byte) 0;
		}

		@Override public void setByteAt(final int index, final byte b) {
			// nothing here, not a subject of testing.
		}
	}
}
