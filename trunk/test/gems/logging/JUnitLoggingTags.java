package gems.logging;

import gems.UnexpectedNullException;
import static gems.logging.LoggingSeverity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

/**
 * Unit tests for {@code LoggingTags} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLoggingTags {

	/**
	 * Security facility.
	 */
	private static final LoggingFacility FACILITY_SECURITY = LoggingFacility.getFacility("SECURITY");

	/**
	 * Performance facility.
	 */
	private static final LoggingFacility FACILITY_PERFORMANCE = LoggingFacility.getFacility("PEFRORMANCE");

	/**
	 * Unsused facility.
	 */
	private static final LoggingFacility FACILITY_UNUSED = LoggingFacility.getFacility("UNUSED");

	/**
	 * Logging tags.
	 */
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
	@Before public void setUp() {
		tags = new LoggingTags(TAGS);
	}

	/**
	 * Checks whether a {@code null} value as a tags array is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsTagsIsForbidden() {
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
	@Test(expected = UnexpectedNullException.class) public void nullElementIsForbidden() {
		new LoggingTags(new LoggingTag[]{null});
	}

	/**
	 * Checks whether a provided iterator is immutable.
	 */
	@Test(expected = UnsupportedOperationException.class) public void iteratorIsImmutable() {
		final Iterator<LoggingTag> iterator = tags.iterator();
		while (iterator.hasNext()) {
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
	@Test(expected = UnexpectedNullException.class) public void nullAsSeverityIsForbiddenByIsSevere() {
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
	@Test(expected = UnexpectedNullException.class) public void nullAsSeverityIsForbiddenByHasSeverity() {
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

	/**
	 * Checks whether a {@code hasFacility(LoggingFacility)} method forbids {@code null} argument.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsFacilityIsForbiddenByHasFacility() {
		tags.hasFacility(null);
	}

	/**
	 * Checks whether a {@code hasFacility(LoggingFacility)} method works as expected.
	 */
	@Test public void testHasFacility() {
		Assert.assertTrue(tags.hasFacility(FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.hasFacility(FACILITY_SECURITY));
		Assert.assertFalse(tags.hasFacility(FACILITY_UNUSED));
		Assert.assertFalse(tags.hasFacility(LoggingFacility.NULL_FACILITY));
	}


	/**
	 * Checks whether a {@code null} value as a facility is forbidden by a {@code getSeverity(LoggingFacility)} method.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullIsForbiddenByGetSevetiy() {
		tags.getSeverity(null);
	}

	/**
	 * Checks whether a {@code getSeverity(LoggingFacility)} works as exepected.
	 */
	@Test public void testGetSeverity() {
		Assert.assertEquals(ALERT, tags.getSeverity(FACILITY_PERFORMANCE));
		Assert.assertEquals(WARNING, tags.getSeverity(FACILITY_SECURITY));
		Assert.assertNull(tags.getSeverity(FACILITY_UNUSED));
	}

	/**
	 * Checks whether a {@code null} value as a severity in
	 * a {@code isSevere(LoggingSeverity,LoggingFacility)}
	 * method is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsSeverityIsForbiddenByIsSevereSeverityFacility() {
		tags.isSevere(null, LoggingFacility.NULL_FACILITY);
	}

	/**
	 * Checks whether a {@code null} value as a facility in
	 * a {@code isSevere(LoggingSeverity,LoggingFacility)}
	 * method is forbidden.
	 */
	@Test(expected = UnexpectedNullException.class) public void nullAsFacilityIsForbiddenByIsSevereSeverityFacility() {
		tags.isSevere(ALERT, null);
	}

	/**
	 * Checks whether a {@code isSevere(LoggingSeverity,LoggingFacility)} method works as expected.
	 */
	@Test public void testIsSevereSeverityFacility() {
		Assert.assertFalse(tags.isSevere(FATAL, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(CRITICAL, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(ALERT, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(WARNING, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(NOTICE, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(INFO, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(VERBOSE, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(DEBUG, FACILITY_UNUSED));
		Assert.assertFalse(tags.isSevere(TRACE, FACILITY_UNUSED));

		Assert.assertFalse(tags.isSevere(FATAL, FACILITY_PERFORMANCE));
		Assert.assertFalse(tags.isSevere(CRITICAL, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(ALERT, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(WARNING, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(NOTICE, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(INFO, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(VERBOSE, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(DEBUG, FACILITY_PERFORMANCE));
		Assert.assertTrue(tags.isSevere(TRACE, FACILITY_PERFORMANCE));

		Assert.assertFalse(tags.isSevere(FATAL, FACILITY_SECURITY));
		Assert.assertFalse(tags.isSevere(CRITICAL, FACILITY_SECURITY));
		Assert.assertFalse(tags.isSevere(ALERT, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(WARNING, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(NOTICE, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(INFO, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(VERBOSE, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(DEBUG, FACILITY_SECURITY));
		Assert.assertTrue(tags.isSevere(TRACE, FACILITY_SECURITY));
	}

}
