/**
 * Your implementation of an ArrayList.
 *
 * @author YOUR NAME HERE
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[ArrayListInterface.INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException();
        }
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (index == size && size + 1 < backingArray.length) {
            backingArray[index] = data;
            size++;
        } else {
            T[] newArray;
            if (size + 1 >= backingArray.length) {
                newArray = (T[]) new Object[backingArray.length * 3 / 2];
            } else {
                newArray = (T[]) new Object[backingArray.length];
            }
            for (int i = 0; i < backingArray.length; i++) {
                if (i < index) {
                    newArray[i] = backingArray[i];
                } else if (i == index) {
                    newArray[i] = data;
                } else {
                    newArray[i] = backingArray[i - 1];
                }
            }
            size++;
            backingArray = newArray;
        }

    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size(), data);
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size ) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 0) {
            return null;
        }

        T item = backingArray[index];
        if (item != null) {
            size--;
        }

        for (int i = index; i <= size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[backingArray.length - 1] = null;

        return item;
    }

    @Override
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        return removeAtIndex(size() - 1);
    }

    @Override
    public T get(int index) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            return backingArray[index];
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Object[ArrayListInterface.INITIAL_CAPACITY];
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
