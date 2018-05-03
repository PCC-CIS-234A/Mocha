package ResultReporting;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/1/2018
 * Description: This is something we asked Liz to do, but because she had trouble with GitHub, I drew up my own blueprint
 */
public class Database {
    private static final String DB_NAME = "234a_Mocha";
    private static final String DB_URL = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/" + DB_NAME;
    private static final String USERNAME = "234a_Mocha";
    private static final String PASSWORD = "@#$Mocha";

    private static final String GET_USER = "SELECT * FROM USERACCOUNT";
    private static final String GET_TESTS = "SELECT * FROM TESTSESSION WHERE UserID = ";
    private static final String GET_RESULTS = "SELECT * FROM RESULT WHERE SessionID = ";

    public Database() {
        ArrayList<User> users = getUsers();

        users.forEach((System.out::println));
    }

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
    private ResultSet execute(String sqlString) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sqlString);
            ResultSet resultSet = stmt.executeQuery();

            return resultSet;
        }
        catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet rs = execute(GET_USER);

            while (rs.next()) {
                User user = new User(rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Role"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    private ArrayList<Test> getTests(User user) {
        ArrayList<Test> tests = new ArrayList<>();

        try {
            ResultSet rs = execute(GET_USER + user.getUserID());

            while(rs.next()) {
                int sessionID = rs.getInt("SessionID");
                Date date = rs.getDate("TestDate");

                ArrayList<Item> items = getItems(sessionID);

                tests.add(new Test(items, sessionID, date));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tests;
    }

    private ArrayList<Item> getItems(int sessionID) {
        ArrayList<Item> items = new ArrayList<>();

        try {
            ResultSet rs = execute(GET_RESULTS + sessionID);

            while(rs.next()) {
                int itemOne = rs.getInt("Item1");
                int itemTwo = rs.getInt("Item2");
                int resultCode = rs.getInt("ResultCode");
                boolean itemExists = false;

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(0).getItemID() == itemOne) {
                        itemExists = true;
                        break;
                    }
                }

//                if (!itemExists) {
//                    items.add(new Item(itemOne, ))
//                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }
}
