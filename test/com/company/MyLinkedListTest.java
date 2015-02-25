package com.company;

import static org.junit.Assert.*;

public class MyLinkedListTest {

    Integer intNumber = 156;
    char character = 'a';
    String string = "Hello";
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @org.junit.Test
    public void testIsEmptyMethodForEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        assertTrue(testList.isEmpty());
    }

    @org.junit.Test
    public void testIsEmptyMethodForNonEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.add("hello");
        assertFalse(testList.isEmpty());
    }

    @org.junit.Test
    public void testAddMethodToEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        assertTrue(testList.isEmpty());
        testList.add("Hello world");
        assertFalse(testList.isEmpty());
        assertEquals("Hello world", testList.get(0));
    }

    @org.junit.Test
    public void testAddMethodToNonEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        assertTrue(testList.isEmpty());
        testList.add("Hello world");
        testList.add("Hi world");
        assertFalse(testList.isEmpty());
        assertEquals("Hi world", testList.get(1));
    }

    @org.junit.Test
    public void testAddMethodForDifferentObjects(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(intNumber, testList.get(0));
        assertEquals(character, testList.get(1));
        assertEquals(string, testList.get(2));
        assertEquals(array, testList.get(3));
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
     public void testGetNodeMethodForEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.getNode(0);
    }

    @org.junit.Test
    public void testGetNodeMethodForTheFirstElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        assertEquals(testList.first, testList.getNode(0));
    }

    @org.junit.Test
    public void testGetNodeMethodForTheLastElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(testList.first.getNext().getNext().getNext(), testList.getNode(3));
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testGetNodeMethodForNegativeIndex() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.getNode(-1);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testGetNodeMethodOutOfBounds() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        testList.getNode(4);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testGetMethodForEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.get(0);
    }

    @org.junit.Test
    public void testGetMethodForTheFirstElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        assertEquals(testList.first.getValue(), testList.get(0));
    }

    @org.junit.Test
    public void testGetMethodForTheLastElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(testList.first.getNext().getNext().getNext().getValue(), testList.get(3));
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testGetMethodForNegativeIndex() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.get(-1);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testGetMethodOutOfBounds() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        testList.get(4);
    }

    @org.junit.Test
    public void testRemoveListOfOneElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.remove(0);
        assertTrue(testList.isEmpty());
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveFromEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.remove(0);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.remove(2);
    }

    @org.junit.Test
    public void testGenericRemove() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        Object removedObject = testList.remove(2);
        assertEquals(string, removedObject);
        assertEquals(character,testList.get(1));
        assertEquals(array,testList.get(2));
        assertEquals(character,testList.getNode(2).getPrevious().getValue());
        assertEquals(array,testList.getNode(1).getNext().getValue());
    }

    @org.junit.Test
    public void testRemoveFirst() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        Object removedObject = testList.remove(0);
        assertEquals(3, testList.size());
        assertEquals(intNumber, removedObject);
        assertEquals(null, testList.getNode(0).getPrevious());
        assertEquals(character, testList.get(0));
    }

    @org.junit.Test
    public void testRemoveLast() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        Object removedObject = testList.remove(3);
        assertEquals(3, testList.size());
        assertEquals(array, removedObject);
        assertEquals(null, testList.getNode(2).getNext());
    }

    @org.junit.Test
    public void testClearEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.clear();
        assertTrue(testList.isEmpty());
    }

    @org.junit.Test
    public void testClearNonEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(intNumber, testList.get(0));
        assertEquals(array, testList.get(3));
        testList.clear();
        assertTrue(testList.isEmpty());
    }

    @org.junit.Test
    public void testContainsEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        assertFalse(testList.contains(1));
    }

    @org.junit.Test
    public void testContainsFalse() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertFalse(testList.contains(1));
    }

    @org.junit.Test
    public void testContainsTrue() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertTrue(testList.contains("Hello"));
    }

    @org.junit.Test
    public void testSizeEmptyList(){
        MyLinkedList testList = new MyLinkedList();
        assertEquals(0, testList.size());
    }

    @org.junit.Test
    public void testSizeOneElementList(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        assertEquals(1, testList.size());
    }

    @org.junit.Test
    public void testSizeGenericList(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(4, testList.size());
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testPutOutOfBoundsToEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.put(1, string);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testPutNewElementToEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.put(0, string);
    }

    @org.junit.Test (expected = IndexOutOfBoundsException.class)
    public void testPutOutOfBoundsToNonEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        testList.put(4, "Some string");
    }

    @org.junit.Test
    public void testGenericPut() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        testList.put(2, "Some string");
        assertEquals("Some string", testList.get(2));
    }

    @org.junit.Test
    public void testIndexOfEmptyList() {
        MyLinkedList testList = new MyLinkedList();
        assertEquals(-1, testList.indexOf(1));
    }

    @org.junit.Test
    public void testIndexOfNonPresentElement() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(-1, testList.indexOf("Some string"));
    }

    @org.junit.Test
    public void testIndicesOfPresentElements() {
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(0, testList.indexOf(intNumber)); // ??? непонятно, почему этот тест валится =( TODO
        assertEquals(1, testList.indexOf(character));
        assertEquals(2, testList.indexOf(string));
        assertEquals(3, testList.indexOf(array));
    }

    @org.junit.Test
    public void testInsertToEmptyList(){
        MyLinkedList testList = new MyLinkedList();
        testList.insert(0, string);
        assertEquals(string, testList.first.getValue());
        assertEquals(null, testList.first.getPrevious());
        assertEquals(null, testList.first.getNext());
    }

    @org.junit.Test
    public void testInsertFirstElement(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(4, testList.size());
        String newString = "Hi there";
        testList.insert(0, newString);
        assertEquals(5, testList.size());
        assertEquals(newString, testList.first.getValue());
        assertEquals(intNumber, testList.first.getNext().getValue());
        assertEquals(newString, testList.first.getNext().getPrevious().getValue());
    }

    @org.junit.Test
    public void testInsertLastElement(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(4, testList.size());
        String newString = "Hi there";
        testList.insert(4, newString);
        assertEquals(newString, testList.get(4));
        assertEquals(null, testList.getNode(4).getNext());
        assertEquals(newString, testList.getNode(3).getNext().getValue());
    }

    @org.junit.Test
    public void testInsertGenericElement(){
        MyLinkedList testList = new MyLinkedList();
        testList.add(intNumber);
        testList.add(character);
        testList.add(string);
        testList.add(array);
        assertEquals(4, testList.size());
        String newString = "Hi there";
        testList.insert(2, newString);
        assertEquals(newString, testList.get(2));
        assertEquals(string, testList.first.getNext().getNext().getNext().getValue());
        assertEquals(character, testList.first.getNext().getNext().getPrevious().getValue());
        assertEquals(newString, testList.getNode(3).getPrevious().getValue());
    }
}