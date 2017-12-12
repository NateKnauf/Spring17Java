import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
       backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
       size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add a null item "
                    + "to the MaxHeap!");
        } else {
            if (size == backingArray.length - 1) {
                T[] newArr = (T[]) new Comparable[backingArray.length * 2 + 1];
                for (int i = 0; i < backingArray.length; i++) {
                    newArr[i] = backingArray[i];
                }
                backingArray = newArr;
            }
            size++;
            backingArray[size] = item;
            heapUp(size);
        }
    }

    private void heapUp(int n) {
        int parent = n/2;
        if (n >= 2 && backingArray[parent].compareTo(backingArray[n]) < 0) {
            T tmp = backingArray[parent];
            backingArray[parent] = backingArray[n];
            backingArray[n] = tmp;
            heapUp(parent);
        }
    }

    private void heapDown(int n) {
        int bigN = n;
        if (2 * n <= size
                && backingArray[2 * n].compareTo(backingArray[bigN]) > 0) {
            bigN = 2 * n;
        }
        if (2 * n + 1 <= size
                && backingArray[2 * n + 1].compareTo(backingArray[bigN]) > 0) {
            bigN = 2 * n + 1;
        }
        if (bigN != n) {
            T bigData = backingArray[n];
            backingArray[n] = backingArray[bigN];
            backingArray[bigN] = bigData;
            heapDown(bigN);
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Can't remove because "
                    + "the MaxHeap is empty");
        }
        T tmp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapDown(1);
        return tmp;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}
