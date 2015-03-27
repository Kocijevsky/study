import com.company.MyList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class TMyList {


    @Before
    public abstract void setUp();

    public abstract MyList getList();

    @Test
    public void add() {

        MyList myList = getList();

        Object o = new Object();
        myList.add(o);

        Assert.assertTrue(o == myList.get(0));
        o = new Object();

        myList.add(o);
        Assert.assertTrue(o == myList.get(1));

    }

    @Test
    public void get() {

        MyList myList = getList();

        Object[] array = new Object[20];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myList.add(obj);
            array[i] = obj;
        }


        Assert.assertTrue("get() first", array[0] == myList.get(0));
        Assert.assertTrue("get() from middle", array[4] == myList.get(4));
        Assert.assertTrue("get() last", array[19] == myList.get(19));

    }

    @Test
    public void sizeAndRemove() {

        MyList myList = getList();

        Object[] array = new Object[20];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myList.add(obj);
            array[i] = obj;
        }
        Assert.assertEquals("size of arrayList", myList.size(), 20);
        obj = myList.remove(12);
        Assert.assertTrue("remove must should return same object", obj == array[12]);
        Assert.assertEquals("size after remove", myList.size(), 19);

    }

    @Test
    public void contains() {

        MyList myList = getList();

        Object[] array = new Object[40];
        Object obj;

        for (int i = 0; i < 20; i++) {
            obj = new Object();
            myList.add(obj);

            array[2 * i + 1] = obj;
            array[2 * i] = new Object();
            // Содержит все, что внесли
            Assert.assertTrue(myList.contains(array[2 * i + 1]));
            // Не содержит левые
            Assert.assertFalse(myList.contains(array[2 * i]));

        }

    }


}
