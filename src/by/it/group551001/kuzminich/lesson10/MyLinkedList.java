package by.it.group551001.kuzminich.lesson10;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> first;   // первый узел
    private Node<E> last;    // последний узел
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;
        while (current != null) {
            sb.append(current.item);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) {
        Node<E> oldFirst = first;
        Node<E> newNode = new Node<>(null, element, oldFirst);
        first = newNode;
        if (oldFirst == null) {
            last = newNode;   // список был пустой
        } else {
            oldFirst.prev = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> oldLast = last;
        Node<E> newNode = new Node<>(oldLast, element, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;  // список был пустой
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public E element() {
        if (size == 0) throw new NoSuchElementException();
        return first.item;
    }

    @Override
    public E getFirst() {
        return element();
    }

    @Override
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        return last.item;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) return null;
        E value = first.item;
        Node<E> next = first.next;
        first.item = null;
        first.next = null;
        first = next;
        if (next == null) {
            last = null;   // список стал пустым
        } else {
            next.prev = null;
        }
        size--;
        return value;
    }

    @Override
    public E pollLast() {
        if (size == 0) return null;
        E value = last.item;
        Node<E> prev = last.prev;
        last.item = null;
        last.prev = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> current = first;
        while (current != null) {
            if (current.item == null ? o == null : current.item.equals(o)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }


    // Удаление по индексу (0-based), возвращает удалённый элемент
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E value = current.item;
        unlink(current);
        return value;
    }


    // Отвязка узла от списка
    private void unlink(Node<E> node) {
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
    }

    // (заглушки)

    @Override public boolean offer(E e) { add(e); return true; }
    @Override public boolean offerFirst(E e) { addFirst(e); return true; }
    @Override public boolean offerLast(E e) { addLast(e); return true; }
    @Override public E remove() { return poll(); }
    @Override public E removeFirst() { return pollFirst(); }
    @Override public E removeLast() { return pollLast(); }
    @Override public E peek() { return size == 0 ? null : first.item; }
    @Override public E peekFirst() { return peek(); }
    @Override public E peekLast() { return size == 0 ? null : last.item; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { throw new UnsupportedOperationException(); }
    @Override public void push(E e) { throw new UnsupportedOperationException(); }
    @Override public E pop() { throw new UnsupportedOperationException(); }
    @Override public boolean removeFirstOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean removeLastOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> descendingIterator() { throw new UnsupportedOperationException(); }
}