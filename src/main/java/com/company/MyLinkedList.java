package com.company;

import java.util.ConcurrentModificationException;

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

        public void setPrev(MyLinkedNode prev) {
            this.prev = prev;
        }
    }

    private class MyLinkedListIterator implements MyListIterator {

        MyLinkedNode cursorNode = new MyLinkedNode(null, null, first);

        // Звали вперед или назад?
        boolean siblingCalled = false;
        boolean isLastCalledNext;

        int nextPosition = 0;
        int localChanges = listChanges;

        private void stopIfChanged() {
            if (localChanges != listChanges) {
                throw new ConcurrentModificationException();
            }
        }

        private void stepForward() {

            if (cursorNode.getNext() == null) {
                throw new IndexOutOfBoundsException();
            }

            MyLinkedNode next = cursorNode.getNext();

            cursorNode.setNext(next.getNext());
            cursorNode.setPrev(next);

            nextPosition++;
        }

        private void stepBack() {

            if (cursorNode.getPrev() == null) {
                throw new IndexOutOfBoundsException();
            }

            MyLinkedNode prev = cursorNode.getPrev();

            cursorNode.setNext(prev);
            cursorNode.setPrev(prev.getPrev());

            nextPosition--;
        }

        private Object removeNext() {
            MyLinkedNode next = cursorNode.getNext();
            MyLinkedNode prev = cursorNode.getPrev();

            Object value = prev.getValue();

            MyLinkedNode newPrev = prev.getPrev();

            cursorNode.setPrev(newPrev);
            if (newPrev != null) {
                newPrev.setNext(next);
            }
            if (next != null) {
                next.setPrev(newPrev);
            }
            if(nextPosition == 1){
                first = next;
            }

            nextPosition--;
            return value;
        }

        private Object removePrev() {
            MyLinkedNode next = cursorNode.getNext();
            MyLinkedNode prev = cursorNode.getPrev();

            Object value = next.getValue();

            MyLinkedNode newNext = next.getNext();

            cursorNode.setNext(newNext);
            if (newNext != null) {
                newNext.setPrev(prev);
            }
            if (prev != null) {
                prev.setNext(newNext);
            }
            if(nextPosition == 0){
                first = newNext;
            }

            return value;
        }


        @Override
        public boolean hasNext() {
            stopIfChanged();
            return cursorNode.getNext() != null;
        }

        @Override
        public boolean hasPrevious() {
            stopIfChanged();
            return cursorNode.getPrev() != null;
        }

        @Override
        public Object next() {
            stopIfChanged();
            stepForward();
            // -  - | -  -  -
            // переводим ссылки вперед
            // -  -   - |-  -
            // getPrev = старому getNext
            //
            // Поэтому возвращать будем значение предыдущей ноды
            siblingCalled = true;
            isLastCalledNext = true;
            return cursorNode.getPrev().getValue();
        }

        @Override
        public Object previous() {
            stopIfChanged();
            stepBack();
            siblingCalled = true;
            isLastCalledNext = false;
            return cursorNode.getNext().getValue();
        }


        @Override
        public Object remove() {
            stopIfChanged();

            if (!siblingCalled) {
                throw new IllegalStateException();
            }

            Object removedObj;

            if (isLastCalledNext) {
                removedObj = removeNext();
            } else {
                removedObj = removePrev();
            }

            isLastCalledNext = false;
            siblingCalled = false;

            listChanges++;
            localChanges++;

            return removedObj;

        }

        @Override
        public int nextIndex() {
            return nextPosition;
        }

        @Override
        public int previousIndex() {
            return nextPosition - 1;
        }

        @Override
        public void set(Object o) {
            stopIfChanged();

            if (!siblingCalled) {
                throw new IllegalStateException();
            }

            // Адовато с прев и некст
            // Если вызвали next(), то бывший следующий будет доступен по .getPrev()
            // И наоборот

            // p    n
            // _  | _   _
            // 0    1   2
            //
            //
            //     p    n
            // _   _  | _
            // 0   1    2
            //
            //
            // Но пользователь хотел обновть следующий,
            // Т.е. 1, который стал предыдущим после next()

            if (isLastCalledNext) {
                cursorNode.getPrev().setValue(o);
            } else {
                cursorNode.getNext().setValue(o);
            }
            // тут не уверен на 100%;
            // В общем случае на структуру не влияет вроде бы
            listChanges++;
            localChanges++;
        }

        @Override
        public void add(Object o) {
            stopIfChanged();

            MyLinkedNode next = cursorNode.getNext();
            MyLinkedNode prev = cursorNode.getPrev();

            MyLinkedNode newNode = new MyLinkedNode(o, prev, next);

            if (prev == null) {
                first = newNode;
            } else {
                prev.setNext(newNode);
            }

            if (next != null) {
                next.setPrev(newNode);
            }
            cursorNode.setPrev(newNode);

            siblingCalled = false;
            nextPosition++;
            listChanges++;
            localChanges++;
        }
    }

    public MyListIterator iterator() {
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
