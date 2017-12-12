import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {

    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */

    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot create tree from null data!");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException(
                        "Cannot create tree from null data!");
            }
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data!");
        }
        root = add(root, data);
        //BTreePrinter.printNode(root);
    }

    /**
     *  Helper method for adding new data onto the tree.
     *
     * @param node Current cursor node, data will be added underneath
     * @param data Data to add to tree
     * @return New root of the subtree
     */

    private AVLNode<T> add(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(node.getRight(), data));
        }
        return balance(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data!");
        } else if (size <= 0) {
            throw new NoSuchElementException(
                    "Cannot remove from an empty tree!");
        }
        AVLNode<T> faith = new AVLNode<T>(null);
        root = remove(root, data, faith);
        //BTreePrinter.printNode(root);
        if (faith.getData() == null) {
            throw new NoSuchElementException("That data: " + data.toString()
                    + " can't be found in the tree!");
        } else {
            size--;
            return faith.getData();
        }
    }

    /**
     * @param node Current cursor node, data to be removed is either
     *             in it or underneath
     * @param data Data being searched for which will be removed
     * @param runner Helper node to carry the removed data up through
     *               recursive function calls
     * @return Data removed from Tree
     * @throws NoSuchElementException if data not found
     */

    private AVLNode<T> remove(AVLNode<T> node, T data, AVLNode<T> runner) {
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
                AVLNode<T> succ = getSucc(node);
                succ.setRight(balance(node.getRight()));
                succ.setLeft(balance(node.getLeft()));
                return balance(succ);
            } else if (node.getLeft() == null) {
                return balance(node.getRight());
            } else if (node.getRight() == null) {
                return balance(node.getLeft());
            }
            return null;
        }
        return balance(node);
    }

    /**
     * Used to find the successor node of any given parent node
     *
     * @param node the node whose successor we are finding
     * @return The successor of the node parameter
     */

    private AVLNode<T> getSucc(AVLNode<T> node) {
        AVLNode<T> parent = update(node);
        node = update(parent.getRight());
        if (node.getLeft() != null) {
            AVLNode<T> grandparent = null;
            while (node.getLeft() != null) {
                grandparent = parent;
                parent = node;
                node = node.getLeft();
            }
            update(node);
            parent.setLeft(node.getRight());
            update(parent);
            if (grandparent != null) {
                grandparent.setLeft(balance(grandparent.getLeft()));
                grandparent.setRight(balance(grandparent.getRight()));
            }
            update(grandparent);
        } else {
            update(node);
            parent.setRight(node.getRight());
        }
        update(parent);
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

    /**
     * Recursive helper method to get data from cursor node or its children
     * @param node Current cursor node in Tree
     * @param data Data that is being searched for
     * @return The data from the Tree once its found in the Tree
     */

    private T get(AVLNode<T> node, T data) {
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

    /**
     * Helper method to search for data in a node and its children
     * @param node The current cursor node in the Tree
     * @param data The data we are looking for
     * @return True is we can find the data in the Tree, false if we can't
     */

    private boolean contains(AVLNode<T> node, T data) {
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
        preorder(root, list);
        return list;
    }

    /**
     * Helper method to add a node and it's children's values
     * to the preorder list
     * @param node The current cursor node in the Tree
     * @param list The list of preordered values
     */

    private void preorder(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        preorder(node.getLeft(), list);
        preorder(node.getRight(), list);
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        postorder(root, list);
        return list;
    }

    /**
     * Helper method to add a node and it's children's values
     * to the postorder list
     * @param node The current cursor node in the Tree
     * @param list The list of postordered values
     */
    private void postorder(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        postorder(node.getLeft(), list);
        postorder(node.getRight(), list);
        list.add(node.getData());
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        inorder(root, list);
        return list;
    }

    /**
     * Helper method to add a node's and its children's values
     * to the inorder list
     * @param node The current cursor node in the Tree
     * @param list The list of inordered values
     */
    private void inorder(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        inorder(node.getLeft(), list);
        list.add(node.getData());
        inorder(node.getRight(), list);
    }


    @Override
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<T>();
        Queue<AVLNode<T>> row = new LinkedList<AVLNode<T>>();
        if (root == null) {
            return list;
        } else {
            row.add(root);
            while (!row.isEmpty()) {
                AVLNode<T> node = row.remove();
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
            return root.getHeight();
        }
    }

    /**
     * Method to update the height and balance properties of a node
     * @param node The node we are updating
     * @return The updated node
     */

    private AVLNode<T> update(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        int lh = -1;
        if (node.getLeft() != null) {
            lh = node.getLeft().getHeight();
        }
        int rh = -1;
        if (node.getRight() != null) {
            rh = node.getRight().getHeight();
        }
        node.setHeight(Math.max(lh, rh) + 1);
        node.setBalanceFactor(lh - rh);
        return node;
    }

    /**
     * Balance a node by rotating it around based on its balance factor
     * @param node The node we are balancing
     * @return The newly balanced nodes
     */

    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        node = update(node);
        if (node.getBalanceFactor() >= 2) {
            AVLNode<T> left = update(node.getLeft());
            if (left == null) {
                return node;
            }
            if (left.getBalanceFactor() > 0) {
                node = rightRotate(node);
            } else if (left.getBalanceFactor() < 0) {
                node = leftRightRotate(node);
            }
        } else if (node.getBalanceFactor() <= -2) {
            AVLNode<T> right = update(node.getRight());
            if (right == null) {
                return node;
            }
            if (right.getBalanceFactor() > 0) {
                node = rightLeftRotate(node);
            } else if (right.getBalanceFactor() < 0) {
                node = leftRotate(node);
            }
        }
        return update(node);
    }

    /**
     * Perform a Left Rotation on a group of nodes
     * @param node The top node of the group we are rotating
     * @return The new top of the rotated group
     */

    private AVLNode<T> leftRotate(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        AVLNode<T> bump = node.getRight();
        AVLNode<T> temp = bump.getLeft();
        bump.setLeft(node);
        node.setRight(temp);
        update(node);
        return update(bump);
    }

    /**
     * Perform a Right Rotation on a group of nodes
     * @param node The top node of the group we are rotating
     * @return The new top of the rotated group
     */

    private AVLNode<T> rightRotate(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        AVLNode<T> bump = node.getLeft();
        AVLNode<T> temp = bump.getRight();
        bump.setRight(node);
        node.setLeft(temp);
        update(node);
        return update(bump);
    }

    /**
     * Perform a Left-Right Rotation on a group of nodes
     * @param node The top node of the group we are rotating
     * @return The new top of the rotated group
     */

    private AVLNode<T> leftRightRotate(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        node.setLeft(leftRotate(node.getLeft()));
        update(node.getLeft());
        update(node);
        return update(rightRotate(node));
    }

    /**
     * Perform a Right-Left Rotation on a group of nodes
     * @param node The top node of the group we are rotating
     * @return The new top of the rotated group
     */

    private AVLNode<T> rightLeftRotate(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        node.setRight(rightRotate(node.getRight()));
        update(node.getRight());
        update(node);
        return update(leftRotate(node));
    }


    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
