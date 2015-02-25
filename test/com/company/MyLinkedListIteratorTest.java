package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyLinkedListIteratorTest {

    Integer intNumber = 156;
    char character = 'a';
    String string = "Hello";
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

//    @Test
//    public void testHasNext() throws Exception {
//
//    }
//
//    @Test
//    public void testNext() throws Exception {
//
//    }
//
//    @Test
//    public void testHasPrevious() throws Exception {
//
//    }
//
//    @Test
//    public void testPrevious() throws Exception {
//
//    }
//
//    @Test
//    public void testNextIndex() throws Exception {
//
//    }
//
//    @Test
//    public void testPreviousIndex() throws Exception {
//
//    }
//
//    @Test
//    public void testRemove() throws Exception {
//
//    }
//
//    @Test
//    public void testSet() throws Exception {
//
//    }

    @Test
    public void testAdd() {
        MyLinkedList list = new MyLinkedList();
        list.add(intNumber);
        System.out.println(list.first.getValue());
        MyLinkedList.MyLinkedListIterator iterator = list.new MyLinkedListIterator();
        System.out.println(list.size());


        iterator.add(intNumber);
        System.out.println(list.size());
        iterator.add(character);
        System.out.println(list.size());
        iterator.add(array);
        System.out.println(list.size());
        iterator.add(string);
        System.out.println(list.size());
    }
}