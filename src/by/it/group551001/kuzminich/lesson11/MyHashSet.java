package by.it.group551001.kuzminich.lesson11;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private static class Node<E> {
        E item;
        Node<E> next;
        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node<E>[] buckets;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyHashSet(){
        buckets = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;

    }

    private int indexFor(Object key, int capacity) {
        if (key == null) return 0;
        int h = key.hashCode() % capacity;
        if (h < 0) h += capacity;
        return h;
    }
    private boolean eq(Object a, Object b) {
        if (a == null) return b == null;
        return a.equals(b);
    }
    @Override
    public boolean add(E e) {
        int idx = indexFor(e, buckets.length);

        // нет ли уже такого в цепочке этого бакета
        for (Node<E> cur = buckets[idx]; cur != null; cur = cur.next) {
            if (eq(cur.item, e)) {
                return false;   // уже есть — ничего не меняем
            }
        }

        // вставляем в голову цепочки
        buckets[idx] = new Node<>(e, buckets[idx]);
        size++;

        // расширяемся
        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        int idx = indexFor(o, buckets.length);
        for (Node<E> cur = buckets[idx]; cur != null; cur = cur.next) {
            if (eq(cur.item, o)) return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        int idx = indexFor(o, buckets.length);
        Node<E> prev = null;

        for (Node<E> cur = buckets[idx]; cur != null; cur = cur.next) {
            if (eq(cur.item, o)) {
                // выкидываем узел из цепочки
                if (prev == null) {
                    buckets[idx] = cur.next;    // удаляем первый
                } else {
                    prev.next = cur.next;       // удаляем средний/последний
                }
                size--;
                return true;
            }
            prev = cur;
        }
        return false;
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
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] old = buckets;
        buckets = (Node<E>[]) new Node[old.length * 2];
        size = 0;

        // перекладываем
        for (Node<E> head : old) {
            Node<E> cur = head;
            while (cur != null) {
                add(cur.item);
                cur = cur.next;
            }
        }
    }
    @Override
    public String toString() {
        String result = "[";
        boolean first = true;

        for (int i = 0; i < buckets.length; i++) {
            for (Node<E> cur = buckets[i]; cur != null; cur = cur.next) {
                if (!first) result += ", ";
                result += cur.item;
                first = false;
            }
        }
        return result + "]";
    }
    @Override
    public java.util.Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(java.util.Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }


}
