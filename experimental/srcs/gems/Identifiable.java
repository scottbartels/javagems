package gems;

/**
 * Defines an object identified by an another object of certain type.
 * It is advisable to use immutable objects with properly defined
 * {@code equals()} and {@code hashCode()} methods as IDs. Implementations
 * should ensure that equality of two objects is given by equality of their
 * IDs. In another words, two {@code Identifiable} objects should be equal
 * if and only if their IDs are equal. Implementators should also ensure
 * that object's ID is never {@code null} and it cannot change during
 * entire object existence. In another words, the contract for ID is pretty
 * similar to contract required for key used in {@code java.util.Map}. In
 * fact, IDs of {@code Identifiable} objects are used as hash map keys on
 * many places. 
 *
 * @param <T> a type of the object ID.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface Identifiable<T> {

	/**
	 * Returns an ID of the object.
	 *
	 * @return an ID of the object. 
	 */
	T getId();

}
