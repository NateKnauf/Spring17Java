import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * HeapSSTests
 *
 * @author Shashank Singh
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeapSSTests {

    private MaxHeap<Integer> maxHeap;
    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        maxHeap = new MaxHeap<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01ConstructorSizeIsEmpty() {
        assertEquals(HeapInterface.INITIAL_CAPACITY,
                maxHeap.getBackingArray().length);
        verifyContents(0, HeapInterface.INITIAL_CAPACITY);
        assertEquals(true, maxHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void t02AddNullException() {
        maxHeap.add(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void t03RemoveEmptyException() {
        maxHeap.remove();
    }

    @Test(timeout = TIMEOUT)
    public void t04Add() {
        maxHeap.add(11);
        verifyContents(1, 15, 11);
        assertEquals(false, maxHeap.isEmpty());
        maxHeap.add(10);
        verifyContents(2, 15, 11, 10);
        maxHeap.add(12);
        maxHeap.add(13);
        maxHeap.add(9);
        verifyContents(5, 15, 13, 12, 11, 10, 9);
        for (int i = 20; i < 40; ++i) {
            maxHeap.add(i);
        }
        verifyContents(25, 30, 39, 36, 38, 31, 35, 37, 27, 24, 30, 32, 34, 28,
                20, 13, 26, 10, 21, 12, 29, 9, 23, 22, 33, 11, 25);
        for (int i = 40; i < 100; ++i) {
            maxHeap.add(i);
        }
        assertEquals(85, maxHeap.size());
        assertEquals(120, maxHeap.getBackingArray().length);
        assertEquals(false, maxHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t05Remove() {
        int[] vals = {4, 7, -4, -9, -6, 8, 2, -1, -10, 9, -7, -5, -8, 6, -3,
                      10, -2, 0, 5, 3, 1};
        for (int elem : vals) {
            maxHeap.add(elem);
        }
        Integer[] expected = new Integer[21];
        for (int i = 10; i > -11; --i) {
            expected[10 - i] = i;
        }
        Integer[] actual = new Integer[21];
        for (int i = 0; i < 21; ++i) {
            actual[i] = maxHeap.remove();
            assertEquals(20 - i, maxHeap.size());
            assertEquals(30, maxHeap.getBackingArray().length);
        }
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void t06Clear() {
        assertEquals(0, maxHeap.size());
        assertEquals(true, maxHeap.isEmpty());
        int[] vals = {4, 7, -4, -9, -6, 8, 2, -1, -10, 9, -7, -5, -8, 6, -3,
                      10, -2, 0, 5, 3, 1};
        for (int elem : vals) {
            maxHeap.add(elem);
        }
        assertEquals(21, maxHeap.size());
        assertEquals(false, maxHeap.isEmpty());
        maxHeap.clear();
        verifyContents(0, 15);
        assertEquals(true, maxHeap.isEmpty());
    }

    /**
     * Helper method for testing proper heap operation
     *
     * @param heapSize expected value for size instance variable
     * @param arrayCapacity expected size of backing array
     * @param arr expected values for backing array
     */
    public void verifyContents(int heapSize, int arrayCapacity,
                               Integer... arr) {
        Integer[] array = new Integer[arrayCapacity];
        System.arraycopy(arr, 0, array, 1, arr.length);
        assertArrayEquals(array, maxHeap.getBackingArray());
        assertEquals(heapSize, maxHeap.size());
        assertEquals(null, maxHeap.getBackingArray()[0]);
    }
}
