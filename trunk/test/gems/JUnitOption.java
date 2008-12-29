package gems;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@code Option} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitOption {

	/**
	 * Checks whether value presence returns {@code false} if no valus is stored.
	 */
	@Test public void hasValueReturnsFalseForNone() {
		Assert.assertFalse(new Option<Object>(null).hasValue());
	}

	/**
	 * Checks whether value presence returns {@code true} if a value is  stored.
	 */
	@Test public void hasValueReturnsTrueForSome() {
		Assert.assertTrue(new Option<Object>(new Object()).hasValue());
	}

	/**
	 * Checks whether unchecked value retrieval from the option without any value throws a runtime exception.
	 */
	@Test(expected = IllegalStateException.class) public void uncheckedRetrievalOfNoneThrowsRTE() {
		new Option<Object>(null).getValue();
	}

	/**
	 * Checks whether unchecked value retrieval from the option with a value throws a runtime exception.
	 */
	@Test(expected = IllegalStateException.class) public void uncheckedRetrievalOfSomeThrowsRTE() {
		new Option<Object>(new Object()).getValue();
	}

	/**
	 * Checks whether checked value retrieval from the option whithout any value returns {@code null}.
	 */
	@Test public void retrievingNoneReturnsNull() {
		final Option<Object> fixture = new Option<Object>(null);
		if (fixture.hasValue()) {
			Assert.fail();
		} else {
			Assert.assertNull(fixture.getValue());
		}
	}

	/**
	 * Checks whether checked value retrieval from the option with a value returns the expected object.
	 */
	@Test public void retrievingSomeReturnsExpectedObject() {
		final Object stored = new Object();
		final Option<Object> fixture = new Option<Object>(stored);
		if (fixture.hasValue()) {
			final Object retrived = fixture.getValue();
			Assert.assertSame(stored, retrived);
		} else {
			Assert.fail();
		}
	}

}
