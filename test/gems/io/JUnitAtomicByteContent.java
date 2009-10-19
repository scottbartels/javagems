package gems.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class JUnitAtomicByteContent extends JUnitByteContentImplementations {

	private static final byte BYTE_ZERO = (byte) 0;
	private static final byte BYTE_ONE = (byte) 1;

	private static final byte[] ONE_BYTE_ARRAY = new byte[]{BYTE_ZERO};

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
		new AtomicByteContent(ONE_BYTE_ARRAY, -1);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void lengthCannotBeGreaterThanContentLength() {
		new AtomicByteContent(ONE_BYTE_ARRAY, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForSetterCannotBeNegative() {
		new AtomicByteContent(ONE_BYTE_ARRAY).getByteAt(-1);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForSetterHaveToBeLessThanContentLength() {
		new AtomicByteContent(ONE_BYTE_ARRAY).getByteAt(ONE_BYTE_ARRAY.length);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForGetterCannotBeNegative() {
		new AtomicByteContent(ONE_BYTE_ARRAY).setByteAt(-1, BYTE_ONE);
	}

	@Test(expected = IndexOutOfBoundsException.class) public void indexForGetterHaveToBeLessThanContentLength() {
		new AtomicByteContent((ONE_BYTE_ARRAY)).setByteAt(ONE_BYTE_ARRAY.length, BYTE_ONE);
	}

	@Test public void contentModificationWorks() {
		final ByteContent fixture = new AtomicByteContent(new byte[]{BYTE_ZERO, BYTE_ONE});
		Assert.assertEquals(BYTE_ZERO, fixture.getByteAt(0));
		Assert.assertEquals(BYTE_ONE, fixture.getByteAt(1));
		fixture.setByteAt(0, BYTE_ONE);
		Assert.assertEquals(BYTE_ONE, fixture.getByteAt(0)); // first changed
		Assert.assertEquals(BYTE_ONE, fixture.getByteAt(1)); // second untouched
		fixture.setByteAt(1, BYTE_ZERO);
		Assert.assertEquals(BYTE_ONE, fixture.getByteAt(0)); // first untouched
		Assert.assertEquals(BYTE_ZERO, fixture.getByteAt(1)); // second changed.
	}

}
