package com.company;


public interface MyListIterator extends MyIterator {

    boolean hasNext();

    Object next();

    boolean hasPrevious();

    Object previous();

    int nextIndex();

    int previousIndex();

    void remove();

    void set(Object e);

    void add(Object e);
}
