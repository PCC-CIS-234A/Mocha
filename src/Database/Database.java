package Database;

import java.sql.*;

/**
 * @author Bobby Puckett
 * @version 5/3/2018
 * Description: This is an example class for the database communication class. Liz will replace this with her own
 */
public class Database {
    private static final String DB_NAME = "234a_Mocha";
    private static final String DB_URL = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/" + DB_NAME;
    private static final String USERNAME = "234a_Mocha";
    private static final String PASSWORD = "@#$Mocha";

    /**
     * Create and return a connection to the database
     * @return connection to the database
     */
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        return connection;
    }

    /**
     * executes a SQL query and returns the ResultSet or null if the code failed.
     * @param sqlString the query to be executed
     * @return either a ResultSet from the executed query or null
     */
    public ResultSet execute(String sqlString) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sqlString);
            ResultSet resultSet = stmt.executeQuery();

            return resultSet;
        }
        catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();

            return null;
        }
    }
}
