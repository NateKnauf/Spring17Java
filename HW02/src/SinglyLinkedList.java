import java.util.NoSuchElementException;

/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!"
                    + " Index: " + index);
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index cannot be larger "
                    + "than the size! Index: " + index + " Size: " + size);
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot add 'null' data!");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            prev.setNext(new LinkedListNode<T>(data, prev.getNext()));
            if (prev.getNext().getNext() == null) {
                tail = prev.getNext();
            }
            size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add 'null' data!");
        }
        head = new LinkedListNode<T>(data, head);
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add 'null' data!");
        } else if (head == null) {
            addToFront(data);
        } else {
            tail.setNext(new LinkedListNode<T>(data));
            tail = tail.getNext();
            size++;
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!"
                    + " Index: " + index);
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be larger "
                    + "than or equal to the size! Index: "
                    + index + " Size: " + size);
        } else if (index == 0) {
            return removeFromFront();
        } else {
            LinkedListNode<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            LinkedListNode<T> cur = prev.getNext();
            if (tail == cur) {
                tail = prev;
            }
            prev.setNext(cur.getNext());
            size--;
            return cur.getData();
        }
    }

    @Override
    public T removeFromFront() {
        T target = null;
        if (head == null) {
            target = null;
        } else {
            if (head == tail) {
                tail = null;
            }
            target = head.getData();
            head = head.getNext();
            size--;
        }
        return target;
    }

    @Override
    public T removeFromBack() {
        T target;
        if (tail == null) {
            target = null;
        } else if (head == tail) {
            target = head.getData();
            head = null;
            tail = null;
            size--;
        } else {
            LinkedListNode<T> cur = head;
            while (cur.getNext() != tail) {
                cur = cur.getNext();
            }
            target = cur.getNext().getData();
            cur.setNext(null);
            tail = cur;
            size--;
        }
        return target;
    }

    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for "
            + "'null' data!");
        }
        if (size == 0) {
            return null;
        } else {
            T target = null;
            for (int i = 0; i < size; i++) {
                if (get(i) == data) {
                    return removeAtIndex(i);
                }
            }
        }
        throw new NoSuchElementException("That data cannot be found in the "
                + "list!");
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative!"
                    + " Index: " + index);
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be larger "
                    + "than or equal to the size! Index: "
                    + index + " Size: " + size);
        } else if (index == 0) {
            return head.getData();
        } else if (index == size) {
            return tail.getData();
        } else {
            LinkedListNode<T> cur = head;
            int ding = 0;
            while (ding < index) {
                cur = cur.getNext();
                ding++;
            }
            return cur.getData();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        LinkedListNode<T> cur = head;
        int ding = 0;
        while (cur != null) {
            array[ding] = cur.getData();
            cur = cur.getNext();
            ding++;
        }
        return array;
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
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
