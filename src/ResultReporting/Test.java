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
    private int sessionID;
    private Date date;

    public Test(ArrayList<Item> items, int sessionID, Date date) {
        this.items = new ArrayList<>();
        for (Item i : items) {
            this.items.add(i);
        }
        sortItems();

        this.sessionID = sessionID;
        this.date = date;
    }

    /**
     * sorts items by rank
     */
    private void sortItems() {
        //item sorting code here
    }

    /**
     * gets the ArrayList of item objects
     * @return the ArrayList of item objects
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}
