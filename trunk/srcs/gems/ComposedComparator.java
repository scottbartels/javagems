package gems;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * A comparator which behavior is determined by an ordered set of other
 * comparators. During the comparator creation, orderd set of comparators
 * is given. When two objects are compared using the comparator, the given
 * comparators are used in their order and the first non-zero comparision
 * result is returned. If all wrapped comparators return zero, the comparator
 * also return zero. It implies that the comparator always returns zero, if
 * an empty collection of comparators was specified during a comparator
 * creation. Note: This comparator implementation itself is serializable,
 * but for a correct serialization and deserialization all involved comparators
 * should be serializable, too.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> a type of compared objects.
 * @since 2009.01
 */
@Experimental public final class ComposedComparator<T> implements Comparator<T>, Serializable {

	/**
	 * Serialization version UID.
	 */
	private static final long serialVersionUID = 4780011141633267767L;

	/**
	 * Wrapped comparators.
	 */
	private final Collection<Comparator<? super T>> comparators = new LinkedList<Comparator<? super T>>();

	/**
	 * Creates a new composed comparator for a given collection of comparators.
	 * Iteration order of the given collection is significant: comparators will
	 * be used in that order. If an input collection contains two or more equal
	 * comparators, only the first one is used, any subsequent are silently ignored.
	 * See contract of the {@code Comparator.equals()} method for details.
	 *
	 * @param comparators a collection of comparators.
	 *
	 * @throws UnexpectedNullException if {@code comparators} or any of its elements is {@code null}.
	 */
	public ComposedComparator(final Iterable<? extends Comparator<? super T>> comparators) {
		for (final Comparator<? super T> comparator : Checks.ensureNotNull(comparators)) {
			if (this.comparators.contains(Checks.ensureNotNull(comparator))) {
				continue;
			}
			this.comparators.add(comparator);
		}
	}

	/**
	 * Compares given values using wrapped comparators in specified order.
	 * The first non-zero comparison result is returned, or zero if all
	 * comarisoins returned zero.
	 *
	 * @param x the first object to be compared.
	 * @param y the second object to be compared.
	 *
	 * @return a negative integer, zero, or a positive integer as the
	 *         first argument is less than, equal to, or greater than the
	 *         second.
	 *
	 * @throws UnexpectedNullException if any of arguments is {@code null}.
	 */
	public int compare(final T x, final T y) {
		if (x == null) {
			throw new UnexpectedNullException();
		}
		if (y == null) {
			throw new UnexpectedNullException();
		}
		for (final Comparator<? super T> comparator : comparators) {
			final int result = comparator.compare(x, y);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	/**
	 * Defines an equality of two composed comparators. Two composed comparators
	 * are considered to be equal if and only if they contains the same comparators
	 * in the same order.
	 *
	 * @param o a compared object.
	 *
	 * @return {@code true} if compared object is a composed comarator containing equal
	 *         comparators in the same order as this comparator, {@code false} otherwise.
	 */
	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ComposedComparator that = (ComposedComparator) o;
		return this.comparators.equals(that.comparators);
	}

	/**
	 * Returns a hash code of the object.
	 *
	 * @return a hash code of the object.
	 */
	@Override public int hashCode() {
		return comparators.hashCode();
	}

}
