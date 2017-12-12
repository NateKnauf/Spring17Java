import java.util.*;

/**
 * Your implementation of a binary search tree.
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        for (T element : data) {
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data!");
        }
        if (root == null) {
            size++;
            root = new BSTNode<T>(data);
        } else {
            add(root, data);
        }
    }

    private void add(BSTNode<T> node, T data) {
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                size++;
                node.setLeft(new BSTNode<T>(data));
            } else {
                add(node.getLeft(), data);
            }
        } else if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                size++;
                node.setRight(new BSTNode<T>(data));
            } else {
                add(node.getRight(), data);
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data!");
        } else if (size <= 0) {
            throw new NoSuchElementException("Cannot remove from an empty tree!");
        }
        BSTNode<T> faith = new BSTNode<T>(null);
        root = remove(root, data, faith);
        if (faith.getData() == null) {
            throw new NoSuchElementException("That data: " + data.toString()
                    + " can't be found in the tree!");
        } else {
            size--;
            return faith.getData();
        }
    }

    private BSTNode<T> remove(BSTNode<T> node, T data, BSTNode<T> runner) {
        if (node == null) {
            throw new NoSuchElementException("That data: " + data.toString()
                    + " can't be found in the tree!");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data, runner));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), data, runner));
        } else {
            runner.setData(node.getData());
            if (node.getLeft() != null && node.getRight() != null) {
                BSTNode<T> succ = getSucc(node, node.getRight());
                succ.setRight(node.getRight());
                succ.setLeft(node.getLeft());
                return succ;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            return null;
        }
        return node;
    }

    private BSTNode<T> getSucc(BSTNode<T> parent, BSTNode<T> node) {
        if (node.getLeft() == null) {
            parent.setRight(node.getRight());
        } else {
            while (node.getLeft() != null) {
                parent = node;
                node = node.getLeft();
            }
            if (node.getRight() == null) {
                parent.setLeft(null);
            } else {
                parent.setLeft(node.getRight());
            }
        }
        return node;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't search for null data!");
        } else if (root == null) {
            throw new NoSuchElementException("Can't search in an empty tree!");
        }
        return get(root, data);
    }

    private T get(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Couldn't find the element!");
        } else if (data.compareTo(node.getData()) < 0) {
            return get(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return get(node.getRight(), data);
        } else {
            return node.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot contain null data!");
        }
        return contains(root, data);
    }

    private boolean contains(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(node.getRight(), data);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        list.addAll(preorder(root));
        return list;
    }

    private List<T> preorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<T>();
        if (node == null) {
            return list;
        }
        list.add(node.getData());
        list.addAll(preorder(node.getLeft()));
        list.addAll(preorder(node.getRight()));
        return list;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        list.addAll(postorder(root));
        return list;
    }

    private List<T> postorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<T>();
        if (node == null) {
            return list;
        }
        list.addAll(postorder(node.getLeft()));
        list.addAll(postorder(node.getRight()));
        list.add(node.getData());
        return list;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        list.addAll(inorder(root));
        return list;
    }

    private List<T> inorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<T>();
        if (node == null) {
            return list;
        }
        list.addAll(inorder(node.getLeft()));
        list.add(node.getData());
        list.addAll(inorder(node.getRight()));
        return list;
    }


    @Override
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<T>();
        Queue<BSTNode<T>> row = new LinkedList<BSTNode<T>>();
        if (root == null) {
            return list;
        } else {
            row.add(root);
            while (!row.isEmpty()) {
                BSTNode<T> node = row.remove();
                list.add(node.getData());
                if (node.getLeft() != null) {
                    row.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    row.add(node.getRight());
                }
            }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return height(root);
        }
    }

    private int height(BSTNode<T> node) {
        if (node == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else {
            return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
