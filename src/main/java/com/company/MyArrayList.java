package com.company;

import java.util.ConcurrentModificationException;

public class MyArrayList implements MyList {

    private int numberOfChanges = 0;
    private int internalSize = 0;
    private Object[] elements = new Object[16];


    private class MyArrayListIterator implements MyListIterator {

        private int localChanges;
        private int nextPosition;
        private boolean siblingCalled;
        private boolean isLastCalledNext;

        MyArrayListIterator() {
            siblingCalled = false;
            isLastCalledNext = false;
            localChanges = numberOfChanges;
            nextPosition = 0;
        }

        private void stopIfChanged() {
            if (localChanges != numberOfChanges) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            stopIfChanged();
            return nextPosition < internalSize;
        }

        public boolean hasPrevious() {
            stopIfChanged();
            return nextPosition > 0;
        }

        @Override
        public Object remove() {
            stopIfChanged();
            int indexToRemove;

            if (!siblingCalled) {
                throw new IllegalStateException();
            }
            if (isLastCalledNext) {
                indexToRemove = nextPosition - 1;
            } else {
                indexToRemove = nextPosition;
            }
            localChanges++;

            return MyArrayList.this.remove(indexToRemove);
        }

        @Override
        public void set(Object e) {
            stopIfChanged();
            MyArrayList.this.put(nextPosition - 1, e);
            localChanges++;
        }

        @Override
        public void add(Object e) {
            stopIfChanged();
            MyArrayList.this.insert(nextPosition - 1, e);
            localChanges++;
        }

        @Override
        public Object next() {
            stopIfChanged();
            if (nextPosition < internalSize) {
                nextPosition++;
                siblingCalled = true;
                isLastCalledNext = true;

                return elements[nextPosition - 1];
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public Object previous() {
            stopIfChanged();
            if (nextPosition - 1 >= 0) {
                nextPosition--;
                siblingCalled = true;
                isLastCalledNext = false;

                return elements[nextPosition];
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int nextIndex() {
            return nextPosition;
        }

        @Override
        public int previousIndex() {
            return nextPosition-1;
        }
    }


    public MyIterator iterator() {
        return new MyArrayListIterator();
    }


    public void add(Object o) {

        enlargeIfBig();

        elements[internalSize] = o;
        internalSize++;
        numberOfChanges++;
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
    public void insert(int index, Object o) {
        enlargeIfBig();
        System.arraycopy(elements, index, elements, index + 1, elements.length - (index + 1));
        elements[index] = o;

        internalSize++;
        numberOfChanges++;
    }

    public int size() {
        return internalSize;
    }

    public boolean isEmpty() {
        return internalSize == 0;
    }

    @Override
    public void put(int index, Object o) {
        elements[index] = o;
        numberOfChanges++;
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
        numberOfChanges++;

        return removedObject;
    }

    public void clear() {
        elements = new Object[16];
        internalSize = 0;
        numberOfChanges++;
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

    private void enlargeIfBig() {
        if (elements.length >= internalSize - 1) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
    }


}