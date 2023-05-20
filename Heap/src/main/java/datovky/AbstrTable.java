/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datovky;

import java.util.Iterator;
import java.util.NoSuchElementException;
import misc.eTypProhl;

/**
 *
 * @author wille
 * @param <K>
 * @param <V>
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    public class Node {

        public K key;
        public V value;

        public Node parent = null;
        public Node left = null;
        public Node right = null;

        public Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;

    @Override
    public void zrus() {
        root = null;
    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    @Override
    public V najdi(K key) {
        Node node = findNode(key);

        return node.value;
    }

    @Override
    public void vloz(K key, V value) {
        if (key == null || value == null) {
            throw new NoSuchElementException("klic nebo hodnota neexistujou!");
        }

        if (jePrazdny()) {
            root = new Node(key, value, null);
            return;
        }

        Node currNode = root;
        while (currNode != null) {
            if (currNode.key.compareTo(key) == 0) {
                System.out.println("pozadovany klic jiz existuje!");
                return;

            } else if (currNode.key.compareTo(key) < 0) {
                if (currNode.left == null) {
                    currNode.left = new Node(key, value, currNode);
                    return;

                } else {
                    currNode = currNode.left;
                }
            } else if (currNode.key.compareTo(key) > 0) {
                if (currNode.right == null) {
                    currNode.right = new Node(key, value, currNode);
                    return;

                } else {
                    currNode = currNode.right;
                }
            }
        }

    }

        @Override
    public V odeber(K key) {
        Node node = findNode(key);

        if (node == null) {
            return null;
        }

        V value = node.value;

        Node newNode = removeNode(node);

        return value;
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case BREADTH_FIRST -> {
                return iteratorBreadthFirst();
            }
            case IN_ORDER -> {
                return iteratorInOrder();
            }
        }

        return null;
    }

    private Iterator<V> iteratorInOrder() {
        IAbstrLifo<Node> stack = new AbstrLifo<>();
        stack.vloz(this.root);

        return new Iterator<V>() {
            Node currNode = root;

            @Override
            public boolean hasNext() {
                return currNode != null && !stack.jePrazdny();
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                while (currNode != null && currNode.left != null) {
                    stack.vloz(currNode.left);
                    currNode = currNode.left;
                }

                Node node = stack.odeber();

                if (node.right != null) {
                    stack.vloz(node.right);
                    currNode = node.right;
                }

                return node.value;
            }
        };
    }

    private Iterator<V> iteratorBreadthFirst() {
        IAbstrFifo<Node> queue = new AbstrFifo<>();
        queue.vloz(this.root);

        return new Iterator<V>() {
            Node currNode = root;

            @Override
            public boolean hasNext() {
                return currNode != null && !queue.jePrazdny();
            }

            @Override
            public V next() {
                currNode = queue.odeber();
                
                if (currNode.left != null) {
                    queue.vloz(currNode.left);
                }
                if (currNode.right != null) {
                    queue.vloz(currNode.right);
                }

                return currNode.value;
            }
        };
    }

    private Node findNode(K key) throws NoSuchElementException {
        Node currNode = root;

        while (currNode != null) {
            if (key.compareTo(currNode.key) == 0) {
                return currNode;
            }

            if (key.compareTo(currNode.key) > 0) {
                currNode = currNode.left;

            } else if (key.compareTo(currNode.key) < 0) {
                currNode = currNode.right;

            }
        }

        throw new NoSuchElementException();
    }
    
     private Node removeNode(Node node) {
        if (root == node && node.left == null) {
            root = this.root.right;
        }
        else if (root == node && node.right == null) {
            root = root.left;
        }
        else if (node.left == null) {
            if (node.parent.left == node) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
        } else if (node.right == null) {
            if (node.parent.left == node) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
        } else {
            Node min = findMin(node.right);

            node.key = min.key;
            node.value = min.value;

            removeNode(min);
        }

        return node;
    }

    private Node findMin(Node node) {
        Node currNode = node;

        while (currNode.left != null) {
            currNode = currNode.left;
        }

        return currNode;
    }

    public Node getRoot() {
        return root;
    }
    
    
}
