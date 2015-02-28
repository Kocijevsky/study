package com.company;

import java.util.Iterator;

/**
 * Created by akonopko on 16.02.15.
 */
public interface MyIterator {

    boolean hasNext();

    Object remove();
    // если коолекция после создания итератора менялась
    // то этот метод кинет ConcurrentModificationException
    Object next();

}
