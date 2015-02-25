package com.company;

import java.util.concurrent.RejectedExecutionException;

public class MyLinkedList implements MyList {

    private class MyLinkedNode {

        private MyLinkedNode next;
        private MyLinkedNode prev;
        private Object value;

        public MyLinkedNode() {
            next = null;
            prev = null;
            value = null;
        }

        public MyLinkedNode(Object value, MyLinkedNode prev, MyLinkedNode next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }

        public MyLinkedNode getNext() {
            return next;
        }

        public MyLinkedNode getPrev() {
            return prev;
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

        public void setPrev(MyLinkedNode next) {
            this.next = next;
        }
    }


    private class MyLinkedListIterator implements MyIterator {

        MyLinkedNode currentPlace = new MyLinkedNode(null, null, first);

        // Звали вперед или назад?
        boolean siblingCalled = false;

        int position = -1;
        int localChanges = listChanges;

        private void stopIfChanged() {
            if (localChanges != listChanges) {
                throw new RejectedExecutionException();
            }
        }

        private void stepForward() {
            if (currentPlace.getNext() == null) {
                throw new IndexOutOfBoundsException();
            }

            MyLinkedNode next = currentPlace.getNext();
            MyLinkedNode prev = currentPlace.getPrev();

            currentPlace.setNext(prev);
            currentPlace.setPrev(next);

            position++;
        }

        @Override
        public boolean hasNext() {
            stopIfChanged();
            return currentPlace.getNext() != null;
        }

        @Override
        public Object next() {
            stopIfChanged();
            stepForward();

            siblingCalled = true;
            return currentPlace.getValue();
        }

        public boolean hasPrev() {
            stopIfChanged();
            return currentPlace.getPrev() != null;
        }


        @Override
        public Object remove() {
            stopIfChanged();

            if (!siblingCalled) {
                throw new IndexOutOfBoundsException();
            }

            Object removedObj = currentPlace.getValue();
            MyLinkedNode next = currentPlace.getNext();
            MyLinkedNode prev = currentPlace.getPrev();

            next.setPrev(prev);
            prev.setNext(next);

            currentPlace = new MyLinkedNode(null, prev, next);
            siblingCalled = false;
            position--;

            listChanges++;
            localChanges++;

            return removedObj;

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
            listChanges++;
            localChanges++;
        }

        public void insert(Object o) {
            stopIfChanged();
            MyLinkedNode newNode = new MyLinkedNode(currentPlace.getNext(), 0);
            currentPlace.setNext(newNode);
            listChanges++;
            localChanges++;
        }
    }


    public MyIterator iterator() {
        return new MyLinkedListIterator();
    }


    private MyLinkedNode first;
    private int listChanges = 0;

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
            first = new MyLinkedNode(o, null, null);
        } else {
            MyLinkedNode search = first;

            //Думал использовать size и getValue, но это 2 прохода
            while (search.getNext() != null) {
                search = search.getNext();
            }

            search.setNext(new MyLinkedNode(o, search, null));
        }

        listChanges++;
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
            first.setPrev(null);
            return removedObj;
        }

        MyLinkedNode currentNode = getNode(index);
        removedObj = currentNode.getValue();

        currentNode.getPrev().setNext(currentNode.getNext());
        currentNode.getNext().setPrev(currentNode.getPrev());

        listChanges++;
        return removedObj;
    }

    @Override
    public void clear() {
        first = null;
        listChanges++;
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
        listChanges++;
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
        MyLinkedNode current = getNode(i);
        new MyLinkedNode(o, current, current.getNext());
    }
}
