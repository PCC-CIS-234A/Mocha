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
                TestSession t = new TestSession(
                                rs.getInt("TestID")
                    //    rs.getString("Name")
                );
                tests.add(t);
             //   t.setTestID(rs.getInt("TestID"));
            }
            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Item> getTestItems() {
        ArrayList<Item> items = new ArrayList<>();

        connect();
        String query = "SELECT *\n" +
                       "FROM ITEM\n" +
                       "WHERE ITEM.TestID = 1;";
        try {
            PreparedStatement stmt = myConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Item i = new Item(
                        rs.getInt("TestID"),
                        rs.getString("Name")
                );
                items.add(i);
            }
            return items;
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

    public void mergeItems(ArrayList<Item> items) {
        //Merge items
        connect();

            String query_full = "MERGE INTO ITEM AS DB_ITEM\n" +
                    "USING (SELECT ITEM.TestID, ITEM.Name FROM ITEM) AS JAVA_ITEMS (Id, N)\n" +
                    "ON DB_ITEM.TestID = JAVA_ITEMS.Id AND DB_ITEM.Name = JAVA_ITEMS.N\n" +
                    "WHEN NOT MATCHED THEN INSERT (TestID, Name) VALUES(JAVA_ITEMS.Id, JAVA_ITEMS.N)\n" +
                    "WHEN NOT MATCHED BY SOURCE THEN DELETE;";

            String query_part1 = "MERGE INTO ITEM AS DB_ITEM\n" +
                    "USING (";

            String query_values = "SELECT ITEM.TestID, ITEM.Name FROM ITEM";

            String query_part2 = ") AS JAVA_ITEMS (TestID, Name)\n" +
                    "ON DB_ITEM.TestID = JAVA_ITEMS.TestID AND DB_ITEM.Name = JAVA_ITEMS.Name\n" +
                    "WHEN NOT MATCHED THEN INSERT (TestID, Name) VALUES(JAVA_ITEMS.TestID, JAVA_ITEMS.Name)\n" +
                    "WHEN NOT MATCHED BY SOURCE THEN DELETE;";

            int lastIndex = items.size() - 1;
            for(Item item: items) {
                int currentIndex = items.indexOf(item);

                if(currentIndex == 0) {
                    query_values = "VALUES (1, '" + item.getName() + "')";
                }else {
                    query_values = query_values + "(1, '" + item.getName() + "')";
                }

                System.out.println(lastIndex);
                System.out.println(currentIndex);

                if(lastIndex != currentIndex) {
                    query_values = query_values + ", ";
                }
            }

        System.out.println(query_part1 + query_values + query_part2);




        try {
            Statement stmt = myConn.createStatement();
            stmt.executeUpdate(query_part1 + query_values + query_part2);

       //     PreparedStatement stmt = myConn.prepareStatement(query);
       //     for(Item item: items) {
       //         stmt.setInt(1, item.getTestID());
       //         stmt.setString(2, item.getName());
        //        stmt.addBatch();
          //  }
         //   stmt.executeBatch();
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
