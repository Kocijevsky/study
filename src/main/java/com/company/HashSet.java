package com.company;

/**
 * Created by maxvektor on 29.03.15.
 */
public class HashSet implements MyCollection {

    private double loadFactor;
    private int numberOfElements;
    private MyLinkedList[] buckets;

    public HashSet() {
        loadFactor = 0.75;
        numberOfElements = 0;
        buckets = new MyLinkedList[16];
    }

    private void enlargeIfNeeded() {
        int length = buckets.length;
        if ((double) length / (double) numberOfElements > loadFactor) {

            MyLinkedList[] oldBuckets = buckets;
            buckets = new MyLinkedList[length * 2];
            // Идем по бакетам
            for (int i = 0; i < length; i++) {
                if (oldBuckets[i] != null) {
                    MyIterator iterator = oldBuckets[i].iterator();
                    // Идем по элементам
                    while (iterator.hasNext()) {
                        add(iterator.next());
                    }
                }
            }
        }
    }

    private boolean constanceHash(Object o) {
        MyLinkedList bucket = getBucket(o);
        if (bucket != null) {
            MyIterator iterator = bucket.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().hashCode() == o.hashCode()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getBucketNumber(Object o) {
        return o.hashCode() % buckets.length;
    }

    private MyLinkedList getBucket(Object o) {
        return buckets[getBucketNumber(o)];
    }

    public boolean add(Object o) {
        if (constanceHash(o)) {
            return false;
        }

        enlargeIfNeeded();
        int bucketNumber = getBucketNumber(o);

        if (buckets[bucketNumber] == null) {
            buckets[bucketNumber] = new MyLinkedList();
        }
        buckets[bucketNumber].add(o);
        numberOfElements++;
        return true;
    }


    @Override
    public boolean remove(Object o) {
        MyLinkedList bucket = getBucket(o);
        if (bucket == null || bucket.isEmpty()) {
            return false;
        }
        MyIterator iterator = bucket.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                iterator.remove();
                numberOfElements--;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean contains(Object o) {
        MyLinkedList bucket = getBucket(o);
        if (bucket == null || bucket.isEmpty()) {
            return false;
        }
        MyIterator iterator = bucket.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }


    @Override
    public MyIterator iterator() {
        return null;
    }
}
