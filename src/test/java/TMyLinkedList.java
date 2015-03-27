import com.company.MyArrayList;
import com.company.MyLinkedList;
import com.company.MyList;

public class TMyLinkedList extends TMyList {

    private MyLinkedList theList;

    @Override
    public void setUp() {
        theList = new MyLinkedList();
    }

    @Override
    public MyList getList() {
        return theList;
    }
}
