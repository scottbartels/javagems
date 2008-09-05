package gems.logging;

import static gems.logging.LoggingSeverity.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.Iterator;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLoggingTags {

	private static final LoggingFacility FACILITY_SECURITY = LoggingFacility.getFacility("SECURITY");
	private static final LoggingFacility FACILITY_PERFORMANCE = LoggingFacility.getFacility("PEFRORMANCE");
	private static final LoggingFacility FACILITY_UNUSED = LoggingFacility.getFacility("UNUSED");

	private static final LoggingTag[] TAGS = new LoggingTag[]{
			new LoggingTag(FACILITY_PERFORMANCE, DEBUG),
			new LoggingTag(FACILITY_PERFORMANCE, ALERT),
			new LoggingTag(FACILITY_SECURITY, WARNING),
			new LoggingTag(FACILITY_SECURITY, INFO)
	};

	/**
	 * A tested instance.
	 */
	private LoggingTags tags;

	/**
	 * Initializes a test fixture.
	 */
	@Before public void init() {
		tags = new LoggingTags(TAGS);
	}

	/**
	 * Checks whether a {@code null} value as a tags array is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullAsTagsIsForbidden() {
		new LoggingTags(null);
	}

	/**
	 * Checks whether an empty array of logging tags is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void emptyTagsAreForbidden() {
		new LoggingTags(new LoggingTag[0]);
	}

	/**
	 * Checks whether a {@code null} value as a logging tag is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullElementIsForbidden() {
		new LoggingTags(new LoggingTag[]{null}); 
	}

	/**
	 * Checks whether a provided iterator is immutable.
	 */
	@Test(expected = UnsupportedOperationException.class) public void iteratorIsImmutable() {
		final Iterator<LoggingTag> iterator = tags.iterator();
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
			Assert.fail(); // at least one remove() method calling succeeds.
		}
	}

	/**
	 * Checks whether a provided iterator has correct size.
	 */
	@Test public void testIteratorSize() {
		int counter = 0;
		for (final LoggingTag tag : tags) {
			counter++;
		}
		Assert.assertEquals(2, counter);
	}

	/**
	 * Checks whether a maximal severity is identified correctly.
	 */
	@Test public void maximalSeverityIdentifiedCorrectly() {
		Assert.assertEquals(ALERT, tags.getMaximalSeverity());
	}

	/**
	 * Checks whether a {@code null} value is forbidden as severity in {@code isSevere(LoggingSeverity)} method.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullAsSeverityIsForbiddenByIsSevere() {
		tags.isSevere(null);
	}

	/**
	 * Checks whether a {@code isSevere(LoggingSeverity)} works as expected.
	 */
	@Test public void testIsSevereSeverity() {
		Assert.assertFalse(tags.isSevere(FATAL));
		Assert.assertFalse(tags.isSevere(CRITICAL));
		Assert.assertTrue(tags.isSevere(ALERT));
		Assert.assertTrue(tags.isSevere(WARNING));
		Assert.assertTrue(tags.isSevere(NOTICE));
		Assert.assertTrue(tags.isSevere(INFO));
		Assert.assertTrue(tags.isSevere(VERBOSE));
		Assert.assertTrue(tags.isSevere(DEBUG));
		Assert.assertTrue(tags.isSevere(TRACE));
	}
	
	/**
	 * Checks whether a {@code null} value is forbidden as severity by {@code hasSeverity(LoggingSeverity)} method.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullAsSeverityIsForbiddenByHasSeverity() {
		tags.hasSeverity(null);
	}

	/**
	 * Checks whether a {@code hasSeverity(LoggingSeverity)} works as expected.
	 */
	@Test public void testHasSeveritySeverity() {
		Assert.assertFalse(tags.hasSeverity(FATAL));
		Assert.assertFalse(tags.hasSeverity(CRITICAL));
		Assert.assertTrue(tags.hasSeverity(ALERT));
		Assert.assertTrue(tags.hasSeverity(WARNING));
		Assert.assertFalse(tags.hasSeverity(NOTICE));
		Assert.assertFalse(tags.hasSeverity(INFO));
		Assert.assertFalse(tags.hasSeverity(VERBOSE));
		Assert.assertFalse(tags.hasSeverity(DEBUG));
		Assert.assertFalse(tags.hasSeverity(TRACE));
	}

}
