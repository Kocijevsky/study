package com.company;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Катя on 26.03.2015.
 */
public class MyArrayListIteratorTest extends MyLinkedListIteratorTest {

    Integer intNumber = 156;
    Character character = 'a';
    String string = "Hello";
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    MyArrayList emptyList;
    MyArrayList nonEmptyList;
    MyListIterator emptyListIterator;
    MyListIterator nonEmptyListIterator;

    @Before
    public void initializeEmptyList() {
        emptyList = new MyArrayList();
    }

    @Before
    public void initializeNonEmptyList() {
        nonEmptyList = new MyArrayList();
        nonEmptyList.add(intNumber);
        nonEmptyList.add(character);
        nonEmptyList.add(string);
        nonEmptyList.add(array);
    }

    @Override
    protected void createEmptyListIterator() {
        emptyListIterator = emptyList.new MyArrayListIterator();
    }

    @Override
    protected void createNonEmptyListIterator() {
        nonEmptyListIterator = nonEmptyList.new MyArrayListIterator();
    }

    @Test
    public void testAddToEmptyList() {
        super.testAddToEmptyList();
    }

    @Test
    public void testAddFirstElementToNonEmptyList() {
        super.testAddFirstElementToNonEmptyList();
    }

    @Test
    public void testAddMiddleElementToNonEmptyList() {
        super.testAddMiddleElementToNonEmptyList();
    }
}