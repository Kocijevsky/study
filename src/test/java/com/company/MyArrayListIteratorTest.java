package com.company;

/**
 * Created by Катя on 26.03.2015.
 */
public class MyArrayListIteratorTest extends MyListIteratorTest {

    private MyArrayList arrayList;

    private MyArrayList.MyArrayListIterator arrayListIterator;

    @Override
    public void createList() {
        arrayList = new MyArrayList();
    }

    @Override
    public MyList getList() {
        return arrayList;
    }

    @Override
    public MyListIterator getListIterator() {
        arrayListIterator = arrayList.new MyArrayListIterator();
        return arrayListIterator;
    }


}
