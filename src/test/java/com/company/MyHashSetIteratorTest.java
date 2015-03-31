package com.company;

/**
 * Created by Катя on 29.03.2015.
 */
public class MyHashSetIteratorTest extends MyIteratorTest {

    private MyHashSet testHashSet;

    private MyHashSet.MyHashSetIterator testIterator;

    @Override
    public void createCollection() {
        testHashSet = new MyHashSet();
    }

    @Override
    public MyCollection getCollection() {
        return testHashSet;
    }

    @Override
    public MyIterator getIterator() {
        testIterator = testHashSet.new MyHashSetIterator();
        return testIterator;
    }
}
