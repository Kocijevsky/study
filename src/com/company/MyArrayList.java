package com.company;

public class MyArrayList implements MyList {

    private int internalSize = 0;
    private Object[] elements = new Object[16];

    private class MyArrayListIterator implements MyIterator {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public void remove() {

        }

        @Override
        public Object next() {
            return null;
        }
    }


    public MyIterator iterator() {
        return new MyArrayListIterator();
    }


    public void add(Object o) {

        if (elements.length == internalSize) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }

        elements[internalSize] = o;
        internalSize++;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < internalSize; i++) {
            if (elements[i] == o) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insert(int i, Object o) {

    }

    public int size() {
        return internalSize;
    }

    public boolean isEmpty() {
        return internalSize == 0;
    }

    @Override
    public void put(int i, Object o) {

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

        return removedObject;
    }

    public void clear() {
        elements = new Object[16];
        internalSize = 0;
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