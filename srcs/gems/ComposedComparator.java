package gems;

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
 * creation.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @since 2008.12
 */
@Experimental public final class ComposedComparator<T> implements Comparator<T> {

	/**
	 * Wrapped comparators.
	 */
	final Collection<Comparator<T>> comparators = new LinkedList<Comparator<T>>();

	/**
	 * Creates a new composed comparator for a given collection of comparators.
	 * Please note that iteration order of the given collection is significant:
	 * comparators will be used in that order.
	 *
	 * @param comparators a collection of comparators.
	 *
	 * @throws IllegalArgumentException if {@code comparators} or any of its elements is {@code null}.
	 */
	public ComposedComparator(final Collection<? extends Comparator<T>> comparators) {
		if (comparators == null) {
			throw new IllegalArgumentException();
		}
		for (final Comparator<T> comparator : comparators) {
			if (comparator == null) {
				throw new IllegalArgumentException();
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
	 * @throws IllegalArgumentException if any of arguments is {@code null}.
	 */
	public int compare(final T x, final T y) {
		if (x == null) {
			throw new IllegalArgumentException();
		}
		if (y == null) {
			throw new IllegalArgumentException();
		}
		for (final Comparator<T> comparator : comparators) {
			final int result = comparator.compare(x, y);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

}
