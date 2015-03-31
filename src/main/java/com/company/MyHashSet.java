package com.company;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Created by Катя on 01.03.2015.
 */
public class MyHashSet implements MyCollection {

    private int collectionChangesCounter = 0;
    private int numberOfElements = 0;
    private int setCapacity = 16;
    private float loadFactor = (float) 0.75; // ??? что за дела?
    private MyLinkedNode[] hashSetArray = new MyLinkedNode[setCapacity];

    public class MyHashSetIterator implements MyIterator{

        private int iteratorChangesCounter;
        private int nextPositionIndex;
        private int currentBucketPosition = 0;
        private MyLinkedNode iteratorCurrentNode = new MyLinkedNode();
        private boolean isNextCalled = false;

        public MyHashSetIterator (){
            iteratorChangesCounter = collectionChangesCounter;
            nextPositionIndex = 0;
            if (!isEmpty()){
                findNextNonEmptyBucket();
                iteratorCurrentNode.setNext(MyHashSet.this.hashSetArray[currentBucketPosition]);
            }
        }

        private void findNextNonEmptyBucket(){
            while ((MyHashSet.this.hashSetArray[currentBucketPosition] == null) && (currentBucketPosition < setCapacity)){
                currentBucketPosition++;
            }
        }

        private void checkForModification(){
            if (iteratorChangesCounter != collectionChangesCounter){
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {

            checkForModification();

            return (nextPositionIndex < MyHashSet.this.size());
        }

        public Object next() {

            checkForModification();

            if (nextPositionIndex == MyHashSet.this.size()){
                throw new NoSuchElementException();
            }

            Object objectToReturn;

            if (iteratorCurrentNode.getNext() != null){ //Check if we have something left in the current bucket
                objectToReturn = iteratorCurrentNode.getNext().getValue();
                iteratorCurrentNode.setPrevious(iteratorCurrentNode.getNext());
                iteratorCurrentNode.setNext(iteratorCurrentNode.getNext().getNext());
                isNextCalled = true;
                nextPositionIndex++;
                return objectToReturn;
            } else { //Jump to the next bucket
                findNextNonEmptyBucket();
                MyLinkedNode firstNodeFromNextBucket = MyHashSet.this.hashSetArray[currentBucketPosition];
                objectToReturn = firstNodeFromNextBucket.getValue();
                iteratorCurrentNode.setPrevious(firstNodeFromNextBucket);
                iteratorCurrentNode.setNext(firstNodeFromNextBucket.getNext());
                isNextCalled = true;
                nextPositionIndex++;
                return objectToReturn;
            }
        }

        @Override
        public void remove() {
            checkForModification();

            if (!isNextCalled){
                throw new IllegalStateException();
            }

            if (iteratorCurrentNode.getPrevious() != null){
                iteratorCurrentNode.getPrevious().setNext(iteratorCurrentNode.getNext());
            }
            if (iteratorCurrentNode.getNext() != null){
                iteratorCurrentNode.getNext().setPrevious(iteratorCurrentNode.getPrevious().getPrevious());
            }

            iteratorCurrentNode.setPrevious(iteratorCurrentNode.getPrevious().getPrevious());
            nextPositionIndex--;
            numberOfElements--;
            isNextCalled = false;
            iteratorChangesCounter++;
            collectionChangesCounter++;
        }
    }


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

    @Override
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
            collectionChangesCounter++;
            numberOfElements ++;
            return true;
        } else {
            MyLinkedNode currentNode = hashSetArray[bucketNumber];
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            MyLinkedNode newNode = new MyLinkedNode(null, o, currentNode);
            currentNode.setNext(newNode);
            collectionChangesCounter++;
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

    public MyIterator iterator() {
        return new MyHashSetIterator();
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
                collectionChangesCounter++;
                numberOfElements--;
                return true;
            }
            searchNode = searchNode.getNext();
        }
        return false;
    }
}
