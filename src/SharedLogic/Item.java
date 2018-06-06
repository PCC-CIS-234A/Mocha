package SharedLogic;

import Database.Database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import  java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 *
 * Description: Retrieves, stores, and manipulates an item from the Item table in the database
 *
 * Modification
 * @author: Anh Nguyen
 * @version: 5/30/2018
 *
 * Added myImage variable
 * Added constructor for ImageLibrary
 * Added getter and setter for image property
 * Modified retrieveItemOnName method
 *
 */
public class Item {
    private int myItemID;
    private int myTestID;
    private String myName;
    private BufferedImage myImage;
    // QUERY FIELDS
    private static final String GET_ALL_ITEMS = "SELECT * FROM ITEM";
    private static final String GET_ITEM_ON_ID = "SELECT * FROM ITEM WHERE ItemID = ";
    private static final String GET_ITEM_ON_NAME = "SELECT * FROM ITEM WHERE Name = ";
    private static final String GET_ITEMS_ON_TEST = "SELECT * FROM ITEM WHERE TestID = ";
    private static final String INSERT_ITEM = "INSERT INTO ITEM (TestID, Name) VALUES ('";

    // QUERY FIELDS END

    // CONSTRUCTORS
    public Item(int itemID, int testID, String name) {
        this.myItemID = itemID;
        this.myTestID = testID;
        this.myName = name;
    }
    // constructor for image library
    // Added by Anh Nguyen on 5/30/2018
    public Item(int itemID,int testID, String name, BufferedImage Image){
        this.myItemID = itemID;
        this.myTestID = testID;
        this.myName = getMyName();
        this.setMyImage(Image);
    }
    public Item(int testID, String name) {
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

    public static Item retrieveItemOnID(int itemID) {
        Database database = new Database();

        ResultSet itemResultSet = database.execute(GET_ITEM_ON_ID + itemID);
        try {
            if (itemResultSet.next()) {
                int newItemID = itemResultSet.getInt("ItemID");
                int newTestID = itemResultSet.getInt("TestID");
                String newName = itemResultSet.getString("Name");

                InputStream stream = itemResultSet.getBinaryStream("Img");
                BufferedImage newImage = null;

                try {
                    if (stream != null)
                        newImage = ImageIO.read(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(newName);
                Item item = new Item(newItemID, newTestID, newName,newImage);

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
     *
     * Modified By Anh Nguyen on 5/30/2018
     * Added image property
     */
    public static Item retrieveItemOnName(String name) {
        Database database = new Database();

        ResultSet itemResultSet = database.execute(GET_ITEM_ON_NAME +"'"+ name+"'");
        try {
            if (itemResultSet.next()) {
                int newItemID = itemResultSet.getInt("ItemID");
                int newTestID = itemResultSet.getInt("TestID");

                InputStream stream = itemResultSet.getBinaryStream("Img");
                BufferedImage newImage = null;

                try {
                    if (stream != null)
                        newImage = ImageIO.read(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Item item = new Item(newItemID, newTestID, name, newImage);

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
     * Retrieves all Items from the database with the testID and returns them in an ArrayList
     *
     * Added by Rebecca Kennedy 5/23/18
     *
     * @param testID the TestID to search for in the database
     * @return an ArrayList of Items on a Test
     */
    public static ArrayList<AdminSetup.AdminSetupItem> retrieveItemsOnTestAsAdminSetupItem(int testID) {
        ArrayList<AdminSetup.AdminSetupItem> items = new ArrayList<>();

        Database database = new Database();

        ResultSet itemsResultSet = database.execute(GET_ITEMS_ON_TEST + testID);
        try {
            while (itemsResultSet.next()) {
                int newItemID = itemsResultSet.getInt("ItemID");
                int newTestID = itemsResultSet.getInt("TestID");
                String newName = itemsResultSet.getString("Name");

                AdminSetup.AdminSetupItem item = new AdminSetup.AdminSetupItem(newItemID, newTestID, newName);

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

    /**
     * Inserts the items for a particular test.
     *
     * Moved over from Rebecca Kennedy's story on 5/23/18
     */
    public static boolean insertItems(ArrayList<AdminSetup.AdminSetupItem> items) {

        Database database = new Database();

        String query = "INSERT INTO ITEM (TestID, Name)\n" +
                "VALUES (?, ?);";

        return database.executeItemsBatch(query, items);
    }

    /**
     * Deletes particular items of a particular test.
     *
     * Migrated over from Rebecca Kennedy's story on 5/23/18
     */
    public static boolean deleteItems(ArrayList<AdminSetup.AdminSetupItem> items) {

        Database database = new Database();

        String query = "DELETE FROM ITEM\n" +
                "WHERE ITEM.TestID = ?\n" +
                "AND ITEM.Name = ?;";

        return database.executeItemsBatch(query, items);
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
     * Sets the TestID
     *
     * Migrated from Rebecca Kennedy's code on 5/23/18
     */
    public void setTestID(int testID) {
        myTestID = testID;
    }

    /**
     * Sets the name
     *
     * Migrated from Rebecca Kennedy's code on 5/23/18
     */
    public void setName(String name) {
        myName = name;
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


    /**
     * Makes Item.toString() return the Item name
     * @return the Item name
     */
    @Override
    public String toString() {
        return getMyName();
    }

    /**
     * Getter and Setter for Image property
     * Added by Anh Nguyen on 5/30/2018
     */

    public BufferedImage getMyImage() {
        return myImage;
    }

    public void setMyImage(BufferedImage myImage) {
        this.myImage = myImage;
    }
}
