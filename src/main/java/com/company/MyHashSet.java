package com.company;

/**
 * Created by Катя on 01.03.2015.
 */
public class MyHashSet implements MyCollection {

    private int numberOfElements = 0;
    private int setCapacity = 16;
    private float loadFactor = (float) 0.75; // ??? что за дела?
    private MyLinkedNode[] hashSetArray = new MyLinkedNode[setCapacity];

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

    private void increaseSetSizeAndCalculateNewBucketNumbers() {
        MyLinkedNode[] oldHashSetArray = hashSetArray;
        hashSetArray = new MyLinkedNode[setCapacity*2];
        numberOfElements = 0;
        for (MyLinkedNode currentNode : oldHashSetArray){
            while (currentNode != null){
                add(currentNode.getValue());
                currentNode = currentNode.getNext();
            }
        }
        setCapacity = setCapacity * 2;
    }

    public boolean add(Object o){
        if (containsHashOf(o)){
            return false;
        }

        if ((float)numberOfElements / (float)setCapacity>= loadFactor) {
            increaseSetSizeAndCalculateNewBucketNumbers();
        }

        int hash = o.hashCode();
        int bucketNumber = hash % setCapacity;

        if (hashSetArray[bucketNumber] == null) {
            hashSetArray[bucketNumber] = new MyLinkedNode (null, o, null);
            numberOfElements ++;
            return true;
        } else {
            MyLinkedNode currentNode = hashSetArray[bucketNumber];
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            MyLinkedNode newNode = new MyLinkedNode(null, o, currentNode);
            currentNode.setNext(newNode);
            numberOfElements ++;
            return true;
        }
    }

    public int size() {
        return numberOfElements;
    }

    public boolean isEmpty() {
        return (numberOfElements == 0);
    }

    public boolean contains(Object o) {
        int hashBucket = o.hashCode() % setCapacity;
        MyLinkedNode searchNode = hashSetArray[hashBucket];
        while (searchNode != null) {
            if (searchNode.getValue().equals(o)) {
                return true;
            }
            searchNode = searchNode.getNext();
        }
        return false;
    }

    private boolean containsHashOf (Object o) {
        int hashBucket = o.hashCode() % setCapacity;
        MyLinkedNode searchNode = hashSetArray[hashBucket];
        while (searchNode != null) {
            if (searchNode.getValue().hashCode() == o.hashCode()) {
                return true;
            }
            searchNode = searchNode.getNext();
        }
        return false;
    }

    @Override
    public MyIterator iterator() {
        return null;
    }

    public boolean remove(Object o) {
        int hashBucket = o.hashCode() % setCapacity;
        MyLinkedNode searchNode = hashSetArray[hashBucket];
        while (searchNode != null) {
            if (searchNode.getValue() == o){
                MyLinkedNode previousNode;
                MyLinkedNode nextNode;
                if (searchNode.getPrevious() != null){
                    previousNode = searchNode.getPrevious();
                    previousNode.setNext(searchNode.getNext());
                }else if (searchNode.getNext() != null){
                    nextNode = searchNode.getNext();
                    nextNode.setPrevious(searchNode.getPrevious());
                    hashSetArray[hashBucket] = nextNode;
                } else {
                    hashSetArray[hashBucket] = null;
                }
                numberOfElements--;
                return true;
            }
            searchNode = searchNode.getNext();
        }
        return false;
    }
}
