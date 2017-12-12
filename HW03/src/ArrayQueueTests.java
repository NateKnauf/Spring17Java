import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayQueueTests {
    private QueueInterface<Integer> queue;

    public static final int TIMEOUT = 200;

    @Test(expected = java.util.NoSuchElementException.class)
    public void testEmptyArrayQueue() {
        queue = new ArrayQueue<>();
        queue.dequeue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInputToArrayQueue() {
        queue = new ArrayQueue<>();
        queue.enqueue(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueue() {
        queue = new ArrayQueue<>();
        assertEquals(0, queue.size());

        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        Object[] expected = new Object[11];
        expected[3] = 4;
        expected[4] = 5;

        assertArrayEquals(expected, backingArray);

        for (int i = 5; i <= 13; i++) {
            queue.enqueue(i);
            expected[i % expected.length] = i;
        }

        assertArrayEquals(expected, backingArray);

        assertEquals(Integer.valueOf(4), queue.dequeue());
        assertEquals(Integer.valueOf(5), queue.dequeue());

        queue.enqueue(14);
        queue.enqueue(15);

        queue.enqueue(100);

        expected = new Object[expected.length * 2 + 1];
        for (int i = 5; i <= 15; i++) {
            expected[i - 5] = i;
        }
        expected[11] = 100;

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertArrayEquals(expected, backingArray);

        for (int i = 12; i < 23; i++) {
            expected[i] = i * 2;
            queue.enqueue(i * 2);
        }

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        assertArrayEquals(((ArrayQueue<Integer>) queue).getBackingArray(), expected);
        queue.enqueue(11111);

        assertEquals(expected.length * 2 + 1, ((ArrayQueue<Integer>) queue).getBackingArray().length);
    }
}