package by.it.group551001.kuzminich.lesson11;


//Задание на уровень B
//
//Создайте class MyLinkedHashSet<E>, который реализует интерфейс Set<E>
//и работает на основе массива с адресацией по хеш-коду
//и односвязным списком для элементов с коллизиями
//БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
//
//Метод toString() должен выводить элементы в порядке их добавления в коллекцию
//Формат вывода: скобки (квадратные) и разделитель (запятая с пробелом) должны
//быть такими же как в методе toString() обычной коллекции
//

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

///////////////////////////////////////////////////////////////////////////
////////               Обязательные к реализации методы             ///////
///////////////////////////////////////////////////////////////////////////
//
//toString()
//size()
//clear()
//isEmpty()
//add(Object)
//remove(Object)
//contains(Object)
//
//containsAll(Collection)
//addAll(Collection)
//removeAll(Collection)
//retainAll(Collection)


public class MyLinkedHashSet<E> implements Set<E> {
    private static class Node<E> {
        E item;
        Node<E> next;     // след в бакете
        Node<E> before;   // пред по добавл
        Node<E> after;    // след по доб

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
    private Node<E>[] buckets;
    private int size;
    private Node<E> head;
    private Node<E> tail;
    private static final int DEFAULT_CAPACITY = 10;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
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
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(E e) {
        int idx = indexFor(e, buckets.length);

        // check same
        for (Node<E> cur = buckets[idx]; cur != null; cur = cur.next) {
            if (eq(cur.item, e)) return false;
        }

        Node<E> node = new Node<>(e, buckets[idx]);
        buckets[idx] = node;

        if (tail == null) {
            head = tail = node;
        } else {
            tail.after = node;
            node.before = tail;
            tail = node;
        }

        size++;

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

                if (prev == null) buckets[idx] = cur.next;
                else              prev.next = cur.next;

                Node<E> b = cur.before;
                Node<E> a = cur.after;
                if (b == null) head = a; else b.after = a;
                if (a == null) tail = b; else a.before = b;

                size--;
                return true;
            }
            prev = cur;
        }
        return false;
    }
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] old = buckets;
        buckets = (Node<E>[]) new Node[old.length * 2];

        for (Node<E> cur = head; cur != null; cur = cur.after) {
            int idx = indexFor(cur.item, buckets.length);
            cur.next = buckets[idx];   // переподвешиваем только next
            buckets[idx] = cur;
        }
    }

    @Override
    public String toString() {
        String result = "[";
        Node<E> cur = head;
        boolean first = true;
        while (cur != null) {
            if (!first) result += ", ";
            result += cur.item;
            first = false;
            cur = cur.after;
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
        Node<E> cur = head;
        while (cur != null) {
            Node<E> next = cur.after;   // сохраняем заранее!
            if (!c.contains(cur.item)) {
                remove(cur.item);
                changed = true;
            }
            cur = next;
        }
        return changed;
    }


    @Override
    public Iterator<E> iterator() {
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


}
