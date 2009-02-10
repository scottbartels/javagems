package gems.io;

import org.junit.Before;
import org.junit.Test;

public final class JUnitAtomicByteContent extends JUnitByteContentImplementations {

	@Before public void setUp() {
		setUp(new AtomicByteContent(ABCDEF.getBytes(), INIT_LENGTH));
	}

	@Test public void test() {
		runAllTests();
	}

	@Test(expected = IllegalArgumentException.class) public void contentCannotBeNull() {
		new AtomicByteContent(null, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void lengthCannotBeNegative() {
		new AtomicByteContent(new byte[0], -1);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void lengthCannotBeGreaterThanContentLength() {
		new AtomicByteContent(new byte[]{(byte) 0}, 2);
	}

}
