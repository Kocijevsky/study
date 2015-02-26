package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyLinkedListIteratorTest {

    Integer intNumber = 156;
    char character = 'a';
    String string = "Hello";
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void testAddToEmptyList() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.add(intNumber);
        assertEquals(intNumber, list.get(0));
    }

    @Test
    public void testAddFirstElementToNonEmptyList() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.add(character);
        assertEquals(character, list.get(0));
        assertEquals(intNumber, list.get(1));
    }

    @Test
         public void testAddMiddleElementToNonEmptyList() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.add(array);
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(string, list.get(3));
    }

    @Test
    public void testAddLastElementToNonEmptyList() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.add(array);
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(string, list.get(2));
        assertEquals(array, list.get(3));
    }
    @Test
    public void testHasNextTrue() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testHasNextFalse() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNextForIntegerCharacterStringAndArrayObjects() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertEquals(intNumber, iterator.next());
        assertEquals(character, iterator.next());
        assertEquals(string, iterator.next());
        assertEquals(array, iterator.next());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testNextOutOfBounds() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
    }

    @Test
    public void testHasPreviousTrue() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        assertTrue(iterator.hasPrevious());
    }

    @Test
    public void testHasPreviousFalse() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertFalse(iterator.hasPrevious());
    }

    @Test
    public void testPreviousForIntegerCharacterStringAndArrayObjects() throws Exception {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        assertEquals(array, iterator.previous());
        assertEquals(string, iterator.previous());
        assertEquals(character, iterator.previous());
        assertEquals(intNumber, iterator.previous());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testPreviousOutOfBounds () {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.previous();
    }

    @Test
    public void testNextIndex() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertEquals(0, iterator.nextIndex());
        iterator.add(intNumber);
        assertEquals(1, iterator.nextIndex());
        iterator.add(character);
        assertEquals(2, iterator.nextIndex());
        iterator.add(string);
        assertEquals(3, iterator.nextIndex());
        iterator.add(array);
        assertEquals(4, iterator.nextIndex());
    }

    @Test
    public void testPreviousIndex() throws Exception {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.add(intNumber);
        iterator.add(character);
        iterator.add(string);
        iterator.add(array);
        assertEquals(3, iterator.previousIndex());
        iterator.previous();
        assertEquals(2, iterator.previousIndex());
        iterator.previous();
        assertEquals(1, iterator.previousIndex());
        iterator.previous();
        assertEquals(0, iterator.previousIndex());
        iterator.previous();
        assertEquals(-1, iterator.previousIndex());
    }

    @Test (expected = IllegalStateException.class)
    public void testRemoveWithoutNextOrPrevCalled() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.add(intNumber);
        iterator.remove();
    }

    @Test
    public void testRemoveTheOnlyListElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.remove();
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveTheFirstElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(character, list.get(0));
        assertEquals(string, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(-1, iterator.previousIndex());
    }

    @Test
    public void testRemoveGenericElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(string, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(0, iterator.previousIndex());
    }

    @Test
    public void testRemoveLastElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(string, list.get(2));
        assertFalse(iterator.hasNext());
        assertEquals(list.size(), iterator.nextIndex());
    }

    @Test
    public void testRemoveTheOnlyListElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.previous();
        iterator.remove();
        assertEquals(0, list.size());
        assertEquals(-1, iterator.previousIndex());
        assertEquals(0, iterator.nextIndex());
    }

    @Test
    public void testRemoveTheFirstElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.previous();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(character, list.get(0));
        assertEquals(string, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(-1, iterator.previousIndex());
    }

    @Test
    public void testRemoveGenericElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(string, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(0, iterator.previousIndex());
    }

    @Test
    public void testRemoveLastElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        list.add(array);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.remove();
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(string, list.get(2));
        assertFalse(iterator.hasNext());
        assertEquals(list.size(), iterator.nextIndex());
    }

    @Test (expected = IllegalStateException.class)
    public void testSetWithoutNextOrPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.set(intNumber);
    }

    @Test
    public void testSetTheOnlyListElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        assertEquals(intNumber, iterator.next());
        iterator.set(character);
        assertEquals(character, iterator.previous());
    }

    @Test
    public void testSetTheFirstElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(array, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(string, list.get(2));
        assertEquals(1, iterator.nextIndex());
    }

    @Test
    public void testSetGenericElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(array, list.get(1));
        assertEquals(string, list.get(2));
        assertEquals(2, iterator.nextIndex());
    }

    @Test
    public void testSetLastElementWithNextCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(array, list.get(2));
        assertFalse(iterator.hasNext());
        assertEquals(list.size(), iterator.nextIndex());
    }

    @Test
    public void testSetTheOnlyListElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.previous();
        iterator.set(character);
        assertEquals(1, list.size());
        assertEquals(character, iterator.next());
    }

    @Test
    public void testSetTheFirstElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.previous();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(array, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(string, list.get(2));
        assertEquals(-1, iterator.previousIndex());
    }

    @Test
    public void testSetGenericElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(array, list.get(1));
        assertEquals(string, list.get(2));
        assertEquals(0, iterator.previousIndex());
    }

    @Test
    public void testSetLastElementWithPreviousCalled() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        list.add(character);
        list.add(string);
        MyListIterator iterator = list.new MyLinkedListIterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.previous();
        iterator.set(array);
        assertEquals(3, list.size());
        assertEquals(intNumber, list.get(0));
        assertEquals(character, list.get(1));
        assertEquals(array, list.get(2));
        assertEquals(2, iterator.nextIndex());
    }
}