package com.company;

import java.util.concurrent.RejectedExecutionException;

public class MyLinkedList implements MyList {

    private class MyLinkedNode {

        private MyLinkedNode next;
        private Object value;

        public MyLinkedNode() {
            next = null;
            value = null;
        }

        public MyLinkedNode(MyLinkedNode next, Object value) {
            this.next = next;
            this.value = value;
        }

        public MyLinkedNode getNext() {
            return next;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public void setNext(MyLinkedNode next) {
            this.next = next;
        }
    }


    private class MyLinkedListIterator implements MyIterator {

        MyLinkedNode currentPlace = new MyLinkedNode(first, null);
        int position = -1;
        int changes = nOfChanges;

        private void stopIfChanged() {
            if (changes != nOfChanges) {
                throw new RejectedExecutionException();
            }
        }

        private void stepForward() {
            if (currentPlace.next == null) {
                throw new IndexOutOfBoundsException();
            }
            currentPlace = currentPlace.getNext();
            position++;
        }

        @Override
        public boolean hasNext() {
            stopIfChanged();
            return currentPlace.getNext() != null;
        }

        @Override
        public void remove() {
            stopIfChanged();
            MyLinkedList.this.remove(position);
            changes++;
        }

        @Override
        public Object next() {
            stopIfChanged();
            stepForward();

            return currentPlace.getValue();
        }

        public int nextIndex() {
            if (currentPlace.next == null) {
                throw new IndexOutOfBoundsException();
            }
            return position + 1;
        }


        public void set(Object o) {
            stopIfChanged();
            stepForward();
            currentPlace.setValue(o);

            // тут не уверен на 100%;
            // В общем случае на структуру не влияет вроде бы
            nOfChanges++;
            changes++;
        }

        public void insert(Object o) {
            stopIfChanged();
            MyLinkedNode newNode = new MyLinkedNode(currentPlace.getNext(), 0);
            currentPlace.setNext(newNode);
            nOfChanges++;
            changes++;
        }
    }


    public MyIterator iterator() {
        return new MyLinkedListIterator();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }


    private MyLinkedNode first;
    private int nOfChanges = 0;

    private MyLinkedNode getNode(int index) {
        MyLinkedNode search = first;

        for (int i = 0; i < index; i++) {
            if (search == null) {
                throw new IndexOutOfBoundsException();
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
        nOfChanges++;
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
            throw new IndexOutOfBoundsException();
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

        nOfChanges++;
        return removedObj;
    }

    @Override
    public void clear() {
        first = null;
        nOfChanges++;
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
        nOfChanges++;
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
        nOfChanges++;
    }
}
