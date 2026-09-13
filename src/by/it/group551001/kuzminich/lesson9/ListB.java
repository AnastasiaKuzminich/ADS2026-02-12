package by.it.group551001.kuzminich.lesson9;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    private Object[] data;
    private int size;

    public ListB() {
        data = new Object[10];
        size = 0;
    }

    private void grow() {
        Object[] newData = new Object[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);

            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == data.length) {
            grow();
        }
        data[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        E removed = (E) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (size == data.length){
            grow();
        }
        size++;
        for (int i = size-1; i > index; i--){
            data[i] = data[i-1];
        }
        data[index] = element;

    }

    @Override
    public boolean remove(Object o) { //Удалить элемент по значению, а не по индексу.
        for (int i=0; i<size; i++){
            if (data[i].equals(o)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        E del_elem = (E)data[index];
        data[index] = element;

        return del_elem;
    }


    @Override
    public boolean isEmpty() {

        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i<size; i++){
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i<size; i++){
            if (data[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return (E)data[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i<size; i++){
            if (data[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size-1; i >= 0; i--){
            if (data[i].equals(o)){
                return i;
            }
        }
        return -1;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element: c){
            if (!contains(element)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object elem: c){
            add((E)elem);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elem: c){
            add(index, (E)elem);
            index++;
        }
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;

        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(data[i])) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) { // оставить только совпадения
        boolean changed = false;
        for (int i = size-1; i>=0; i--){
            if (!c.contains(data[i])){
                remove(i);
                changed = true;
            }
        }
        return changed;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) { // fromIndex включительно и toIndex НЕ включительно
        ListA<E> sub = new ListA<>();

        for (int i = fromIndex; i<toIndex; i++){
            sub.add((E) data[i]);
        }
        return sub;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {  // создаём объект прямо здесь

            int cursor = 0;         // закладка, начинаем с 0

            @Override
            public boolean hasNext() {
                return cursor < size; // есть ли ещё элементы?
            }

            @Override
            public E next() {
                return (E) data[cursor++]; // вернуть элемент и сдвинуть закладку
            }
        };
    }

}
