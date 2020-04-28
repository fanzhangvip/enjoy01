package com.zero.genericsdemo.generics_inheritance_subtypes_05.generic_classes_subtypin;

import com.zero.genericsdemo.generics_inheritance_subtypes_05.Box;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//以Collections类为例，ArrayList<E>实现List<E>，而List<E>扩展Collection<E>。
//因此，ArrayList<String>是List<String>的子类型，而List<String>是Collection<String>的子类型。
//只要你不改变类型参数，子类型关系就保留在类型之间。

interface PayloadList<E,P> extends List<E> {
    void setPayload(int index, P val);
}

public class Test {
    public static void listTest(List<String> n) { /* ... */ }

    public static void main(String[] args) {

        PayloadList<String,String> payloadList1 = new MyList<>();
        PayloadList<String,Integer> payloadList2 = new MyList<>();
        PayloadList<String,Exception> payloadList3 = new MyList<>();
        //都OK
        listTest(payloadList1);
        listTest(payloadList2);
        listTest(payloadList3);

    }
}

class MyList<E,P> implements PayloadList<E,P>{

    @Override
    public void setPayload(int index, P val) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
