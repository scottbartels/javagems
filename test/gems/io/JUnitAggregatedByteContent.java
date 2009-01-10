package gems.io;

import gems.Bounds;
import org.junit.Before;
import org.junit.Test;

public final class JUnitAggregatedByteContent extends JUnitByteContentImplementations {

	private static final Bounds BOUNDS = new Bounds(2, 4);

	@Before public void setUp() {
		final ExpandableByteContent content = new AggregatedByteContent();
		content.append(new AtomicByteContent(ABCDEF.getBytes()).getSubcontent(BOUNDS));
		content.prepend(new AtomicByteContent(ABCDEF.getBytes(), 2));
		setUp(content);
	}

	@Test public void test() {
		runAllTests();
	}

}
