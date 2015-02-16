package com.company;

public class MyLinkedList implements MyList {

    private MyLinkedNode first;

    private MyLinkedNode getNode(int index) {
        MyLinkedNode search = first;

        for (int i = 0; i < index; i++) {
            if (search == null) {
                throw new RuntimeException();
            }
            search = search.getNext();
        }

        return search;
    }

    @Override
    public void add(Object o) {

        if (isEmpty()) {
            first = new MyLinkedNode(null, o);
        } else {
            MyLinkedNode search = first;

            //Думал использовать size и getValue, но это 2 прохода
            while (search.getNext() != null) {
                search = search.getNext();
            }
            search.setNext(new MyLinkedNode(null, o));
        }

    }

    @Override
    public Object get(int index) {

        MyLinkedNode search = getNode(index);

        return search.getValue();
    }

    @Override
    public Object remove(int index) {
        Object removedObj;

        if (isEmpty()) {
            throw new RuntimeException();
        }

        if (index == 0) {
            removedObj = first.getValue();
            first = first.getNext();
            return removedObj;
        }

        MyLinkedNode previousNode = getNode(index - 1);
        removedObj = previousNode.getNext().getValue();

        //previousNode.getNext().getNext() = getNode(index+1), но, по идее, быстрее
        previousNode.setNext(previousNode.getNext().getNext());

        return removedObj;
    }

    @Override
    public void clear() {
        first = new MyLinkedNode();
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public int size() {
        MyLinkedNode search = first;
        int size = 0;

        while (search != null) {
            search = search.getNext();
            size++;
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void put(int i, Object o) {
        MyLinkedNode node = getNode(i);
        node.setValue(o);
    }

    @Override
    public int indexOf(Object o) {
        MyLinkedNode search = first;

        for (int i = 0; search != null; i++) {
            if (search.getValue() == o) {
                return i;
            }

            search = search.getNext();
        }

        return -1;
    }

    @Override
    public void insert(int i, Object o) {
        MyLinkedNode previousNode = getNode(i);
        MyLinkedNode newNode = new MyLinkedNode(previousNode.getNext(), o);
        previousNode.setNext(newNode);
    }
}
