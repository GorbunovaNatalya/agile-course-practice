package ru.unn.agile.LeftSidedHeap.Model;

import java.util.Collection;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

public class LeftSidedHeap<T> {

    public LeftSidedHeap() {
        size = 0;
        root = null;
    }

    public LeftSidedHeap(final Collection<SimpleEntry<Integer, T>> collection) {
        this();
        for (SimpleEntry<Integer, T> keyValuePair : collection) {
            add(keyValuePair);
        }
    }

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

    public final void add(final SimpleEntry<Integer, T> keyValuePair) {
        if (size() == 0) {
            root = new Node<>(keyValuePair, 1, null, null, null);
            size++;
        } else {
            merge(new LeftSidedHeap<>(keyValuePair));
        }
    }

    public SimpleEntry<Integer, T> search(final int key) {
        SimpleEntry<Integer, T> result = null;
        Node<T> node = search(root, key);
        if (node != null) {
            result = new SimpleEntry<>(node.getKey(), node.getValue());
        }
        return result;
    }

    public SimpleEntry<Integer, T> getMin() throws IllegalStateException {
        if (size() == 0) {
            throw new IllegalStateException("Attempt to get minimum from empty heap!");
        }
        return new SimpleEntry<>(root.getKey(), root.getValue());
    }

    public Collection<SimpleEntry<Integer, T>> removeAll(final int key)
            throws IllegalStateException {
        Collection<SimpleEntry<Integer, T>> retVal = null;
        if (size() == 0) {
            throw new IllegalStateException("Attempt to remove elements from empty heap!");
        } else {
            retVal = new LinkedList<>();
            SimpleEntry<Integer, T> removeVal = remove(key);
            while (removeVal != null) {
                retVal.add(removeVal);
                if (size() != 0) {
                    removeVal = remove(key);
                } else {
                    removeVal = null;
                }
            }
        }
        return retVal;
    }

    public boolean contains(final Collection<SimpleEntry<Integer, T>> collection) {
        boolean result = true;
        for (SimpleEntry<Integer, T> keyValuePair : collection) {
            SimpleEntry<Integer, T> foundElement = search(keyValuePair.getKey());
            result = false;
            if (foundElement != null) {
                result = search(keyValuePair.getKey()).equals(keyValuePair);
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public boolean merge(final LeftSidedHeap<T> heap) {
        boolean bResult = true;
        if (heap == null) {
            bResult = false;
        } else if (size() == 0) {
            root = heap.root;
            size = heap.size;
        } else if (heap.root != null) {
            root = merge(root, heap.root);
            size += heap.size;
        }
        return bResult;
    }

    public SimpleEntry<Integer, T> remove(final int key) throws IllegalStateException {
        SimpleEntry<Integer, T> retVal = null;
        if (size() == 0) {
            throw new IllegalStateException("Attempt to remove element from empty heap!");
        } else {
            Node<T> nodeToRemove = search(root, key);

            if (nodeToRemove == null) {
                retVal = null;
            } else if (nodeToRemove == root) {
                retVal = new SimpleEntry<>(root.getKey(), root.getValue());
                root = merge(root.left, root.right);
                size--;
            } else {
                retVal = new SimpleEntry<>(nodeToRemove.getKey(), nodeToRemove.getValue());

                Node<T> parentRemovingNode = nodeToRemove.parent;
                Node<T> newHeap = merge(nodeToRemove.left, nodeToRemove.right);
                if (parentRemovingNode.left == nodeToRemove) {
                    parentRemovingNode.left = newHeap;
                } else {
                    parentRemovingNode.right = newHeap;
                }
                Node<T> tmp = parentRemovingNode;
                while (tmp != root && dist(tmp.left) < dist(tmp.right)) {
                    tmp.swapChildren();
                }
                size--;
            }
        }
        return retVal;
    }

    private static final class Node<T> extends SimpleEntry<Integer, T> {
        Node(final SimpleEntry<Integer, T> keyValuePair, final int rank,
                     final Node<T> left, final Node<T> right, final Node<T> parent) {
            super(keyValuePair);
            this.rank = rank;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        private void swapChildren() {
            Node<T> tmp = left;
            left = right;
            right = tmp;
        }

        private int rank;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;
    }

    private LeftSidedHeap(final SimpleEntry<Integer, T> keyValuePair) {
        this();
        add(keyValuePair);
    }

    private int dist(final Node<T> node) {
        int ret;
        if (node == null) {
            ret = 0;
        } else {
            ret = node.rank;
        }
        return ret;
    }

    private Node<T> search(final Node<T> head, final int key) {
        Node<T> currentNode = null;
        if (head != null) {
            if (head.getKey() == key) {
                currentNode = head;
            } else if (key >= head.getKey()) {
                currentNode = search(head.left, key);
                if (currentNode == null) {
                    currentNode = search(head.right, key);
                }
            }
        }
        return currentNode;
    }

    private Node<T> merge(final Node<T> firstHeap, final Node<T> secondHeap) {
        Node<T> x = firstHeap;
        Node<T> y = secondHeap;
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }
        if (x.getKey() > y.getKey()) {
            Node<T> tmp = x;
            x = y;
            y = tmp;
        }
        x.right = merge(x.right, y);
        if (dist(x.right) > dist(x.left)) {
            x.swapChildren();
        }

        Node<T> leftNode = x.left;
        if (leftNode != null) {
            leftNode.parent = x;
        }
        Node<T> rightNode = x.right;
        if (rightNode != null) {
            rightNode.parent = x;
        }
        x.rank = Math.min(dist(x.right), dist(x.left)) + 1;
        return x;
    }

    private int size;
    private Node<T> root;
}
