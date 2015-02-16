package com.company;

/**
 * Created by akonopko on 09.02.15.
 */
public interface MyList {

    public void add(Object o);

    public Object get(int i);

    public Object remove(int i);

    public void clear();

    public boolean contains(Object o);

    public int size();

    public boolean isEmpty();

    public void put(int i, Object o);

    public int indexOf(Object o);

    public void insert(int i, Object o);

}
