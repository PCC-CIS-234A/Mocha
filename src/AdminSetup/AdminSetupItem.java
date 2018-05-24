package AdminSetup;

import SharedLogic.Item;

/**
 * @author Rebecca Kennedy
 * @version 5/23/2018.
 * Description: This class represents an item from the database table ITEM.
 */
public class AdminSetupItem extends Item {
    private TableAction act;

    /**
     * An enum who's value is whether or not this item will be kept
     * in the database, inserted into the database, or deleted from
     * the database.
     */
    public enum TableAction {KEEP, INS, DEL;}

    public AdminSetupItem(int itemID, int testID, String name) {
        super(itemID, testID, name);
    }

    public AdminSetupItem(int testID, String name) {
        super(testID, name);
    }

    /**
     * Gets the TableAction
     */
    public TableAction getTableAction() {
        return act;
    }

    /**
     * Sets the TableAction
     */
    public void setTableAction(TableAction action) {
        act = action;
    }
}
