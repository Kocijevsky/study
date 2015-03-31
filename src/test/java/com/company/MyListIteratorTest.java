package com.company;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public abstract class MyListIteratorTest extends MyIteratorTest{

        @Override
        public void createCollection(){
            createList();
        };

        @Override    
        public MyCollection getCollection(){
            return getList();
        };

        @Override
        public MyIterator getIterator(){
            return getListIterator();
        };

        public abstract void createList();

        public abstract MyList getList();

        public abstract MyListIterator getListIterator();

        //The following tests use ListIterator specific methods:

        /* iterator.add() tests */
        @Test
        public void testAddToEmptyList() {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            iterator.add(intNumber);
            Assert.assertEquals(intNumber, testList.get(0));
        }

        @Test
        public void testAddFirstElementToNonEmptyList() {
            MyList testList = getList();
            testList.add(intNumber);
            MyListIterator iterator = getListIterator();
            iterator.add(character);
            Assert.assertEquals(character, testList.get(0));
            Assert.assertEquals(intNumber, testList.get(1));
        }

        @Test
        public void testAddMiddleElementToNonEmptyList() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.add(array);
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(array, testList.get(2));
            Assert.assertEquals(string, testList.get(3));
        }

        @Test
        public void testAddLastElementToNonEmptyList() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.add(array);
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertEquals(array, testList.get(3));
        }

        /* iterator.hasPrevious() tests */
        @Test
        public void testHasPreviousTrue() {
            MyList testList = getList();
            testList.add(intNumber);
            MyListIterator iterator = getListIterator();
            iterator.next();
            Assert.assertTrue(iterator.hasPrevious());
        }

        @Test
        public void testHasPreviousFalse() {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            Assert.assertFalse(iterator.hasPrevious());
        }

        @Test
        public void testPreviousForIntegerCharacterStringAndArrayObjects() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            testList.add(array);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.next();
            Assert.assertEquals(array, iterator.previous());
            Assert.assertEquals(string, iterator.previous());
            Assert.assertEquals(character, iterator.previous());
            Assert.assertEquals(intNumber, iterator.previous());
        }

        @Test (expected = NoSuchElementException.class)
        public void testPreviousOutOfBounds () {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            iterator.previous();
        }


        /* iterator.nextIndex() tests */
        @Test
        public void testNextIndex() {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            Assert.assertEquals(0, iterator.nextIndex());
            iterator.add(intNumber);
            Assert.assertEquals(1, iterator.nextIndex());
            iterator.add(character);
            Assert.assertEquals(2, iterator.nextIndex());
            iterator.add(string);
            Assert.assertEquals(3, iterator.nextIndex());
            iterator.add(array);
            Assert.assertEquals(4, iterator.nextIndex());
        }

        /* iterator.previousIndex() tests */
        @Test
        public void testPreviousIndex() throws Exception {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            iterator.add(intNumber);
            iterator.add(character);
            iterator.add(string);
            iterator.add(array);
            Assert.assertEquals(3, iterator.previousIndex());
            iterator.previous();
            Assert.assertEquals(2, iterator.previousIndex());
            iterator.previous();
            Assert.assertEquals(1, iterator.previousIndex());
            iterator.previous();
            Assert.assertEquals(0, iterator.previousIndex());
            iterator.previous();
            Assert.assertEquals(-1, iterator.previousIndex());
        }

        /* iterator.remove() for situations when iterator.previous() has been called tests */
        @Test
        public void testRemoveTheOnlyListElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.remove();
            Assert.assertEquals(0, testList.size());
            Assert.assertEquals(-1, iterator.previousIndex());
            Assert.assertEquals(0, iterator.nextIndex());
        }

        @Test
        public void testRemoveTheFirstElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            testList.add(array);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.remove();
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(character, testList.get(0));
            Assert.assertEquals(string, testList.get(1));
            Assert.assertEquals(array, testList.get(2));
            Assert.assertEquals(-1, iterator.previousIndex());
        }

        @Test
        public void testRemoveMiddleElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            testList.add(array);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.previous();
            iterator.remove();
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(string, testList.get(1));
            Assert.assertEquals(array, testList.get(2));
            Assert.assertEquals(0, iterator.previousIndex());
        }

        @Test
        public void testRemoveLastElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            testList.add(array);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.previous();
            iterator.remove();
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertFalse(iterator.hasNext());
            Assert.assertEquals(testList.size(), iterator.nextIndex());
        }

        /* iterator.set() tests */
        @Test (expected = IllegalStateException.class)
        public void testSetWithoutNextOrPreviousCalled() {
            MyList testList = getList();
            MyListIterator iterator = getListIterator();
            iterator.set(intNumber);
        }

        @Test
        public void testSetTheOnlyListElementWithNextCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            MyListIterator iterator = getListIterator();
            Assert.assertEquals(intNumber, iterator.next());
            iterator.set(character);
            Assert.assertEquals(character, iterator.previous());
        }

        @Test
        public void testSetTheFirstElementWithNextCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(array, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertEquals(1, iterator.nextIndex());
        }

        @Test
        public void testSetMiddleElementWithNextCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(array, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertEquals(2, iterator.nextIndex());
        }

        @Test
        public void testSetLastElementWithNextCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(array, testList.get(2));
            Assert.assertFalse(iterator.hasNext());
            Assert.assertEquals(testList.size(), iterator.nextIndex());
        }

        @Test
        public void testSetTheOnlyListElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.set(character);
            Assert.assertEquals(1, testList.size());
            Assert.assertEquals(character, iterator.next());
        }

        @Test
        public void testSetTheFirstElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(array, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertEquals(-1, iterator.previousIndex());
        }

        @Test
        public void testSetMiddleElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.previous();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(array, testList.get(1));
            Assert.assertEquals(string, testList.get(2));
            Assert.assertEquals(0, iterator.previousIndex());
        }

        @Test
        public void testSetLastElementWithPreviousCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.previous();
            iterator.set(array);
            Assert.assertEquals(3, testList.size());
            Assert.assertEquals(intNumber, testList.get(0));
            Assert.assertEquals(character, testList.get(1));
            Assert.assertEquals(array, testList.get(2));
            Assert.assertEquals(2, iterator.nextIndex());
        }

        @Test (expected = IllegalStateException.class)
        public void testSetAfterNextAndRemoveCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.remove();
            iterator.set(array);
        }

        @Test (expected = IllegalStateException.class)
        public void testSetAfterNextAndAddCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.add(array);
            iterator.set(array);
        }

        @Test (expected = IllegalStateException.class)
        public void testSetAfterPreviousAndRemoveCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.remove();
            iterator.set(array);
        }

        @Test (expected = IllegalStateException.class)
        public void testSetAfterPreviousAndAddCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.add(array);
            iterator.set(array);
        }

        @Test (expected = IllegalStateException.class)
        public void testRemoveAfterNextAndAddCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.add(array);
            iterator.remove();
        }

        @Test (expected = IllegalStateException.class)
        public void testRemoveAfterPreviousAndAddCalled() {
            MyList testList = getList();
            testList.add(intNumber);
            testList.add(character);
            testList.add(string);
            MyListIterator iterator = getListIterator();
            iterator.next();
            iterator.previous();
            iterator.add(array);
            iterator.remove();
        }
}


