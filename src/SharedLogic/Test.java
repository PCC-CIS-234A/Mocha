package SharedLogic;

import Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 *
 * Description: Retrieves, stores, and manipulates a test from the Test table in the database
 */
public class Test {
    private int myTestID;
    private ArrayList<Item> myItems;
    private String myName;

    // QUERY FIELDS
    private static final String INSERT_TEST = "INSERT INTO TEST (Name) VALUES('";
    private static final String GET_ALL_TESTS = "SELECT * FROM TEST";
    private static final String GET_TESTS_FROM_NAME = "SELECT * FROM TEST WHERE Name = ";
    private static final String GET_TEST_FROM_ID = "SELECT * FROM TEST WHERE TestID = ";
    // QUERY FIELDS END


    // CONSTRUCTORS
    public Test(ArrayList<Item> items, String name) {
        this.myItems = items;
        this.myName = name;
    }
    public Test(ArrayList<Item> items, String name, int testID) {
        this.myItems = items;
        this.myName = name;
        this.myTestID = testID;
    }
    // CONSTRUCTORS END


    // DATABASE METHODS

    /**
     * Note: this was migrated from Rebecca's(?) code. It used to be called "addNewTest()"
     * @param name
     * @param items
     */
    public void insertTest(String name, ArrayList<Item> items) {
        Database database = new Database();

        //insert test
        String query = INSERT_TEST + name + "');";
        database.execute(query);

        //insert myItems
        items.forEach(Item::insertItem);
    }

    /**
     * Note: this method was migrated from Rebecca's(?) code. It used to be called "getTests()"
     * @return
     */
    public ArrayList<Test> retrieveAllTests() {
        ArrayList<Test> tests = new ArrayList<>();

        Database database = new Database();

        try {
            ResultSet testsResultSet = database.execute(GET_ALL_TESTS);

            while (testsResultSet.next()) {
                int testID = testsResultSet.getInt("TestID");
                ArrayList<Item> items = Item.retrieveItemsOnTest(testID);
                String name = testsResultSet.getString("Name");

                Test test = new Test(items, name, testID);

                tests.add(test);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    /**
     * Note: this was migrated from Rebecca's code. It should be migrated to an inherited class. Please don't use
     *          the method unless you're Rebecca.
     * @param name
     * @return
     */
    public ArrayList<Test> getTestWithName(String name) {
        ArrayList<Test> tests = new ArrayList<>();

        Database database = new Database();

        try {
            ResultSet testsResultSet = database.execute(GET_TESTS_FROM_NAME + name);

            while (testsResultSet.next()) {
                int testID = testsResultSet.getInt("TestID");
                ArrayList<Item> items = Item.retrieveItemsOnTest(testID);

                Test test = new Test(items, name, testID);

                tests.add(test);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public static Test retrieveTestOnName(String name) {
        Database database = new Database();

        try {
            ResultSet testsResultSet = database.execute(GET_TESTS_FROM_NAME + name);

            if (testsResultSet.next()) {
                int testID = testsResultSet.getInt("TestID");
                ArrayList<Item> items = Item.retrieveItemsOnTest(testID);

                Test test = new Test(items, name, testID);

                return test;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Test retrieveTestOnID(int id) {
        Database database = new Database();

        try {
            ResultSet testsResultSet = database.execute(GET_TEST_FROM_ID + id);

            if (testsResultSet.next()) {
                String name = testsResultSet.getString("Name");
                ArrayList<Item> items = Item.retrieveItemsOnTest(id);

                Test test = new Test(items, name, id);

                return test;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    // DATABASE METHODS END


    // GETTERS / SETTERS
    public ArrayList<Item> getMyItems() {
        return myItems;
    }

    public void setMyItems(ArrayList<Item> myItems) {
        this.myItems = myItems;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public int getMyTestID() {
        return myTestID;
    }
    // GETTERS / SETTERS END
}

/*
 * NOTES:
 *
 * Rebecca - Should getTestWithName return and Array or a Test?
 *
 */