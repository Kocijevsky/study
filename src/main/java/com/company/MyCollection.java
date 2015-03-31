package com.company;

/**
 * Created by akonopko on 16.02.15.
 */
public interface MyCollection {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    MyIterator iterator();

    boolean remove(Object o);

    boolean add(Object o);

}
