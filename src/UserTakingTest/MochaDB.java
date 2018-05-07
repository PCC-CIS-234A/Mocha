package UserTakingTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to communicate with Mocha Database
 * @author Liz Goltz
 * @version 4/2/2018
 */
public class MochaDB {
    //access database
    private static final String MOCHA_DB = "234a_Mocha";
    private static final String SERVER = "cisdbss.pcc.edu";
    private static final String USERNAME = "234a_Mocha"; //?is this the user taking test?
    private static final String PASSWORD = "@#$Mocha";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"
            + SERVER + "/" + MOCHA_DB + ";user=" + USERNAME + ";password=" + PASSWORD;
    private Connection mConnection = null;

    int sessionID = 0; //initialized and set to 0 so program would run, not sure how to get or set sessionID

    /*database queries -not sure if I need these
    private static final String ITEMS = "SELECT Name FROM ITEM WHERE CollectionID = ?";//read items into ArrayList
    private static final String SESSION_ID = "SELECT TOP SessionID FROM ";
    private static String input;

    */

    private void connect() {
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
     * Read in a list of items from a specific collection
     * @return list of items
     */
    public ArrayList<String> readItems(int collectionID)
    {
        ArrayList<String> items = new ArrayList<>();
        connect();
        String query = "SELECT ITEM.Name from ITEM WHERE TestID = ?";
        try {

            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, collectionID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("Name"));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update/save item pairing and test question result in the database
     * @param item1 string name of item1
     * @param item2 string name of item2
     * @param winCode integer win code (0=tie 1=item1 2=item2)
     */

    public void insertItem(int sessionID, String item1, String item2, int winCode) {
        connect();
        String query = "INSERT INTO RESULT (SessionID, Item1, Item2, ResultCode) VALUES (" + sessionID + ", " + item1 + ", " + item2 + ", " + winCode + ")";

        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, sessionID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update/save ArrayList of test results
     * @param testResults ArrayList of paired item names and result
     */

    public void insertResults (ArrayList<UserTakingTest.ItemPair> testResults) {
        connect();
        //for each ItemPair result in the arraylist
        for (ItemPair result : testResults) {
            String item1 = result.getItem1(); //?are these redundant, can I put them directly in String query assignment statment?
            String item2 = result.getItem2();
            int winItem = result.getWinItem();

            insertItem(sessionID, item1, item2, winItem);
        }
    }

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

    @Override
    protected void finalize() { close(); }
}
