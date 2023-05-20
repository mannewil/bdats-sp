/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datovky;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import misc.eTypProhl;

public class AbstrHeap<T extends Comparable<T>> implements IAbstrHeap<T> {

    private Comparable[] heap;
    private int size;

    public AbstrHeap() {
        this.heap = new Comparable[1];
        this.size = 0;

    }

    @Override
    public void vybuduj(List<T> array) {
        if (array.isEmpty()) {
            return;
        }

        heap = new Comparable[array.size()];
        size = 0;

        for (T element : array) {
            vloz(element);
        }
    }

    @Override
    public void prebuduj() {
        funcPrebuduj(0);
    }

    private void funcPrebuduj(int number) {
        int left = left(number);
        int right = right(number);
        int smallest = number;

        if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }
        if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != number) {
            swap(number, smallest);
            funcPrebuduj(smallest);
        }
    }

    @Override
    public void zrus() {
        heap = new Comparable[1];
        size = 0;
    }

    @Override
    public boolean jePrazdny() {
        return size == 0;
    }

    @Override
    public void vloz(T element) {
        if (size >= heap.length) {
            Comparable[] newHeap = new Comparable[size * 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }

        int i = size;
        heap[i] = element;

        while (i != 0 && heap[parent(i)].compareTo(heap[i]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }

        this.size++;

    }

    @Override
    public T odeberMax() {
        if (jePrazdny()) {
            return null;
        }

        T value = (T) heap[0];

        if (size == 1) {
            size--;
            heap[size] = null;
            return value;
        }

        size--;
        heap[0] = heap[size];
        heap[size] = null;

        funcPrebuduj(0);

        return value;
    }

    @Override
    public T zpristupniMax() {
        return size == 0 ? null : (T) heap[0];
    }

    @Override
    public Iterator<T> vytvorIterator(eTypProhl typ) {
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

    private Iterator<T> iteratorInOrder() {
        IAbstrLifo<Integer> stack = new AbstrLifo<>();
        stack.vloz(0);
        return new Iterator<T>() {
            int curr = 0;

            @Override
            public boolean hasNext() {
                return !stack.jePrazdny();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                while (left(curr) < size) {
                    stack.vloz(left(curr));
                    curr = left(curr);
                }

                int v = stack.odeber();

                if (right(v) < size) {
                    stack.vloz(right(v));
                    curr = right(v);
                }

                return (T) heap[v];
            }
        };
    }

    private Iterator<T> iteratorBreadthFirst() {
        return new Iterator<T>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return (T) heap[i++];
            }
        };
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private void swap(int a, int b) {
        T temp = (T) heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }
}
