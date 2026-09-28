package by.it.group551001.kuzminich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyTreeSet() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) {
        return ((Comparable<Object>) a).compareTo(b);
    }

    private int binarySearch(Object key) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = compare(elements[mid], key);
            if (cmp < 0)      lo = mid + 1;
            else if (cmp > 0) hi = mid - 1;
            else              return mid;
        }
        return -(lo + 1);
    }

    private void grow() {
        Object[] bigger = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = elements[i];
        }
        elements = bigger;
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        int idx = binarySearch(e);

        if (idx >= 0) return false;      // уже есть

        int insertAt = -(idx + 1);       // куда

        if (size == elements.length) grow();

        //  хвост вправо на 1
        for (int i = size; i > insertAt; i--) {
            elements[i] = elements[i - 1];
        }
        elements[insertAt] = e;
        size++;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return binarySearch(o) >= 0;
    }

    @Override
    public boolean remove(Object o) {
        int idx = binarySearch(o);
        if (idx < 0) return false;

        for (int i = idx; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            if (i > 0) result += ", ";
            result += elements[i];
        }
        return result + "]";
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] arr = c.toArray();
        for (int i = 0; i < arr.length; i++) {
            if (!contains(arr[i])) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        Object[] arr = c.toArray();
        for (int i = 0; i < arr.length; i++) {
            @SuppressWarnings("unchecked")
            E e = (E) arr[i];
            if (add(e)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        Object[] arr = c.toArray();
        for (int i = 0; i < arr.length; i++) {
            if (remove(arr[i])) changed = true;
        }
        return changed;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        int i = 0;
        while (i < size) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                changed = true;
                // i не увеличиваем — на его место пришёл следующий элемент
            } else {
                i++;
            }
        }
        return changed;
    }

    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }

}