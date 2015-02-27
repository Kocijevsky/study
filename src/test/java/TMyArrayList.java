import com.company.MyLinkedList;
import org.junit.Assert;
import org.junit.Test;

public class TMyArrayList {


    @Test
    public void add() {
        MyLinkedList arrayList = new MyLinkedList();

        Object o = new Object();
        arrayList.add(o);

        Assert.assertTrue(o == arrayList.get(0));
        o = new Object();

        arrayList.add(o);
        Assert.assertTrue(o == arrayList.get(1));

    }

    @Test
    public void get() {
        MyLinkedList myLinkedList = new MyLinkedList();
        Object[] array = new Object[20];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myLinkedList.add(obj);
            array[i] = obj;
        }


        Assert.assertTrue("get() first", array[0] == myLinkedList.get(0));
        Assert.assertTrue("get() from middle", array[4] == myLinkedList.get(4));
        Assert.assertTrue("get() last", array[19] == myLinkedList.get(19));

    }

    @Test
    public void sizeAndRemove() {
        MyLinkedList myLinkedList = new MyLinkedList();
        Object[] array = new Object[20];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myLinkedList.add(obj);
            array[i] = obj;
        }
        Assert.assertEquals("size of arrayList", myLinkedList.size(), 20);
        obj = myLinkedList.remove(12);
        Assert.assertTrue("remove must should return same object", obj == array[12]);
        Assert.assertEquals("size after remove", myLinkedList.size(), 19);

    }

    @Test
    public void contains() {
        MyLinkedList myLinkedList = new MyLinkedList();
        Object[] array = new Object[40];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myLinkedList.add(obj);

            array[2 * i + 1] = obj;
            array[2 * i] = new Object();
            // Содержит все, что внесли
            Assert.assertTrue(myLinkedList.contains(array[2 * i + 1]));
            // Не содержит левые
            Assert.assertFalse(myLinkedList.contains(array[2 * i]));

        }

    }


}
