package Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to interact with the database
 * @author Liz Goltz with input from team Mocha & Rebecca Kennedy (executeItemsBatch() and executeAnUpdate())
 * @version 4/4/2018
 */
public class Database {
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
     * Executes an update to the database
     *
     * Added by Rebecca Kennedy on 5/24/18
     *
     * @param sqlString
     * @return
     */
    public boolean executeAnUpdate(String sqlString) {
        try {
            connect();
            Statement statement = mConnection.createStatement();
            statement.executeUpdate(sqlString);

            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Executes a batch of items
     *
     * Added by Rebecca Kennedy on 5/23/18
     *
     * @param sqlString query to the database
     * @param items arraylist of items
     * @return ResultSet object
     */
    public boolean executeItemsBatch(String sqlString, ArrayList<AdminSetup.AdminSetupItem> items) {
        try {
            connect();
            PreparedStatement statement = mConnection.prepareStatement(sqlString);

            for(AdminSetup.AdminSetupItem item: items) {
                statement.setInt(1, item.getMyTestID());
                statement.setString(2, item.getMyName());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
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
}
