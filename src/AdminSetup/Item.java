package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/2/2018.
 * Description: This class represents an item from the database table ITEM.
 */
public class Item {
    private int myTestID;
    private String myName;
    private TableAction act;

    /**
     * An enum who's value is whether or not this item will be kept
     * in the database, inserted into the database, or deleted from
     * the database.
     */
    public enum TableAction {KEEP, INS, DEL;}

  Item (int testID, String name) {
        myTestID = testID;
        myName = name;
    }

    /**
     * Gets the TestID
     */
    public int getTestID() {
        return myTestID;
    }

    /**
     * Gets the name
     */
    public String getName() {
        return myName;
    }

    /**
     * Gets the TableAction
     */
    public TableAction getTableAction() {
        return act;
    }

    /**
     * Sets the TestID
     */
    public void setTestID(int testID) {
        myTestID = testID;
    }

    /**
     * Sets the name
     */
    public void setName(String name) {
        myName = name;
    }

    /**
     * Sets the TableAction
     */
    public void setTableAction(TableAction action) {
        act = action;
    }

    /**
     * Gets the test items from the database
     */
    public static ArrayList<Item> getTestItems(int testID) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestItems(testID);
    }
}
