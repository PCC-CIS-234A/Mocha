package AdminSetup;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/1/2018.
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

    public void addNewTest(String name, ArrayList<String> items) {
        //Controller method for adding new test and items
        //checkTestName(name);
    }

    /*
    public void checkTestName(String name) {
        connect();
        String query = "SELECT *\n" +
                       "FROM TEST\n" +
                       "WHERE TEST.Name = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Customer c = new Customer(
                        rs.getString("Name"),
                        rs.getString("StreetAddress"),
                        rs.getString("StateProvince")
                );
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/

    public ArrayList<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<>();

        connect();
        String query = "SELECT *\n" +
                "FROM TEST\n";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Test t = new Test(
                //        rs.getInt("TestID"),
                        rs.getString("Name")
                );
                tests.add(t);
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Doesn't need to return an ArrayList I don't think. Should return a String which is the Test's name.
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
                Test t = new Test(
                 //       rs.getInt("TestID"),
                        rs.getString("Name")
                );
                tests.add(t);
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertItems(ArrayList<Item> items) {
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
         //   ResultSet rs = stmt.executeQuery();
            /*
            while(rs.next()) {
                Test t = new Test(
                        //       rs.getInt("TestID"),
                        rs.getString("Name")
                );
                tests.add(t);
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTestID(String name) {
        int id;

    //    System.out.println("getTestID: " + name);

        connect();
        String query = "SELECT TestID\n" +
                "FROM TEST\n" +
                "WHERE TEST.Name = ?;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            //id = rs.getInt("TestID");

            while(rs.next()) {
                id = rs.getInt("TestID");
                return id;
            }

           // return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    //    return id;
    }

    public void insertTest(Test test) {
        connect();
        String query = "INSERT INTO TEST (Name)\n" +
                       "VALUES ('" + test.getName() + "');";
        try {
         //   PreparedStatement stmt = myConn.prepareStatement(query);
            Statement stmt = myConn.createStatement();
       //     stmt.setString(1, test.getName());
       //     stmt.executeQuery();
            stmt.executeUpdate(query);
          //  ResultSet rs = stmt.executeQuery();
            /*
            while(rs.next()) {
                Test t = new Test(
                        //       rs.getInt("TestID"),
                        rs.getString("Name")
                );
                tests.add(t);
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();
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
