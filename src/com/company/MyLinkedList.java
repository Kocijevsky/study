package com.company;

import java.util.ConcurrentModificationException;

public class MyLinkedList implements MyList {

    class MyLinkedNode {

        private MyLinkedNode next;
        private Object value;
        private MyLinkedNode previous;

        public MyLinkedNode() {
            next = null;
            value = null;
            previous = null;
        }

        public MyLinkedNode(MyLinkedNode next, Object value, MyLinkedNode previous) {
            this.next = next;
            this.value = value;
            this.previous = previous;
        }

        public MyLinkedNode getNext() {
            return next;
        }

        public Object getValue() {
            return value;
        }

        public MyLinkedNode getPrevious() { return previous; }

        public void setValue(Object value) {
            this.value = value;
        }

        public void setNext(MyLinkedNode next) {
            this.next = next;
        }

        public void setPrevious (MyLinkedNode previous) { this.previous = previous; }
    }


    public class MyLinkedListIterator implements MyListIterator {

        MyLinkedNode virtualCurrentNode = new MyLinkedNode();

        int iteratorChangesCounter;
        int nextPositionIndex;
        boolean isNextCalled = false;
        boolean isPreviousCalled = false;

        public MyLinkedListIterator(){
            iteratorChangesCounter = changesCounter;
            if (!isEmpty()) { virtualCurrentNode.setNext(first); }
            nextPositionIndex = 0;
        }

        private void throwModificationException (){
            if (iteratorChangesCounter != changesCounter){
                throw new ConcurrentModificationException();
            }
        }

        private void cleanNextAndPreviousCallsAndIncrementChangesCounter(){
            isNextCalled = false;
            isPreviousCalled = false;
            iteratorChangesCounter++;
            changesCounter++;
        }

        public boolean hasNext() {
            throwModificationException();
            return virtualCurrentNode.next != null;
        }

        public Object next() {
            throwModificationException();
            if (virtualCurrentNode.getNext() == null){
                throw new IndexOutOfBoundsException();
            } else {
                virtualCurrentNode.setPrevious(virtualCurrentNode.getNext());
                virtualCurrentNode.setNext(virtualCurrentNode.getNext().getNext());
                nextPositionIndex++;
                isNextCalled = true;
                isPreviousCalled = false;
                return virtualCurrentNode.getPrevious().getValue();
            }
        }

        public boolean hasPrevious() {
            throwModificationException();
            return virtualCurrentNode.previous != null;
        }

        public Object previous() {
            throwModificationException();
            if (virtualCurrentNode.getPrevious() == null){
                throw new IndexOutOfBoundsException();
            } else {
                virtualCurrentNode.setNext(virtualCurrentNode.getPrevious());
                virtualCurrentNode.setPrevious(virtualCurrentNode.getPrevious().getPrevious());
                nextPositionIndex--;
                isNextCalled = false;
                isPreviousCalled = true;
                return virtualCurrentNode.getNext().getValue();
            }
        }

        public int nextIndex() {
            throwModificationException();
            return (nextPositionIndex);
        }

        public int previousIndex() {
            throwModificationException();
            return (nextPositionIndex - 1);
        }

        public void remove() { // ??? Может, нужно next и previous считать, и ремувить сразу кучу элементов?
            throwModificationException();
            if (!isNextCalled && !isPreviousCalled){
                throw new IllegalStateException();
            }
            if (isNextCalled){
                if (virtualCurrentNode.getNext() != null) {
                    MyLinkedNode newNext = virtualCurrentNode.getNext();
                    newNext.setPrevious(virtualCurrentNode.getPrevious().getPrevious());
                }
                if (virtualCurrentNode.getPrevious().getPrevious() != null) {
                    MyLinkedNode newPrevious = virtualCurrentNode.getPrevious().getPrevious();
                    newPrevious.setNext(virtualCurrentNode.getNext());
                    virtualCurrentNode.setPrevious(newPrevious);
                } else {
                    virtualCurrentNode.setPrevious(null);
                    first = virtualCurrentNode.getNext();
                }
                nextPositionIndex--;
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            } else {
                if (virtualCurrentNode.getPrevious() != null) {
                    MyLinkedNode newPrevious = virtualCurrentNode.getPrevious();
                    newPrevious.setNext(virtualCurrentNode.getNext().getNext());
                } else {
                    first = virtualCurrentNode.getNext().getNext();
                }
                if (virtualCurrentNode.getNext().getNext() != null) {
                    MyLinkedNode newNext = virtualCurrentNode.getNext().getNext();
                    newNext.setPrevious(virtualCurrentNode.getPrevious());
                    virtualCurrentNode.setNext(newNext);
                } else {
                    virtualCurrentNode.setNext(null);
                }
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            }
        }

        public void set(Object e) {
            throwModificationException();
            if (!isNextCalled && !isPreviousCalled){
                throw new IllegalStateException();
            } if (isNextCalled){
                virtualCurrentNode.getPrevious().setValue(e);
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            } else {
                virtualCurrentNode.getNext().setValue(e);
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            }
        }

        public void add(Object e) {
            throwModificationException();
            if (isEmpty()) {
                first = new MyLinkedNode(null, e, null);
                virtualCurrentNode.setPrevious(first);
                nextPositionIndex++;
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            } else {
                MyLinkedNode newNode = new MyLinkedNode(virtualCurrentNode.getNext(), e, virtualCurrentNode.getPrevious());
                if (newNode.getNext() != null) {
                    MyLinkedNode nextNode = virtualCurrentNode.getNext();
                    nextNode.setPrevious(newNode);
                }
                if (newNode.getPrevious() != null) {
                    MyLinkedNode previousNode = virtualCurrentNode.getPrevious();
                    previousNode.setNext(newNode);
                } else {
                    first = newNode;
                }
                virtualCurrentNode.setPrevious(newNode);
                nextPositionIndex++;
                cleanNextAndPreviousCallsAndIncrementChangesCounter();
            }
        }
    }

    public MyIterator iterator() {
        return new MyLinkedListIterator();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }


    MyLinkedNode first;
    private int changesCounter = 0;

    MyLinkedNode getNode(int index) {
        MyLinkedNode search = first;
        if (search == null || index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            if (search == null) {
                throw new IndexOutOfBoundsException();
            }
            search = search.getNext();
        }
        return search;
    }

    public void add(Object o) {
        if (isEmpty()) {
            first = new MyLinkedNode(null, o, null);
        } else {
            MyLinkedNode search = first;

            while (search.getNext() != null) {
                search = search.getNext();
            }
            search.setNext(new MyLinkedNode(null, o, search));
        }
        changesCounter++;
    }

    public Object get(int index) {
        MyLinkedNode search = getNode(index);
        return search.getValue();
    }

    public Object remove(int index) {
        Object removedObj;
        if (isEmpty()) { throw new IndexOutOfBoundsException(); }
        if (index == 0) {
            removedObj = first.getValue();
            first = first.getNext();
            if (first != null){ first.setPrevious(null); }
            changesCounter++;
            return removedObj;
        }
        MyLinkedNode previousNode = getNode(index - 1);
        MyLinkedNode nextNode = previousNode.getNext().getNext();
        removedObj = previousNode.getNext().getValue();
        previousNode.setNext(nextNode);
        if (nextNode != null) { nextNode.setPrevious(previousNode); }
        changesCounter++;
        return removedObj;
    }

    public void clear() {
        first = null;
        changesCounter++;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public int size() {
        MyLinkedNode search = first;
        int size = 0;
        while (search != null) {
            search = search.getNext();
            size++;
        }
        return size;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void put(int i, Object o) {
        MyLinkedNode node = getNode(i);
        node.setValue(o);
        changesCounter++;
        }

    public int indexOf(Object object) {
        MyLinkedNode search = first;
        for (int i = 0; search != null; i++) {
            Object searchObject = search.getValue();
            if (searchObject == object) {
                return i;
            }
            search = search.getNext();
        }
        return -1;
    }

    public void insert(int i, Object o) {
        int size = size();
        if (i > size) {
            throw new IndexOutOfBoundsException();
        } if (i == size) {
            add(o);
            return;
        } if (i == 0){
            MyLinkedNode nextNode = first;
            first = new MyLinkedNode(nextNode, o, null);
            nextNode.setPrevious(first);
            changesCounter++;
        } else{
            MyLinkedNode previousNode = getNode(i - 1);
            MyLinkedNode nextNode = previousNode.getNext();
            MyLinkedNode newNode = new MyLinkedNode(nextNode, o, previousNode);
            previousNode.setNext(newNode);
            nextNode.setPrevious(newNode);
            changesCounter++;
        }

    }
}
