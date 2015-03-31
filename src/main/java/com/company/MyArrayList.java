package com.company;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class MyArrayList implements MyList {

    int changesCounter = 0;
    private int internalSize = 0;
    private Object[] elements = new Object[16];

    public class MyArrayListIterator implements MyListIterator{

        private int iteratorChangesCounter;
        private int nextPositionIndex;
        private boolean isNextCalled = false;
        private boolean isPreviousCalled = false;

        public MyArrayListIterator() {
            iteratorChangesCounter = changesCounter;
            nextPositionIndex = 0;
        }

        private void checkForModification() {
            if (iteratorChangesCounter != changesCounter){
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {
            checkForModification();
            return (size()) > nextPositionIndex;
        }

        public Object next() {

            checkForModification();

            if (nextPositionIndex == size()){
                throw new NoSuchElementException();
            }

            nextPositionIndex++;
            isNextCalled = true;
            isPreviousCalled = false;
            return MyArrayList.this.get(nextPositionIndex - 1);
        }

        public boolean hasPrevious() {
            checkForModification();
            return (nextPositionIndex > 0);
        }

        public Object previous() {
            checkForModification();
            if (nextPositionIndex == 0){
                throw new NoSuchElementException();
            }
            nextPositionIndex--;
            isNextCalled = false;
            isPreviousCalled = true;
            return MyArrayList.this.get(nextPositionIndex);
        }

        public int nextIndex() {
            checkForModification();
            return nextPositionIndex;
        }

        public int previousIndex() {
            checkForModification();
            return nextPositionIndex - 1;
        }

        public void remove() {
            checkForModification();
            if (!isPreviousCalled && !isNextCalled){
                throw new IllegalStateException();
            }
            if (isNextCalled){
                MyArrayList.this.remove(nextPositionIndex - 1);
                nextPositionIndex--;
                iteratorChangesCounter++;
                isNextCalled = false;
            } else {
                MyArrayList.this.remove(nextPositionIndex);
                iteratorChangesCounter++;
                isPreviousCalled = false;
            }
        }

        public void set(Object e) {
            checkForModification();
            if (!isPreviousCalled && !isNextCalled){
                throw new IllegalStateException();
            }
            if (isNextCalled){
                MyArrayList.this.put(nextPositionIndex - 1, e);
                iteratorChangesCounter++;
                isNextCalled = false;
            } else {
                MyArrayList.this.put(nextPositionIndex, e);
                iteratorChangesCounter++;
                isPreviousCalled = false;
            }
        }

        public void add(Object e) {
            checkForModification();
            MyArrayList.this.insert(nextPositionIndex, e);
            nextPositionIndex++;
            iteratorChangesCounter++;
            isNextCalled = isPreviousCalled = false;
        }
    }

    private void increaseArraySize(){
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    public boolean add(Object o) {

        if (elements.length == internalSize) {
            increaseArraySize();
        }

        elements[internalSize] = o;
        changesCounter++;
        internalSize++;
        return true;
    }

    public void insert(int i, Object o) {

        if (elements.length == internalSize) {
            increaseArraySize();
        }

        System.arraycopy(elements, i, elements, i + 1, internalSize - i);
        changesCounter++;
        elements[i] = o;
        internalSize++;
    }

    @Override
    public MyIterator iterator() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < internalSize; i++) {
            if (elements[i] == o) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return internalSize;
    }

    public boolean isEmpty() {
        return internalSize == 0;
    }

    public void put(int i, Object o) {
        elements[i] = o;
        changesCounter++;
    }

    public Object get(int i) {
        return elements[i];
    }

    public Object remove(int index) {
        Object removedObject;

        if (index != internalSize - 1) {
            //копируем сам в себя начиная с i+1 - всего elements.length - 1 - i штук
            System.arraycopy(elements, index + 1, elements, index, elements.length - 1 - index);
        }
        removedObject = elements[internalSize - 1];
        elements[internalSize - 1] = null;
        internalSize--;
        changesCounter++;

        return removedObject;
    }

    public void clear() {
        elements = new Object[16];
        internalSize = 0;
        changesCounter++;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < internalSize; i++) {
            string = string + elements[i];
            if (i < internalSize - 1) {
                string = string + ", ";
            }
        }

        return string;
    }


}