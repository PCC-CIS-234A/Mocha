package ResultReporting;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Bobby Puckett
 * @version 4/25/2018
 * Description: has data and methods relating to a test.
 */
public class Test {
    private ArrayList<Item> items;
    private String sessionID;
    private Date date;

    public Test(ArrayList<Item> items, String sessionID, Date date) {
        this.items = new ArrayList<>();
        for (Item i : items) {
            this.items.add(i);
        }
        sortItems();

        this.sessionID = sessionID;
        this.date = date;
    }

    private void sortItems() {
        //item sorting code here
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
