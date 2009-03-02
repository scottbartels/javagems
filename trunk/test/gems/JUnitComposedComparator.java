package gems;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests for the {@code ComposedComparator} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
@SuppressWarnings({"unchecked"}) public final class JUnitComposedComparator {

	/**
	 * Checks whether a {@code null} value as a collection of comparators is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullCollectionIsForbidden() {
		new ComposedComparator(null);
	}

	/**
	 * Checks whether a {@code null} value as one of wrapped comparators is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullCollectionElementIsForbidden() {
		final Collection c = new HashSet();
		c.add(null);
		new ComposedComparator(c);
	}

	/**
	 * Checks whether an empty collection of comparators is accepted.
	 */
	@Test public void emptyCollectionIsAccepted() {
		final ComposedComparator fixture = new ComposedComparator(Collections.EMPTY_SET);
		Assert.assertEquals(0, fixture.compare(new Object(), new Object()));
	}

	/**
	 * Checks whether the first compared object cannot be {@code null}.
	 */
	@Test(expected = IllegalArgumentException.class) public void fistOperandCannotBeNull() {
		new ComposedComparator(Collections.EMPTY_SET).compare(null, new Object());
	}

	/**
	 * Checks whether the second compared object cannot be {@code null}.
	 */
	@Test(expected = IllegalArgumentException.class) public void secondOperandCannotBeNull() {
		new ComposedComparator(Collections.EMPTY_SET).compare(new Object(), null);
	}

	private static final Person JILL_30 = new Person("Jill", 30);
	private static final Person JOHN_10 = new Person("John", 10);
	private static final Person JANE_10 = new Person("Jane", 10);
	private static final Person JOHN_20 = new Person("John", 20);

	/**
	 * This list of persons is going to be sorted using a composed
	 * comparator and elements order is going to be checkd after sorting.
	 */
	private List<Person> persons;

	/**
	 * Setting up the new list of persons for each test.
	 */
	@Before public void setUp() {
		persons = new LinkedList<Person>();
		persons.add(JILL_30);
		persons.add(JOHN_10);
		persons.add(JANE_10);
		persons.add(JOHN_20);

	}

	/**
	 * Checks a sorting using a composed comparator containing only
	 * one underlaying comparator comparing persons by their names.
	 */
	@Test public void singleComparatorTestI() {
		final Collection<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();
		comparators.add(new NameComparator());
		final ComposedComparator<Person> fixture = new ComposedComparator<Person>(comparators);
		Collections.sort(persons, fixture);
		Assert.assertSame(JANE_10, persons.get(0));
		Assert.assertSame(JILL_30, persons.get(1));
		// nothing certain about the last two
	}

	/**
	 * Checks a sorting using a composed comparator containing only
	 * one underlaying comparator comparing persons by their age.
	 */
	@Test public void singleComparatorTestII() {
		final Collection<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();
		comparators.add(new AgeComparator());
		final ComposedComparator<Person> fixture = new ComposedComparator<Person>(comparators);
		Collections.sort(persons, fixture);
		// nothing certain about the first two
		Assert.assertSame(JOHN_20, persons.get(2));
		Assert.assertSame(JILL_30, persons.get(3));
	}

	/**
	 * Checks a sorting using two different comparators, the first one
	 * sorts persons by their names, the second one sorts by age.
	 */
	@Test public void multipleComparatorsTestI() {
		final Collection<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();
		comparators.add(new NameComparator());
		comparators.add(new AgeComparator());
		final ComposedComparator<Person> fixture = new ComposedComparator<Person>(comparators);
		Collections.sort(persons, fixture);
		Assert.assertSame(JANE_10, persons.get(0));
		Assert.assertSame(JILL_30, persons.get(1));
		Assert.assertSame(JOHN_10, persons.get(2));
		Assert.assertSame(JOHN_20, persons.get(3));
	}

	/**
	 * Checks a sorting using two different comparators, the first one
	 * sorts persons by age, the second one sorts by their names.
	 */
	@Test public void multipleComparatorsTestII() {
		final Collection<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();
		comparators.add(new AgeComparator());
		comparators.add(new NameComparator());
		final ComposedComparator<Person> fixture = new ComposedComparator<Person>(comparators);
		Collections.sort(persons, fixture);
		Assert.assertSame(JANE_10, persons.get(0));
		Assert.assertSame(JOHN_10, persons.get(1));
		Assert.assertSame(JOHN_20, persons.get(2));
		Assert.assertSame(JILL_30, persons.get(3));
	}

	/**
	 * Checks a sorting using two different comparators, the first one
	 * sorts persons by age, the second one sorts by their names. In this
	 * test case, input collection contains one comparator more times.
	 */
	@Test public void repeatingComparatorsTestII() {
		final AgeComparator ageComparator = new AgeComparator();
		final NameComparator nameComparator = new NameComparator();
		final Collection<Comparator<Person>> comparators = new LinkedList<Comparator<Person>>();
		comparators.add(ageComparator);
		comparators.add(nameComparator);
		comparators.add(nameComparator);
		comparators.add(ageComparator);
		final ComposedComparator<Person> fixture = new ComposedComparator<Person>(comparators);
		Collections.sort(persons, fixture);
		Assert.assertSame(JANE_10, persons.get(0));
		Assert.assertSame(JOHN_10, persons.get(1));
		Assert.assertSame(JOHN_20, persons.get(2));
		Assert.assertSame(JILL_30, persons.get(3));
	}

	/**
	 * A persion - the entity described by two attributes, age and name.
	 */
	private static final class Person {

		/**
		 * Age.
		 */
		private final int age;

		/**
		 * Name.
		 */
		private final String name;

		/**
		 * Creates a new person with given attributes.
		 *
		 * @param name a name.
		 * @param age age.
		 */
		private Person(final String name, final int age) {
			this.age = age;
			this.name = name;
		}

	}

	/**
	 * Compares two persons according their age.
	 */
	private static final class AgeComparator implements Comparator<Person> {

		/**
		 * Returns negative integer, zero, or positive integer if the person given
		 * byt the first attribute {@code x} is younger, has the same age or is older
		 * then the second persion given by the attribute {@code y} respectively.
		 *
		 * @param x the first compared person.
		 * @param y the second compared person.
		 *
		 * @return the result of comparison as described above.
		 */
		public int compare(final Person x, final Person y) {
			return x.age - y.age;
		}

	}

	/**
	 * Compares two persons according their names.
	 */
	private static final class NameComparator implements Comparator<Person> {

		/**
		 * Returns the result of lexicographical comparison of persons' names.
		 *
		 * @param x the first compared person.
		 * @param y the second compared person.
		 *
		 * @return the result of comparison as described above.
		 */
		public int compare(final Person x, final Person y) {
			return x.name.compareTo(y.name);
		}

	}

}
