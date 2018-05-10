package AdminSetup;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/1/2018.
 * Description: This is the database class for the AdminSetup Package. It connects to
 * the database and both queries it and makes changes to it.
 */
public class AdminSetupDB {
    private static final String SERVER = "cisdbss.pcc.edu";
    private static final String DATABASE = "234a_Mocha";
    private static final String USERNAME = "234a_Mocha";
    private static final String PASSWORD = "@#$Mocha";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"
            + SERVER + "/" + DATABASE + ";user=" + USERNAME + ";password=" + PASSWORD;
    private Connection myConn = null;

    private void connect() {
        if (myConn != null) {
            return;
        }
        try {
            myConn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected to 234a_Mocha database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<>();

        connect();
        String query = "SELECT *\n" +
                "FROM TEST\n";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Test t = new Test(rs.getString("Name"));
                tests.add(t);
                t.setTestID(rs.getInt("TestID"));
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<TestSession> getTakenTests() {
        ArrayList<TestSession> tests = new ArrayList<>();

        connect();
        String query = "SELECT TESTSESSION.TestID\n" +
                "FROM TESTSESSION;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                TestSession t = new TestSession(rs.getInt("TestID"));
                tests.add(t);
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Item> getTestItems(int testID) {
        ArrayList<Item> items = new ArrayList<>();

        connect();
        String query = "SELECT *\n" +
                       "FROM ITEM\n" +
                       "WHERE ITEM.TestID = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            stmt.setInt(1, testID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Item i = new Item(rs.getInt("TestID"), rs.getString("Name"));
                items.add(i);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Test> getTestWithName(String name) {
        ArrayList<Test> tests = new ArrayList<>();

        connect();
        String query = "SELECT *\n" +
                "FROM TEST\n" +
                "WHERE TEST.Name = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Test t = new Test(rs.getString("Name"));
                tests.add(t);
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteItems(ArrayList<Item> items) {
        connect();
        String query = "DELETE FROM ITEM\n" +
                "WHERE ITEM.TestID = ?\n" +
                "AND ITEM.Name = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            for(Item item: items) {
                stmt.setInt(1, item.getTestID());
                stmt.setString(2, item.getName());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertItems(ArrayList<Item> items) {
        connect();
        String query = "INSERT INTO ITEM (TestID, Name)\n" +
                "VALUES (?, ?);";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            for(Item item: items) {
                stmt.setInt(1, item.getTestID());
                stmt.setString(2, item.getName());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTestID(String name) {
        int id;
        connect();
        String query = "SELECT TestID\n" +
                "FROM TEST\n" +
                "WHERE TEST.Name = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                id = rs.getInt("TestID");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean insertTest(Test test) {
        connect();
        String query = "INSERT INTO TEST (Name)\n" +
                       "VALUES ('" + test.getName() + "');";
        try {
            Statement stmt = myConn.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        if(myConn != null) {
            System.out.println("Closing the 234a_Mocha database connection.");
        }
        try {
            myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() {
        close();
    }
}
