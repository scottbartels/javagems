package gems;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code ThreadInfo} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitThreadInfo {

	/**
	 * A name of an another thread.
	 */
	private static final String ANOTHER_NAME = "another";

	/**
	 * A current thread ID.
	 */
	private long currentThreadId;

	/**
	 * A current thread name.
	 */
	private String currentThreadName;

	/**
	 * A thread info object of the current thread.
	 */
	private ThreadInfo current;

	/**
	 * A thread info object of an another thread.
	 */
	private ThreadInfo another;

	/**
	 * Initializes fixtures.
	 */
	@Before public void init() {
		final Thread currentThread = Thread.currentThread();
		currentThreadId = currentThread.getId();
		currentThreadName = currentThread.getName();
		current = new ThreadInfo();
		another = new AnotherThread(ANOTHER_NAME).getInfo();
	}

	/**
	 * Checks wheter IDs of the current thread matches.
	 */
	@Test public void currentIdMatches() {
		Assert.assertEquals(currentThreadId, (long) current.getId());
	}

	/**
	 * Checks whether names of the current thread matches.
	 */
	@Test public void currentNameMatches() {
		Assert.assertEquals(currentThreadName, current.getName());
	}

	/**
	 * Checks whether names of the another thread matches.
	 */
	@Test public void anotherNameMatches() {
		Assert.assertEquals(ANOTHER_NAME, another.getName());
	}

	/**
	 * Checks whether IDs of the current and the another threads differ.
	 */
	@Test public void currentAndAnotherIdsDiffers() {
		Assert.assertNotSame(current.getId(), another.getId());
	}

	/**
	 * Just another thread. :-)
	 *
	 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
	 */
	private static final class AnotherThread extends Thread {

		/**
		 * A thread info object of the another thread.
		 */
		private volatile ThreadInfo info;

		/**
		 * Creates an instance of the another thread with a given
		 * name, sets it as a daemon and starts it.
		 *
		 * @param name a name of the thread.
		 */
		private AnotherThread(final String name) {
			super(name);
			setDaemon(true);
			start();
		}

		@Override public void run() {
			info = new ThreadInfo();
		}

		/**
		 * Returns a thread info object.
		 *
		 * @return a thread info object
		 */
		public ThreadInfo getInfo() {
			while (info == null) {
				yield();
			}
			return info;
		}

	}

}
