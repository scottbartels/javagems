package gems.io;

import org.junit.Assert;

abstract class JUnitByteContentImplementations {

	private ByteContent fixture;
	protected static final String ABCDEF = "abcdef";
	protected static final int INIT_LENGTH = 4;

	void setUp(final ByteContent fixture) {
		if (fixture == null) {
			throw new IllegalArgumentException();
		}
		this.fixture = fixture;
	}

	protected void runAllTests() {

		Assert.assertEquals(4, fixture.length());
		Assert.assertEquals((byte) 'a', fixture.getByteAt(0));
		Assert.assertEquals((byte) 'b', fixture.getByteAt(1));
		Assert.assertEquals((byte) 'c', fixture.getByteAt(2));
		Assert.assertEquals((byte) 'd', fixture.getByteAt(3));

		fixture.setByteAt(0, (byte) 's');

		Assert.assertEquals(4, fixture.length());
		Assert.assertEquals((byte) 's', fixture.getByteAt(0));
		Assert.assertEquals((byte) 'b', fixture.getByteAt(1));
		Assert.assertEquals((byte) 'c', fixture.getByteAt(2));
		Assert.assertEquals((byte) 'd', fixture.getByteAt(3));

		fixture.setByteAt(1, (byte) 'p');

		Assert.assertEquals(4, fixture.length());
		Assert.assertEquals((byte) 's', fixture.getByteAt(0));
		Assert.assertEquals((byte) 'p', fixture.getByteAt(1));
		Assert.assertEquals((byte) 'c', fixture.getByteAt(2));
		Assert.assertEquals((byte) 'd', fixture.getByteAt(3));

		fixture.setByteAt(2, (byte) 'q');

		Assert.assertEquals(4, fixture.length());
		Assert.assertEquals((byte) 's', fixture.getByteAt(0));
		Assert.assertEquals((byte) 'p', fixture.getByteAt(1));
		Assert.assertEquals((byte) 'q', fixture.getByteAt(2));
		Assert.assertEquals((byte) 'd', fixture.getByteAt(3));

		fixture.setByteAt(3, (byte) 'r');

		Assert.assertEquals(4, fixture.length());
		Assert.assertEquals((byte) 's', fixture.getByteAt(0));
		Assert.assertEquals((byte) 'p', fixture.getByteAt(1));
		Assert.assertEquals((byte) 'q', fixture.getByteAt(2));
		Assert.assertEquals((byte) 'r', fixture.getByteAt(3));

		Assert.assertArrayEquals("spqr".getBytes(), fixture.getBytes());

	}

}
