package gems.io;

import gems.Bounds;
import gems.UnexpectedNullException;
import org.junit.Before;
import org.junit.Test;

public final class JUnitByteSubContent extends JUnitByteContentImplementations {

	private static final Bounds BOUNDS = new Bounds(0, INIT_LENGTH);

	ByteContent localFixture;

	@Before public void setUp() {
		setUp(new ByteSubContent(new AtomicByteContent(ABCDEF.getBytes()), BOUNDS));
		localFixture = new ByteSubContent(new AtomicByteContent(new byte[]{(byte) 0}), new Bounds(0, 0));
	}

	@Test public void test() {
		runAllTests();
	}

	@Test(expected = UnexpectedNullException.class) public void nullSourceContentIsForbidden() {
		new ByteSubContent(null, BOUNDS);
	}

	@Test(expected = UnexpectedNullException.class) public void nullBoundsAreForbidden() {
		new ByteSubContent(ByteContent.EMPTY_CONTENT, null);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void tooWideBoundsAreForbidden() {
		new ByteSubContent(ByteContent.EMPTY_CONTENT, BOUNDS);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForSetterCannotBeNegative() {
		localFixture.getByteAt(-1);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForSetterHaveToBeLessThanContentLength() {
		localFixture.getByteAt(1);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForGetterCannotBeNegative() {
		localFixture.setByteAt(-1, (byte) 0);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForGetterHaveToBeLessThanContentLength() {
		localFixture.setByteAt(1, (byte) 0);
	}

}
