import com.company.MyArrayList;
import com.company.MyLinkedList;
import com.company.MyList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TMyArrayList extends TMyList {

    private MyArrayList theList;

    @Override
    public void setUp() {
        theList = new MyArrayList();
    }

    @Override
    public MyList getList() {
        return theList;
    }
}