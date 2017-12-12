import java.util.NoSuchElementException;

/**
 * Your implementation of a linked stack.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public T pop() {
        if (head == null) {
            throw new NoSuchElementException("Can't pop because the"
                    + " stack is empty!");
        }
        T data = head.getData();
        head = head.getNext();
        size--;
        return data;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't push null data "
                    + "to the stack!");
        }
        head = new LinkedNode<T>(data, head);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}