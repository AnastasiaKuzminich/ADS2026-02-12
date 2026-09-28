package by.it.group551001.kuzminich.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    //Левый ребёнок i	2*i + 1
    //Правый ребёнок i	2*i + 2
    //Родитель i	(i - 1) / 2
    private E[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        heap = (E[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }


    @SuppressWarnings("unchecked")
    private void grow() {
        if (size < heap.length) return;
        E[] newHeap = (E[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i)   { return 2 * i + 1; }
    private int right(int i)  { return 2 * i + 2; }

    private void swap(int i, int j) {
        E tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    // Просеивание вверх (после добавления в конец)
    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            if (heap[i].compareTo(heap[p]) >= 0) break;
            swap(i, p);
            i = p;
        }
    }

    // Просеивание вниз (после удаления корня)
    private void siftDown(int i) {
        while (true) {
            int l = left(i);
            int r = right(i);
            int smallest = i;
            if (l < size && heap[l].compareTo(heap[smallest]) < 0) smallest = l;
            if (r < size && heap[r].compareTo(heap[smallest]) < 0) smallest = r;
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    // Построение кучи за O(n) — для removeAll / retainAll
    private void heapify() {
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) heap[i] = null;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public E remove() {
        E value = poll();
        if (value == null) throw new NoSuchElementException();
        return value;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public boolean offer(E element) {
        grow();
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) return null;
        E root = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) siftDown(0);
        return root;
    }

    @Override
    public E peek() {
        return size == 0 ? null : heap[0];
    }

    @Override
    public E element() {
        if (size == 0) throw new NoSuchElementException();
        return heap[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            offer(e);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(heap[i])) {
                heap[newSize++] = heap[i];
            } else {
                changed = true;
            }
        }
        for (int i = newSize; i < size; i++) heap[i] = null;
        size = newSize;
        heapify();
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(heap[i])) {
                heap[newSize++] = heap[i];
            } else {
                changed = true;
            }
        }
        for (int i = newSize; i < size; i++) heap[i] = null;
        size = newSize;
        heapify();
        return changed;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                if (size > 0) {
                    siftDown(i);
                    siftUp(i);
                }
                return true;
            }
        }
        return false;
    }

    // ---------- заглушки ----------

    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
}