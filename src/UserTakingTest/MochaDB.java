package UserTakingTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to communicate with Mocha Database
 * @author Liz Goltz
 * @version
 */
public class MochaDB {
    //access database
    private static final String MOCHA_DB = "234a_Mocha";
    private static final String SERVER = "cisdbss.pcc.edu";
    private static final String USERNAME = "234a_Mocha"; //?is this the user taking test?
    private static final String PASSWORD = "@#$Mocha";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"
            + SERVER + ";user=" + USERNAME + ";password=" + PASSWORD;
    private Connection mConnection = null;

    //database queries
    private static final String ITEMS = "SELECT Name FROM ITEM WHERE CollectionID = ?";//read items into ArrayList
    private static final String SESSION_ID = "SELECT TOP SessionID FROM ";
    private static String input;

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

 /*   public ArrayList<Item> readItems(int collectionID)
   {
        ArrayList<Item> items = new ArrayList<>();
        connect();
        String query = "SELECT * from Item i RIGHT OUTER JOIN ON ItemCollection ic WHERE i.ItemID = ic.ItemID AND ic.ItemCollectionID = ?";
        try (
                PreparedStatement stmt = mConnection.prepareStatement(query);
                stmt.setInt(1, collectionID);
                ResultSet rs = stmt.executeQuery();
            ) {
            while (rs.next()) {
                Item i = new Item(
                    rs.getInt("ItemID"),
                    rs.getString("Name"));
                 items.add(i);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/


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
