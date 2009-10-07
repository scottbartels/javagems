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
	 * Checks whether a {@code null} ID is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullIdIsForbidden() {
		new CacheItemStatistics<Object>(null);
	}

	/**
	 * Checks whether a newly created object is a live object, not a snapshot.
	 */
	@Test public void newlyCreatedObjectIsNotSnapshot() {
		Assert.assertFalse(fixture.isSnapshot());
	}

	/**
	 * Checks whether a snapshot is is marked as snapshot.
	 */
	@Test public void snapshotIsSnapshot() {
		Assert.assertTrue(fixture.getSnapshot().isSnapshot());
	}

	/**
	 * Checks whether snapshot is cached, ie whether the same snapshot
	 * is returned more times if object was not changed meantime.
	 */
	@Test public void snapshotIsCached() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertSame(s1, s2);
	}

	/**
	 * Checks whether a snapshot of a snapshot is the same
	 * snapshot, ie unnecessary snapshots are not created.
	 */
	@Test public void snapshotOfSnapshotIsTheSameSnapshot() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		final CacheItemStatistics<Object> s2 = s1.getSnapshot();
		Assert.assertSame(s1, s2);
	}

	/**
	 * Checks whether setting a negative object size is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void negativeSizeIsNotAllowed() {
		fixture.recordSize(-1L);
	}

	/**
	 * Checks whether setting a size on the snapshot is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void recordingSizeOnSnapshotIsNotAllowed() {
		fixture.getSnapshot().recordSize(0L);
	}

	/**
	 * Checks whether size recording invalidates the cached snapshot.
	 */
	@Test public void snapshotIsDifferentAfterSizeChange() {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordSize(0L);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertNotSame(s1, s2);
	}

	/**
	 * Checks whether getting size from live object is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void gettingSizeFromLiveObjectIsNotAllowed() {
		fixture.getSize();
	}

	/**
	 * Checks whether size changes work. 
	 */
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

	/**
	 * Checks whether hit recording on snapshot is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void recordingHitOnSnapshotIsNotAllowed() {
		recordingAccessOnSnapshotIsNotAllowedImpl(true);
	}

	/**
	 * Checks whether miss recording on snapshot is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void recordingMissOnSnapshotIsNotAllowed() {
		recordingAccessOnSnapshotIsNotAllowedImpl(false);
	}

	/**
	 * Checks whether access recording on snapshot is forbidden.
	 *
	 * @param hit a flag indicating that recorded access is a hit. 
	 */
	private void recordingAccessOnSnapshotIsNotAllowedImpl(final boolean hit) {
		fixture.getSnapshot().recordAccess(hit);
	}

	/**
	 * Checks whether hit recording invalidates the cached snapshot.
	 */
	@Test public void snapshotIsDifferentAfterHitRecord() {
		snapshotIsDifferentAfterAccessRecordImpl(true);
	}

	/**
	 * Checks whether miss recording invalidates the cached snapshot.
	 */
	@Test public void snapshotIsDifferentAfterMissRecord() {
		snapshotIsDifferentAfterAccessRecordImpl(false);
	}

	/**
	 * Checks whether access recording invalidates the cached snapshot.
	 *
	 * @param hit a flag indicating that recorded access is a hit.
	 */
	private void snapshotIsDifferentAfterAccessRecordImpl(final boolean hit) {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		fixture.recordAccess(hit);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertNotSame(s1, s2);
	}

	/**
	 * Checks whether getting number of hits on live object if forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void gettingHitsFromLiveObjectIsNotAllowed() {
		fixture.getHits();
	}

	/**
	 * Checks whether getting number of mises on live object if forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void gettingMissesFromLiveObjectIsNotAllowed() {
		fixture.getMisses();
	}

	/**
	 * Checks whether getting number of accesses on live object if forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void gettingAccessesFromLiveObjectIsNotAllowed() {
		fixture.getAccesses();
	}

	/**
	 * Checks whether recording accesses works.
	 */
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

	/**
	 * Checks whether getting last access time from a live objec is forbidden.
	 */
	@Test(expected = IllegalStateException.class) public void gettingLastAccessFromLiveObjectIsNotAllowed() {
		fixture.getLastAccess();
	}

	/**
	 * Checks whether hit recording updates last access time.
	 */
	@Test public void recordingHitUpdatesLastAccessTimestamp() {
		recordingAccessUpdatesLastAccessTimestempImpl(true);
	}

	/**
	 * Checks whether miss recording updates last access time.
	 */
	@Test public void recordingMissUpdatesLastAccessTimestamp() {
		recordingAccessUpdatesLastAccessTimestempImpl(false);
	}

	/**
	 * Checks whether access recording updates last access timestamp.
	 *
	 * @param hit a flag indicating whether access is miss or hit. 
	 */
	private void recordingAccessUpdatesLastAccessTimestempImpl(final boolean hit) {
		final CacheItemStatistics<Object> s1 = fixture.getSnapshot();
		safeSleep(200);
		fixture.recordAccess(hit);
		final CacheItemStatistics<Object> s2 = fixture.getSnapshot();
		Assert.assertTrue(s2.getLastAccess() - s1.getLastAccess() > 0);
	}

	// TODO: TU SOM SKONCIL

	private static void safeSleep(final long delay) {
		try {
			Thread.sleep(delay);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
