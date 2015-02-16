package com.company;

import java.util.Iterator;

/**
 * Created by akonopko on 16.02.15.
 */
public interface MyCollection {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    MyIterator iterator();

    boolean remove(Object o);

}
