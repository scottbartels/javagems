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

}
