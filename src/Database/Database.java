package Database;

import java.sql.*;

/**
 * Class to interact with the database
 * @author Liz Goltz with input from team Mocha
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