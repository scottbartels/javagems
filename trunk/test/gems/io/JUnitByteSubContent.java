package gems.io;

import gems.Bounds;
import org.junit.Test;
import org.junit.Before;

public final class JUnitByteSubContent extends JUnitByteContentImplementations {
	
	private static final Bounds BOUNDS = new Bounds(0, INIT_LENGTH);

	@Before public void setUp() {
		setUp(new ByteSubContent(new AtomicByteContent(ABCDEF.getBytes()), BOUNDS));
	}

	@Test public void test() {
		runAllTests();
	}

}
