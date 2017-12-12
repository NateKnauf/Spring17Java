import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of HashMap.
 * 
 * @author Nate Knauf
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        table = (MapEntry<K, V>[])
                new MapEntry[HashMapInterface.INITIAL_CAPACITY];
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[])
                new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot "
                    + "put a key that is null!");
        } else if (value == null) {
            throw new IllegalArgumentException("Cannot "
                    + "put a value that is null!");
        }
        double ratio = (double) (size + 1) / table.length;
        if (ratio > HashMapInterface.MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int delSpot = -1;
        int nulSpot = -1;
        boolean probing = true;
        int ind = Math.abs(key.hashCode());
        for (int i = 0; i < table.length && probing; i++) {
            if (table[(ind + (i * i)) % table.length] == null) {
                if (nulSpot == -1) {
                    nulSpot = (ind + (i * i)) % table.length;
                }
                probing = false;
            } else if (table[(ind + (i * i)) % table.length].getKey()
                    .equals(key) && !table[(ind + (i * i)) % table.length]
                    .isRemoved()) {
                V old = table[(ind + (i * i)) % table.length].getValue();
                table[(ind + (i * i)) % table.length]
                        = new MapEntry<K, V>(key, value);
                return old;
            } else if (table[(ind + (i * i)) % table.length].isRemoved()) {
                if (delSpot == -1) {
                    delSpot = (ind + (i * i)) % table.length;
                }
            }
        }
        if (delSpot != -1) {
            table[delSpot] = new MapEntry<K, V>(key, value);
            size++;
            return null;
        } else if (nulSpot != -1) {
            table[nulSpot] = new MapEntry<K, V>(key, value);
            size++;
            return null;
        }
        resizeBackingTable(table.length * 2 + 1);
        return put(key, value);

    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot "
                    + "remove from a key that is null!");
        }
        int ind = Math.abs(key.hashCode());
        boolean probing = true;
        for (int i = 0; i < table.length; i++) {
            if (table[(ind + (i * i)) % table.length] == null) {
                throw new NoSuchElementException("Key: '"
                        + key + "' is not found!");
            } else if (table[(ind + (i * i)) % table.length].getKey()
                    .equals(key) && !table[(ind + (i * i)) % table.length]
                    .isRemoved()) {
                size--;
                table[(ind + (i * i)) % table.length].setRemoved(true);
                return table[(ind + (i * i)) % table.length].getValue();
            }
        }
        throw new NoSuchElementException("Key: '" + key + "' is not found!");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot "
                    + "remove from a key that is null!");
        }
        int ind = Math.abs(key.hashCode());
        for (int i = 0; i < table.length; i++) {
            if (table[(ind + (i * i)) % table.length] == null) {
                throw new NoSuchElementException("Key: '"
                        + key + "' is not found!");
            } else if (table[(ind + (i * i)) % table.length].getKey()
                    .equals(key) && !table[(ind + (i * i)) % table.length]
                    .isRemoved()) {
                return table[(ind + (i * i)) % table.length].getValue();
            }
        }
        throw new NoSuchElementException("Key: '" + key + "' is not found!");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key: '"
                    + key + "' is not found!");
        }
        int ind = Math.abs(key.hashCode());
        for (int i = 0; i < table.length; i++) {
            if (table[(ind + (i * i)) % table.length] == null) {
                return false;
            } else if (table[(ind + (i * i)) % table.length].getKey()
                    .equals(key) && !table[(ind + (i * i)) % table.length]
                    .isRemoved()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[])
                new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> mySet = new HashSet<K>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                mySet.add(entry.getKey());
            }
        }
        return mySet;
    }

    @Override
    public List<V> values() {
        ArrayList<V> myList = new ArrayList<V>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                myList.add(entry.getValue());
            }
        }
        return myList;
    }

    @Override
    public void resizeBackingTable(int length) {
        MapEntry<K, V>[] oldTable = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        size = 0;
        for (MapEntry<K, V> entry : oldTable) {
            if (entry != null && !entry.isRemoved()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
