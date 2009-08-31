package gems.caching;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code CacheItemStatistics} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitCacheItemStatistics {

	/**
	 * A tested fixture.
	 */
	private CacheItemStatistics<Object> fixture;

	/**
	 * Creates a new fixture for each test.
	 */
	@Before public void setUp() {
		fixture = new CacheItemStatistics<Object>(new Object());
	}

	/**
	 * Checks whether a {@code null} id is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullIdIsForbidden() {
		new CacheItemStatistics<Object>(null);
	}

	@Test public void newlyCreatedObjectIsNotSnapshot() {
		Assert.assertFalse(fixture.isSnapshot());
	}

	@Test public void snapshotIsSnapshot() {
		Assert.assertTrue(fixture.getSnapshot().isSnapshot());
	}

	@Test public void snapshotIsCached() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertSame(s1, s2);
	}

	@Test public void snapshotOfSnapshotIsTheSameSnapshot() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		final CacheItemStatistics<Object> s2 = s1.getSnapshot();
		Assert.assertSame(s1, s2);
	}

	@Test(expected = IllegalArgumentException.class) public void negativeSizeIsNotAllowed() {
		fixture.recordSize(-1L);
	}

	@Test(expected = IllegalStateException.class) public void recordingSizeOnSnapshotIsNotAllowed() {
		fixture.getSnapshot().recordSize(0L);
	}

	@Test public void snapshotIsDifferentAfterSizeChange() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordSize(0L);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertNotSame(s1, s2);
	}

	@Test(expected = IllegalStateException.class) public void gettingSizeFromLiveObjectIsNotAllowed() {
		fixture.getSize();
	}

	@Test public void recordingSizeWorks() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordSize(42L);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		fixture.recordSize(1024L);
		final CacheItemStatistics<Object> s3 = fixture.getSnapshot();
		Assert.assertEquals(0L, s1.getSize());
		Assert.assertEquals(42L, s2.getSize());
		Assert.assertEquals(1024L, s3.getSize());
	}

	@Test(expected = IllegalStateException.class) public void recordingHitOnSnapshotIsNotAllowed() {
		recordingAccessOnSnapshotIsNotAllowedImpl(true);
	}

	@Test(expected = IllegalStateException.class) public void recordingMissOnSnapshotIsNotAllowed() {
		recordingAccessOnSnapshotIsNotAllowedImpl(false);
	}

	private void recordingAccessOnSnapshotIsNotAllowedImpl(final boolean hit) {
		fixture.getSnapshot().recordAccess(hit);
	}

	@Test public void snapshotIsDifferentAfterHitRecord() {
		snapshotIsDifferentAfterAccessRecordImpl(true);
	}

	@Test public void snapshotIsDifferentAfterMissRecord() {
		snapshotIsDifferentAfterAccessRecordImpl(false);
	}

	private void snapshotIsDifferentAfterAccessRecordImpl(final boolean hit) {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordAccess(hit);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertNotSame(s1, s2);
	}

	@Test(expected = IllegalStateException.class) public void gettingHitsFromLiveObjectIsNotAllowed() {
		fixture.getHits();
	}

	@Test(expected = IllegalStateException.class) public void gettingMissesFromLiveObjectIsNotAllowed() {
		fixture.getMisses();
	}

	@Test(expected = IllegalStateException.class) public void gettingAccessesFromLiveObjectIsNotAllowed() {
		fixture.getAccesses();
	}

	@Test public void recordingAccessWorks() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordAccess(true);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		fixture.recordAccess(false);
		final CacheItemStatistics<Object> s3 = fixture.getSnapshot();
		Assert.assertEquals(0L, s1.getHits());
		Assert.assertEquals(0L, s1.getMisses());
		Assert.assertEquals(0L, s1.getAccesses());

		Assert.assertEquals(1L, s2.getHits());
		Assert.assertEquals(0L, s2.getMisses());
		Assert.assertEquals(1L, s2.getAccesses());

		Assert.assertEquals(1L, s3.getHits());
		Assert.assertEquals(1L, s3.getMisses());
		Assert.assertEquals(2L, s3.getAccesses());		
	}

	@Test(expected = IllegalStateException.class) public void gettingLastAccessFromLiveObjectIsNotAllowed() {
		fixture.getLastAccess();
	}

	@Test public void recordingHitUpdatesLastAccessTimestamp() {
		recordingAccessUpdatesLastAccessTimestempImpl(true);
	}

	@Test public void recordingMissUpdatesLastAccessTimestamp() {
		recordingAccessUpdatesLastAccessTimestempImpl(false);
	}

	private void recordingAccessUpdatesLastAccessTimestempImpl(final boolean hit) { // XXX: THIS CAN BE TRICKY
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		safeSleep(200);
		fixture.recordAccess(hit);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertTrue(s2.getLastAccess() - s1.getLastAccess() > 200);
	}

	// todo: updating last access time

	// TODO: TU SOM SKONCIL

	private static void safeSleep(final long delay) {
		try {
			Thread.sleep(delay);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
