package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashSetTest {

    String firstString = "pollinating sandboxes";
    String secondString = "amusement & hemophilias";
    String thirdString = "amusement";
    Integer firstNumber = 16;
    Integer secondNumber = 32;

    @Test
    public void testObjectsHashCodes() {
        assertEquals(0, firstString.hashCode());
        assertEquals(0, secondString.hashCode());
        assertEquals(1611065785, thirdString.hashCode());
        assertEquals(16, firstNumber.hashCode());
        assertEquals(32, secondNumber.hashCode());
    }

    @Test
    public void testAddTestObjects() {
        MyHashSet testSet = new MyHashSet();
        assertTrue(testSet.add(firstString));
        assertTrue(testSet.add(thirdString));
        assertTrue(testSet.add(firstNumber));
        assertTrue(testSet.add(secondNumber));
    }

    @Test
    public void testAddDifferentObjectSameHash(){
        MyHashSet testSet = new MyHashSet();
        assertTrue(testSet.add(firstString));
        assertFalse(testSet.add(firstString));
        assertFalse(testSet.add(secondString));
    }

    @Test
    public void testAddManyElements() {
        MyHashSet testSet = new MyHashSet();
        for (int i = 0; i < 18; i++){
            testSet.add(i);
            assertTrue(testSet.contains(i));
            assertEquals(i + 1, testSet.size());
        }
    }

    @Test
    public void testSizeForEmptySet() {
        MyHashSet testSet = new MyHashSet();
        assertEquals(0, testSet.size());
    }

    @Test
    public void testSizeForNonEmptySet() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        assertEquals(1, testSet.size());
        testSet.add(secondString); //cannot be added - the same hash
        assertEquals(1, testSet.size());
        testSet.add(thirdString);
        assertEquals(2, testSet.size());
    }

    @Test
    public void testSizeForSetWithRemovedElements() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(thirdString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        assertEquals(4, testSet.size());
        testSet.remove(firstString);
        assertEquals(3, testSet.size());
        testSet.remove(thirdString);
        assertEquals(2, testSet.size());
        testSet.remove(firstNumber);
        assertEquals(1, testSet.size());
        testSet.remove(secondNumber);
        assertEquals(0, testSet.size());
    }

    @Test
    public void testIsEmptyForEmptySet() {
        MyHashSet testSet = new MyHashSet();
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testIsEmptyForNonEmptySet() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void testIsEmptyForSetWithRemovedElements(){
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.remove(firstString);
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testContainsForAContainedObject() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(thirdString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        assertTrue(testSet.contains(firstString));
        assertTrue(testSet.contains(thirdString));
        assertTrue(testSet.contains(firstNumber));
        assertTrue(testSet.contains(secondNumber));
    }

    @Test
    public void testContainsForNonContainedObject() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(thirdString);
        assertTrue(testSet.contains(firstString));
        assertTrue(testSet.contains(thirdString));
        assertFalse(testSet.contains(firstNumber));
    }

    @Test
    public void testContainsForDifferentObjectWithSameHash() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        assertFalse(testSet.contains(secondString));
    }

    @Test
    public void testContainsForRemovedObjects() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(thirdString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        testSet.remove(firstString);
        assertFalse(testSet.contains(firstString));
        testSet.remove(thirdString);
        assertFalse(testSet.contains(thirdString));
        testSet.remove(firstNumber);
        assertFalse(testSet.contains(firstNumber));
        testSet.remove(secondNumber);
        assertFalse(testSet.contains(secondNumber));
    }
    
    @Test
    public void testRemoveObjectFromTheBottomOfBucket() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        assertEquals(3, testSet.size());
        testSet.remove(firstString);
        assertFalse(testSet.contains(firstString));
        assertTrue(testSet.contains(firstNumber));
        assertTrue(testSet.contains(secondNumber));
        assertEquals(2, testSet.size());
    }

    @Test
    public void testRemoveObjectFromTheMiddleOfBucket() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        assertEquals(3, testSet.size());
        testSet.remove(firstNumber);
        assertTrue(testSet.contains(firstString));
        assertFalse(testSet.contains(firstNumber));
        assertTrue(testSet.contains(secondNumber));
        assertEquals(2, testSet.size());
    }

    @Test
    public void testRemoveObjectFromTheTopOfBucket() {
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        testSet.add(firstNumber);
        testSet.add(secondNumber);
        assertEquals(3, testSet.size());
        testSet.remove(secondNumber);
        assertTrue(testSet.contains(firstString));
        assertTrue(testSet.contains(firstNumber));
        assertFalse(testSet.contains(secondNumber));
        assertEquals(2, testSet.size());
    }

    @Test
    public void testRemoveFromBucketWithNotZeroHash(){
        MyHashSet testSet = new MyHashSet();
        testSet.add(thirdString);
        testSet.remove(thirdString);
        assertFalse(testSet.contains(thirdString));
        assertEquals(0, testSet.size());
    }

    @Test
    public void testRemoveFromEmptySet() {
        MyHashSet testSet = new MyHashSet();
        assertFalse(testSet.remove(secondString));
    }

    @Test
    public void testRemoveNonContainedObject(){
        MyHashSet testSet = new MyHashSet();
        testSet.add(firstString);
        assertFalse(testSet.remove(secondString));
    }
}