package com.company;

public class MyLinkedListIteratorTest extends MyListIteratorTest {

    private MyLinkedList linkedList;

    private MyLinkedList.MyLinkedListIterator linkedListIterator;

    @Override
    public void createList() {
        linkedList = new MyLinkedList();
    }

    @Override
    public MyList getList() {
        return linkedList;
    }

    @Override
    public MyListIterator getListIterator() {
        linkedListIterator = linkedList.new MyLinkedListIterator();
        return linkedListIterator;
    }
}