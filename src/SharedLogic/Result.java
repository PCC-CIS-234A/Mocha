package SharedLogic;

import Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Result {
    private int mySessionID;
    private int myResultID;
    private Item myItemOne;
    private Item myItemTwo;
    private int myResultCode;

    // QUERY FIELDS
    private static final String GET_RESULTS_ON_TEST_SESSION = "SELECT * FROM RESULT WHERE SessionID = ";
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

                Result result = new Result(newItemOneID, newItemTwoID, newResultCode, newResultID, newSessionID);

                results.add(result);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return results;
    }

    /**
     * Note: this method was migrated from Liz's code. It used to be called insertItem
     *
     * Update/save item pairing and test question result in the database
     * @param item1 string name of item1
     * @param item2 string name of item2
     * @param winCode integer win code (0=tie 1=item1 2=item2)
     */
    public static void insertResult(int SessionID, String item1, String item2, int winCode) {
        Database database = new Database();

        String insertQuery = INSERT_RESULT + SessionID + ", " + item1 + ", " + item2 + ", " + winCode + ")";

        database.execute(insertQuery);
    }
    //DATABASE METHODS END

    // GETTERS / SETTERS
    public Item getWinner() {
        if (myResultCode == 1) {
            return myItemOne;
        }
        else if (myResultCode == 2) {
            return myItemTwo;
        }
        else {
            return null;
        }
    }

    public Item getLoser() {
        if (myResultCode == 1) {
            return myItemTwo;
        }
        else if (myResultCode == 2) {
            return myItemOne;
        }
        else {
            return null;
        }
    }

    public ArrayList<Item> getTies() {
        ArrayList<Item> ties = new ArrayList<>();

        if (myResultCode ==0) {
            ties.add(myItemOne);
            ties.add(myItemTwo);
        }

        return ties;
    }

    public int getMySessionID() {
        return mySessionID;
    }

    public int getMyResultCode() {
        return myResultCode;
    }

    public int getMyResultID() {
        return myResultID;
    }

    public Item getMyItemOne() {
        return myItemOne;
    }

    public Item getMyItemTwo() {
        return myItemTwo;
    }

    public void setMySessionID(int mySessionID) {
        this.mySessionID = mySessionID;
    }

    public void setMyItemOne(Item myItemOne) {
        this.myItemOne = myItemOne;
    }

    public void setMyItemTwo(Item myItemTwo) {
        this.myItemTwo = myItemTwo;
    }

    public void setMyResultCode(int myResultCode) {
        this.myResultCode = myResultCode;
    }

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
