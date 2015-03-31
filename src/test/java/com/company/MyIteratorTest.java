package com.company;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public abstract class MyIteratorTest {

    protected Integer intNumber = 156;
    protected Character character = 'a';
    protected String string = "Hello";
    protected int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Before
    public abstract void createCollection();

    public abstract MyCollection getCollection();

    public abstract MyIterator getIterator();

    @Test
    public void testHasNextTrue() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        MyIterator iterator = getIterator();
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void testHasNextFalse() {
        MyCollection testCollection = getCollection();
        MyIterator iterator = getIterator();
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testNextForIntegerCharacterStringAndArrayObjects() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        testCollection.add(character);
        testCollection.add(string);
        testCollection.add(array);
        MyIterator iterator = getIterator();
        Object nextElement = iterator.next();
        Assert.assertTrue((nextElement == intNumber) || (nextElement == character) || (nextElement == string) || (nextElement == array));
        nextElement = iterator.next();
        Assert.assertTrue((nextElement == intNumber) || (nextElement == character) || (nextElement == string) || (nextElement == array));
        nextElement = iterator.next();
        Assert.assertTrue((nextElement == intNumber) || (nextElement == character) || (nextElement == string) || (nextElement == array));
        nextElement = iterator.next();
        Assert.assertTrue((nextElement == intNumber) || (nextElement == character) || (nextElement == string) || (nextElement == array));
    }

    @Test (expected = NoSuchElementException.class)
    public void testNextOutOfBounds() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.next();
    }

    @Test (expected = IllegalStateException.class)
    public void testRemoveWithoutNextOrPrevCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        MyIterator iterator = getIterator();
        iterator.remove();
    }

    @Test
    public void testRemoveTheOnlyCollectionElementWithNextCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.remove();
        Assert.assertEquals(0, testCollection.size());
    }

    @Test
    public void testRemoveTheFirstElementWithNextCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        testCollection.add(character);
        testCollection.add(string);
        testCollection.add(array);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.remove();
        Assert.assertEquals(3, testCollection.size());
        Assert.assertTrue(testCollection.contains(character));
        Assert.assertTrue(testCollection.contains(string));
        Assert.assertTrue(testCollection.contains(array));
    }

    @Test
    public void testRemoveMiddleElementWithNextCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        testCollection.add(character);
        testCollection.add(string);
        testCollection.add(array);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        Assert.assertEquals(3, testCollection.size());
        Assert.assertTrue(testCollection.contains(intNumber));
        Assert.assertTrue(testCollection.contains(string));
        Assert.assertTrue(testCollection.contains(array));
    }

    @Test
    public void testRemoveLastElementWithNextCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        testCollection.add(character);
        testCollection.add(string);
        testCollection.add(array);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.remove();
        Assert.assertEquals(3, testCollection.size());
        Assert.assertTrue(testCollection.contains(intNumber));
        Assert.assertTrue(testCollection.contains(string));
        Assert.assertTrue(testCollection.contains(character));
        Assert.assertFalse(iterator.hasNext());
    }

    @Test (expected = ConcurrentModificationException.class)
    public void testConcurrentModificationException () {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        MyIterator iterator = getIterator();
        testCollection.add(character);
        iterator.next();
    }

    @Test (expected = IllegalStateException.class)
    public void testRemoveAfterNextAndRemoveCalled() {
        MyCollection testCollection = getCollection();
        testCollection.add(intNumber);
        testCollection.add(character);
        testCollection.add(string);
        MyIterator iterator = getIterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        iterator.remove();
    }
}
