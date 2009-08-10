package gems.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Unit tests for {@code PersistentCollection} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitPersistentCollection {

    /**
     * Tested fixture.
     */
    private Collection<Integer> fixture;

    /**
     * Creates a new tested fixture.
     */
    @Before public void setUp() {
        fixture = new PersistentCollection<Integer>();
        fixture.add(0);
        fixture.add(1);
        fixture.add(2);
        fixture.add(3);
    }

    /**
     * Checks if a newly created instance is empty.
     */
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    @Test public void newInstanceIsEmpty() {
        final Collection<Integer> fixture = new PersistentCollection<Integer>();
        checkEmptiness(fixture);
    }

    /**
     * Checks whether adding elements increases size.
     */
    @Test public void addingElementIncreasesSize() {
        Assert.assertEquals(4, fixture.size());
        fixture.add(42); // this is not contained yet
        Assert.assertEquals(5, fixture.size());
        fixture.add(0); // this is already contained
        Assert.assertEquals(6, fixture.size());
    }

    /**
     * Checks whether it is not possible to add {@code null} value.
     */
    @Test(expected = IllegalArgumentException.class) public void itIsImpossibleToAddNullDirectly() {
        fixture.add(null);
    }

    /**
     * Checks whether removing elements decreases size.
     */
    @Test public void removingElementDecreasesSize() {
        Assert.assertEquals(4, fixture.size());
        fixture.remove(0); // removing existing object
        Assert.assertEquals(3, fixture.size());
        fixture.remove(42); // trying to remove non-existent object
        Assert.assertEquals(3, fixture.size());
    }

    /**
     * Checks whether {@code clear()} action works.
     */
    @Test public void checkClear() {
        fixture.clear();
        checkEmptiness(fixture);
    }

    /**
     * Checks whether {@code contains()} method works for values.
     */
    @Test public void checkContains() {
        Assert.assertFalse(fixture.contains(-1));
        Assert.assertTrue(fixture.contains(0));
        Assert.assertTrue(fixture.contains(1));
        Assert.assertTrue(fixture.contains(2));
        Assert.assertTrue(fixture.contains(3));
        Assert.assertFalse(fixture.contains(4));
    }

    /**
     * The collection implementation does not allow storing {@code null} keys, so
     * the {@code contains()} method has to return {@code false} for {@code null} key. 
     */
    @Test public void containsReturnsFalseForNullKey() {
        Assert.assertFalse(fixture.contains(null));
    }

    /**
     * Checking value presence fails if object has not proper type.  
     */
    @SuppressWarnings({"SuspiciousMethodCalls"})
    @Test public void containsReturnsFalseForInvalidKeyType() {
        Assert.assertFalse(fixture.contains((short) 0));
        Assert.assertFalse(fixture.contains(0L));
    }

    @Test(expected = IllegalArgumentException.class) public void containsAllRefusesNull() {
        fixture.containsAll(null);
    }
    
    @Test public void containsAllReturnsFalseForCollectionWithNullElement() {
        final Collection<?> searched = new LinkedList();
        searched.add(null);
        Assert.assertFalse(fixture.containsAll(searched));
    }

    @Test public void checkContainsAll() {
        final Collection<Integer> searched = new LinkedList<Integer>();
        Assert.assertTrue(fixture.containsAll(searched)); // empty
        searched.add(0);
        Assert.assertTrue(fixture.containsAll(searched)); // 0
        searched.add(1);
        Assert.assertTrue(fixture.containsAll(searched)); // 0, 1
        searched.add(2);
        Assert.assertTrue(fixture.containsAll(searched)); // 0, 1, 2
        searched.add(3);
        Assert.assertTrue(fixture.containsAll(searched)); // 0, 1, 2, 3
        searched.add(4);
        Assert.assertFalse(fixture.containsAll(searched)); // 0, 1, 2, 3, 4 
    }

    /**
     * Checks given fixture for emptiness.
     *
     * @param fixture a checked fixture.
     */
    private static void checkEmptiness(final Collection<?> fixture) {
        Assert.assertTrue(fixture.isEmpty());
        Assert.assertTrue(fixture.size() == 0);
        // TODO: WRITE TESTS FOR ITERATOR FIRST.
//        Assert.assertFalse(fixture.iterator().hasNext());
    }


}
