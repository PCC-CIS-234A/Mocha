package SharedLogic;

import Database.Database;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 *
 * Description: Retrieves, stores, and manipulates an item from the Item table in the database
 */
public class Item {
    private int myItemID;
    private int myTestID;
    private String myName;

    // QUERY FIELDS
    private static final String GET_ALL_ITEMS = "SELECT * FROM ITEM";
    private static final String GET_ITEM_ON_ID = "SELECT * FROM ITEM WHERE ItemID = ";
    private static final String GET_ITEM_ON_NAME = "SELECT * FROM ITEM WHERE ItemName = ";
    private static final String GET_ITEMS_ON_TEST = "SELECT * FROM ITEM WHERE TestID = ";
    private static final String INSERT_ITEM = "INSERT INTO ITEM (TestID, Name) VALUES ('";
    // QUERY FIELDS END

    // CONSTRUCTORS
    public Item(int itemID, int testID, String name) {
        this.myItemID = itemID;
        this.myTestID = testID;
        this.myName = name;
    }
    // CONSTRUCTORS END

    // DATABASE METHODS

    /**
     * returns every Item in the database
     *
     * @return an ArrayList of Items
     */
    public static ArrayList<Item> retrieveAllItems() {
        ArrayList<Item> items = new ArrayList<>();

        Database database = new Database();

        ResultSet itemsResultSet = database.execute(GET_ALL_ITEMS);
        try {
            while (itemsResultSet.next()) {
                int newItemID = itemsResultSet.getInt("ItemID");
                int newTestID = itemsResultSet.getInt("TestID");
                String newName = itemsResultSet.getString("Name");

                Item item = new Item(newItemID, newTestID, newName);

                items.add(item);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }

    /**
     * Returns one Item tied to the itemID or null if it doesn't exist
     *
     * @param itemID the itemID searched for in the database
     * @return the Item tied to the itemID
     */
    public static Item retrieveItemOnID(int itemID) {
        Database database = new Database();

        ResultSet itemResultSet = database.execute(GET_ITEM_ON_ID + itemID);
        try {
            if (itemResultSet.next()) {
                int newItemID = itemResultSet.getInt("ItemID");
                int newTestID = itemResultSet.getInt("TestID");
                String newName = itemResultSet.getString("Name");

                Item item = new Item(newItemID, newTestID, newName);

                return item;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static ArrayList<Item> retrieveMultipleItemsOnID(ArrayList<Integer> itemIDs) {
        ArrayList<Item> items = new ArrayList<>();

        Database database = new Database();

        StringBuilder query = new StringBuilder(GET_ITEM_ON_ID);
        String orClause = " OR ItemID = ";
        for (int itemID : itemIDs) {
            query.append(String.valueOf(itemID)).append(orClause);
        }
        query.delete(query.lastIndexOf(orClause), query.length());

        ResultSet itemResultSet = database.execute(query.toString());
        try {
            while (itemResultSet.next()) {
                int newItemID = itemResultSet.getInt("ItemID");
                int newTestID = itemResultSet.getInt("TestID");
                String newName = itemResultSet.getString("Name");

                Item item = new Item(newItemID, newTestID, newName);

                items.add(item);
            }
            return items;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Returns one Item tied to the name passed or null if it doesn't exist
     *
     * @param name the name to search for in the database
     * @return the Item in the database with the specified name
     */
    public static Item retrieveItemOnName(String name) {
        Database database = new Database();

        ResultSet itemResultSet = database.execute(GET_ITEM_ON_NAME + name);
        try {
            if (itemResultSet.next()) {
                int newItemID = itemResultSet.getInt("ItemID");
                int newTestID = itemResultSet.getInt("TestID");

                Item item = new Item(newItemID, newTestID, name);

                return item;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves all Items from the database with the testID and returns them in an ArrayList
     *
     * @param testID the TestID to search for in the database
     * @return an ArrayList of Items on a Test
     */
    public static ArrayList<Item> retrieveItemsOnTest(int testID) {
        ArrayList<Item> items = new ArrayList<>();

        Database database = new Database();

        ResultSet itemsResultSet = database.execute(GET_ITEMS_ON_TEST + testID);
        try {
            while (itemsResultSet.next()) {
                int newItemID = itemsResultSet.getInt("ItemID");
                int newTestID = itemsResultSet.getInt("TestID");
                String newName = itemsResultSet.getString("Name");

                Item item = new Item(newItemID, newTestID, newName);

                items.add(item);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }

    /**
     * Inserts an Item into the database
     * @param item the Item to insert
     */
    public static void insertItem(Item item) {
        Database database = new Database();

        String query = INSERT_ITEM + item.getMyTestID() + "', '" + item.getMyName() + "');";

        database.execute(query);
    }
    // DATABASE METHODS END

    // GETTERS / SETTERS
    /**
     * returns myItemID
     *
     * @return the ItemID for the Item
     */
    public int getMyItemID() {
        return myItemID;
    }

    /**
     * returns myName
     *
     * @return the Name for the Item
     */
    public String getMyName() {
        return myName;
    }

    /**
     * returns myTestID
     *
     * @return the TestID for the Item
     */
    public int getMyTestID() {
        return myTestID;
    }

    /**
     * Returns a list of Item names (migrated from Liz's Item class)
     *
     * @param testID the TestID (formerly "collectionID") to search for
     * @return the list of item names
     */
    public static ArrayList<String> readItems(int testID) {
        ArrayList<Item> items = retrieveItemsOnTest(testID);
        ArrayList<String> itemNames = new ArrayList<>();

        items.forEach(item -> itemNames.add(item.getMyName()));

        return itemNames;
    }
    // GETTERS / SETTERS END

    /**
     * Makes Item.toString() return the Item name
     * @return the Item name
     */
    @Override
    public String toString() {
        return getMyName();
    }
}

/*
 * NOTES:
 *
 * Liz -    In your MochaDB class, there's a readItems(int collectionID) method which returns a list of strings.
 *          I've included this in this class, so that you don't have to migrate over.
 *
 *          (from next day) I believe the String array from readItems is passed to a combobox or similar widget. You
 *          might consider whether you want to pass the Item object itself. Because of the overridden toString() method,
 *          the widget will be able to read the names from the Item without you doing extra work. It also helps when you
 *          need to do something with the widget. Instead of saying something like "find Item where myName is the widget
 *          myName," you can pass the currently selected Item stored in the widget. The way you'd do this is -
 *
 *              nameOfComboBox.add(Item);
 *
 *          Where "Item" is the item you want to pass. Either method is a fine way of doing things. Choose what you feel
 *          better about :)
 */