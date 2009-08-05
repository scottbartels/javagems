package gems.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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
     * Checks given fixture for emptyness.
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
