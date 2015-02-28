package com.company;


public interface MyListIterator extends MyIterator {

    boolean hasPrevious();

    Object previous();

    int nextIndex();

    int previousIndex();

    Object remove();

    void set(Object e);

    void add(Object e);
}
