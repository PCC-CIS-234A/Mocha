package SharedLogic;

import Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 * <p>
 * Description: Retrieves, stores, and manipulates a result from the Result table in the database
 */
public class Result {
    private int mySessionID;
    private int myResultID;
    private Item myItemOne;
    private Item myItemTwo;
    private int myResultCode;

    // QUERY FIELDS
    private static final String GET_RESULTS_ON_TEST_SESSION = "SELECT * FROM RESULT WHERE SessionID = ";
    private static final String GET_ALL_RESULTS = "SELECT * FROM RESULT";
    private static final String INSERT_RESULT = "INSERT INTO RESULT (SessionID, Item1, Item2, ResultCode) VALUES (?";
    // QUERY FIELDS END

    // CONSTRUCTORS
    public Result(Item itemOne, Item itemTwo, int resultCode, int resultID, int sessionID) {
        this.myItemOne = itemOne;
        this.myItemTwo = itemTwo;
        this.myResultCode = resultCode;

        this.myResultID = resultID;
        this.mySessionID = sessionID;
    }

    public Result(String itemOne, String itemTwo, int sessionID) {
        Item one = Item.retrieveItemOnName(itemOne);
        Item two = Item.retrieveItemOnName(itemTwo);
        this.mySessionID = sessionID;
    }

    public Result(int itemOneID, int itemTwoID, int resultCode, int resultID, int sessionID) {
        this.myItemOne = Item.retrieveItemOnID(itemOneID);
        this.myItemTwo = Item.retrieveItemOnID(itemTwoID);
        this.myResultCode = resultCode;

        this.myResultID = resultID;
        this.mySessionID = sessionID;
    }
    // CONSTRUCTORS END

    // DATABASE METHODS

    /**
     * gets a list of Results on a test session from the database
     * @param sessionID database will be queried based on sessionID
     * @return the ArrayList of Results on a test session
     */
    public static ArrayList<Result> retrieveTestSessionResults(int sessionID) {
        Database database = new Database();

        ArrayList<Result> results = new ArrayList<>();

        ResultSet resultResultSet = database.execute(GET_RESULTS_ON_TEST_SESSION + String.valueOf(sessionID));
        try {
            while (resultResultSet.next()) {
                int newItemOneID = resultResultSet.getInt("Item1");
                int newItemTwoID = resultResultSet.getInt("Item2");
                int newResultCode = resultResultSet.getInt("ResultCode");
                int newResultID = resultResultSet.getInt("ResultID");
                int newSessionID = resultResultSet.getInt("SessionID");

                ArrayList<Integer> itemIDs = new ArrayList<>();
                itemIDs.add(newItemOneID);
                itemIDs.add(newItemTwoID);
                ArrayList<Item> items = Item.retrieveMultipleItemsOnID(itemIDs);
                Item itemOne;
                Item itemTwo;

                assert items != null;
                if (items.size() == 2) {
                    if (items.get(0).getMyItemID() == newItemOneID) {
                        itemOne = items.get(0);
                        itemTwo = items.get(1);
                    } else {
                        itemOne = items.get(1);
                        itemTwo = items.get(0);
                    }

                    Result result = new Result(itemOne, itemTwo, newResultCode, newResultID, newSessionID);

                    results.add(result);
                } else {
                    System.out.println("Result.retrieveTestSessionResults(): ID was invalid");
                }
            }

        } catch (
                SQLException e)

        {
            System.out.println(e.getMessage());
        }

        return results;
    }

    /**
     * queries the database for all Results
     * @return all Results
     */
    public static ArrayList<Result> retrieveAllResults() {
        Database database = new Database();

        ArrayList<Result> results = new ArrayList<>();


        ResultSet resultResultSet = database.execute(GET_ALL_RESULTS);

        try {
            ArrayList<Item> allItems = Item.retrieveAllItems();

            while (resultResultSet.next()) {
                int newItemOneID = resultResultSet.getInt("Item1");
                int newItemTwoID = resultResultSet.getInt("Item2");
                int newResultCode = resultResultSet.getInt("ResultCode");
                int newResultID = resultResultSet.getInt("ResultID");
                int newSessionID = resultResultSet.getInt("SessionID");

                Item itemOne = null;
                Item itemTwo = null;

                for(Item item : allItems) {
                    if (item.getMyItemID() == newItemOneID) {
                        itemOne = item;
                    } else if (item.getMyItemID() == newItemTwoID) {
                        itemTwo = item;
                    }
                }

                if (itemOne != null && itemTwo != null) {
                    Result result = new Result(itemOne, itemTwo, newResultCode, newResultID, newSessionID);
                }
                else {
                    System.out.println("Result: Items could not be initialized");
                }
            }

        } catch (
                SQLException e)

        {
            System.out.println(e.getMessage());
        }

        return results;
    }
    public static ArrayList<Result> retrieveAllResults(ArrayList<Item> allItems) {
        Database database = new Database();

        ArrayList<Result> results = new ArrayList<>();


        ResultSet resultResultSet = database.execute(GET_ALL_RESULTS);

        try {
//            ArrayList<Item> allItems = Item.retrieveAllItems();

            while (resultResultSet.next()) {
                int newItemOneID = resultResultSet.getInt("Item1");
                int newItemTwoID = resultResultSet.getInt("Item2");
                int newResultCode = resultResultSet.getInt("ResultCode");
                int newResultID = resultResultSet.getInt("ResultID");
                int newSessionID = resultResultSet.getInt("SessionID");

                Item itemOne = null;
                Item itemTwo = null;

                for(Item item : allItems) {
                    if (item.getMyItemID() == newItemOneID) {
                        itemOne = item;
                    } else if (item.getMyItemID() == newItemTwoID) {
                        itemTwo = item;
                    }
                }

                if (itemOne != null && itemTwo != null) {
                    Result result = new Result(itemOne, itemTwo, newResultCode, newResultID, newSessionID);
                    results.add(result);
                }
                else {
                    System.out.println("Result: Items could not be initialized");
                }
            }

        } catch (
                SQLException e)

        {
            System.out.println(e.getMessage());
        }

        return results;
    }

    /**
     * Note: this method was migrated from Liz's code. It used to be called insertItem
     * <p>
     * Update/save item pairing and test question result in the database
     *
     * @param item1   string name of item1
     * @param item2   string name of item2
     * @param winCode integer win code (0=tie 1=item1 2=item2)
     */
    public static void insertResult(int SessionID, String item1, String item2, int winCode) {
        Database database = new Database();

        String insertQuery = INSERT_RESULT + SessionID + ", " + item1 + ", " + item2 + ", " + winCode + ")";

        database.execute(insertQuery);
    }
    //DATABASE METHODS END

    // GETTERS / SETTERS

    /**
     * gets the Item which won
     * @return the Winner Item
     */
    public Item getWinner() {
        if (myResultCode == 1) {
            return myItemOne;
        } else if (myResultCode == 2) {
            return myItemTwo;
        } else {
            return null;
        }
    }

    /**
     * gets the loser Item
     * @return the loser Item
     */
    public Item getLoser() {
        if (myResultCode == 1) {
            return myItemTwo;
        } else if (myResultCode == 2) {
            return myItemOne;
        } else {
            return null;
        }
    }

    /**
     * returns a list of both items if the items were tied or a list of size 0
     * @return an ArrayList of tied items
     */
    public ArrayList<Item> getTies() {
        ArrayList<Item> ties = new ArrayList<>();

        if (myResultCode == 0) {
            ties.add(myItemOne);
            ties.add(myItemTwo);
        }

        return ties;
    }

    /**
     * gets mySessionID
     * @return mySessionID
     */
    public int getMySessionID() {
        return mySessionID;
    }

    /**
     * gets myResultCode
     * @return myResultCode
     */
    public int getMyResultCode() {
        return myResultCode;
    }

    /**
     * gets myResultID
     * @return myResultID
     */
    public int getMyResultID() {
        return myResultID;
    }

    /**
     * gets myItemOne
     * @return myItemOne
     */
    public Item getMyItemOne() {
        return myItemOne;
    }

    /**
     * gets myItemTwo
     * @return myItemTwo
     */
    public Item getMyItemTwo() {
        return myItemTwo;
    }

    /**
     * gets mySessionID
     * @return mySessionID
     */
    public void setMySessionID(int mySessionID) {
        this.mySessionID = mySessionID;
    }

    /**
     * sets myItemOne
     * @param myItemOne the new ItemOne
     */
    public void setMyItemOne(Item myItemOne) {
        this.myItemOne = myItemOne;
    }

    /**
     * sets myItemTwo
     * @param myItemTwo the new ItemTwo
     */
    public void setMyItemTwo(Item myItemTwo) {
        this.myItemTwo = myItemTwo;
    }

    /**
     * sets the result code
     * @param myResultCode the new result code
     */
    public void setMyResultCode(int myResultCode) {
        this.myResultCode = myResultCode;
    }

    /**
     * sets the result id
     * @param myResultID the new result id
     */
    public void setMyResultID(int myResultID) {
        this.myResultID = myResultID;
    }
    //GETTERS / SETTERS END
}

/*
 * NOTES:
 *
 * Liz - Result class replaces your ItemPair class.
 *
 */
