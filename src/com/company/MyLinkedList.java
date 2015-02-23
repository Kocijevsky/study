package com.company;

import java.util.ConcurrentModificationException;

public class MyLinkedList implements MyList {

    private class MyLinkedNode {

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

        MyLinkedNode currentPosition;
        int changes;
        int currentPositionIndex;
        boolean isNextCalled = false;
        boolean isPreviousCalled = false;

        public MyLinkedListIterator(){
            changes = changesCounter;
            currentPosition.setNext(first);
            currentPositionIndex = -1;
        }

        private void throwModificationException (){
            if (this.changes != changesCounter){
                throw new ConcurrentModificationException();
            }
        }

        private void cleanNextAndPreviousCallsandIncrementChangesCounter(){
            isNextCalled = false;
            isPreviousCalled = false;
            changesCounter++;
        }

        public boolean hasNext() {
            throwModificationException();
            return currentPosition.next != null;
        }

        public Object next() {
            throwModificationException();
            if (currentPosition.getNext() == null){
                throw new IndexOutOfBoundsException();
            } else {
                currentPosition = currentPosition.getNext();
                currentPositionIndex++;
                isNextCalled = true;
                isPreviousCalled = false;
                return currentPosition;
            }
        }

        public boolean hasPrevious() {
            throwModificationException();
            return currentPosition.previous != null;
        }

        public Object previous() {
            throwModificationException();
            if (currentPosition.getPrevious() == null){
                throw new IndexOutOfBoundsException();
            } else {
                currentPosition = currentPosition.getPrevious();
                currentPositionIndex--;
                isNextCalled = false;
                isPreviousCalled = true;
                return currentPosition;
            }
        }

        public int nextIndex() {
            throwModificationException();
            
            if (hasNext()) {
                return (currentPositionIndex + 1);
            } else {
                return -1;
            }
        }

        public int previousIndex() {
            throwModificationException();
            if (hasPrevious()) {
                return indexOf(currentPositionIndex - 1);
            } else {
                return -1;
            }
        }

        public void remove() { // ??? Может, нужно next и previous считать, и ремувить сразу кучу элементов?
            throwModificationException();
            if (!isNextCalled && !isPreviousCalled){
                throw new IllegalStateException();
            }
            if (isNextCalled){
                currentPosition = currentPosition.getPrevious();
                MyLinkedList.this.remove(currentPositionIndex);
                currentPositionIndex--;
                cleanNextAndPreviousCallsandIncrementChangesCounter();
            } else {
                currentPosition = currentPosition.getNext();
                MyLinkedList.this.remove(currentPositionIndex);
                cleanNextAndPreviousCallsandIncrementChangesCounter();
            }
        }

        public void set(Object e) {
            throwModificationException();
            if (!isNextCalled && !isPreviousCalled){
                throw new IllegalStateException();
            } else {
                MyLinkedList.this.put(currentPositionIndex, e);
                cleanNextAndPreviousCallsandIncrementChangesCounter();
            }
        }

        public void add(Object e) {
            throwModificationException();
            MyLinkedList.this.insert(currentPositionIndex, e);
            currentPositionIndex++;
            cleanNextAndPreviousCallsandIncrementChangesCounter();
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
    private int changesCounter = 0;

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
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            removedObj = first.getValue();
            first = first.getNext();
            first.setPrevious(null);
            changesCounter++;
            return removedObj;
        }
        MyLinkedNode previousNode = getNode(index - 1);
        MyLinkedNode nextNode = previousNode.getNext().getNext();
        removedObj = previousNode.getNext().getValue();
        previousNode.setNext(nextNode);
        nextNode.setPrevious(previousNode);
        changesCounter++;
        return removedObj;
    }

    public void clear() {
        first = new MyLinkedNode();
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
        if (i >= size()){
            throw new IndexOutOfBoundsException();
        }
        MyLinkedNode node = getNode(i);
        node.setValue(o);
        changesCounter++;
        }

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

    public void insert(int i, Object o) {
        int size = size();
        if (i > size) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0){
            first = new MyLinkedNode(first, o, null);
            changesCounter++;
        }
        if (i == size) {
            add(o);
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
