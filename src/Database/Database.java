package Database;

import UserTakingTest.Item;
import UserTakingTest.ItemPair;
import UserTakingTest.TestSession;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to interact with the database
 * @author Liz Goltz with input from team Mocha
 * @version 4/26/2018
 *
 * modifications:
 * - added UserTakingTest database methods
 */
public class Database {
    //connection fields
    private static final String MOCHA_DB = "234a_Mocha";
    private static final String SERVER = "cisdbss.pcc.edu";
    private static final String USERNAME = "234a_Mocha"; //?is this the user taking test?
    private static final String PASSWORD = "@#$Mocha";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://" + SERVER + "/" + MOCHA_DB + ";user=" + USERNAME + ";password=" + PASSWORD;
    public Connection mConnection = null;

    /**
     * Connects to the database
     */
    public void connect() {
        if (mConnection != null)
            return;
        try {
            mConnection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected to database.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a query
     * @param sqlString query to the database
     * @return ResultSet object
     */
    public ResultSet execute(String sqlString) {
        try {
            connect();
            PreparedStatement statement = mConnection.prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();
            return rs;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Closes connection to the database
     */
    public void close() {
        if (mConnection != null) {
            System.out.println("Closing database connection.");
            try {
                mConnection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read in a list of items from a specific collection
     *
     * @return list of items
     */
    public ArrayList<Item> readItems(int collectionID) {
        ArrayList<Item> items = new ArrayList<>();
        connect();
        String query = "SELECT ItemID, TestID, Name from ITEM WHERE TestID = ?";
        try {

            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, collectionID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt("ItemID"), rs.getInt("TestID"), rs.getString("Name"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // close();
        return null;
    }

    /**
     * Insert a new row to the TESTSESSION table & assign unique SessionID
     * @param testID
     * @return TestSession
     */
    public TestSession assignSessionID(int testID, int userID) {
        connect();
        // String query = "INSERT INTO TESTSESSION (TestID, UserID) VALUES (?, ?);";
        String query = "INSERT INTO TESTSESSION (TestID, UserID, TestDate) VALUES (?, ?, GETDATE()); SELECT SCOPE_IDENTITY() AS SessionID;";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, testID);
            stmt.setInt(2, userID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new TestSession(rs.getInt("SessionID"), testID, userID, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  close();
        return null;
    }

    /**
     * Update/save item pairing and test question result in the database
     *
     * @param item1ID ID of item1
     * @param item2ID ID of item2
     * @param winCode integer win code (0=tie 1=item1 2=item2)
     */
    public void insertResult(int sessionID, int item1ID, int item2ID, int winCode) {
        connect();
        String query = "INSERT INTO RESULT (SessionID, Item1, Item2, ResultCode) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, sessionID);
            stmt.setInt(2, item1ID);
            stmt.setInt(3, item2ID);
            stmt.setInt(4, winCode);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  close();
    }

    /**
     * Update/save ArrayList of test results
     *
     * @param testResults ArrayList of paired item names and result
     */

    public void saveResults(int sessionID, ArrayList<ItemPair> testResults) {
        connect();
        //for each ItemPair result in the arraylist
        for (ItemPair result : testResults) {

            insertResult(
                    sessionID,
                    result.getItem1().getItemID(),
                    result.getItem2().getItemID(),
                    result.getWinItem()
            );
        }
      //  close();
    }

    @Override
    protected void finalize() {
        close();
    }
}
