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

    public enum TableAction {KEEP, INS, DEL;}

  Item (int testID, String name) {
        myTestID = testID;
        myName = name;
    }

    public int getTestID() {
        return myTestID;
    }

    public String getName() {
        return myName;
    }

    public TableAction getTableAction() {
        return act;
    }

    public void setTestID(int testID) {
        myTestID = testID;
    }

    public void setName(String name) {
        myName = name;
    }

    public void setTableAction(TableAction action) {
        act = action;
    }

    public static ArrayList<Item> getTestItems(int testID) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestItems(testID);
    }
}
