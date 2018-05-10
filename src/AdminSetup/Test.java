package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/2/2018.
 * Description: This class represents a test from the database table TEST.
 */
public class Test {
    private int myTestID;
    private String myName;
    //editable variable tells whether or not this test can be edited
    private boolean editable = true;

    Test (String name) {
        myName = name;
    }

    /**
     * Gets the TestID
     */
    public int getTestID() {
        return myTestID;
    }

    /**
     * Gets the name
     */
    public String getName() {
        return myName;
    }

    /**
     * Gets the editable variable
     */
    public boolean getEditable() {
        return editable;
    }

    /**
     * Sets the TestID
     */
    public void setTestID(int testID) {
        myTestID = testID;
    }

    /**
     * Sets the name
     */
    public void setName(String name) {
        myName = name;
    }

    /**
     * Sets the editable variable
     */
    public void setEditable(boolean isEditable) {
        editable = isEditable;
    }

    /**
     * Gets all the tests from the database
     */
    public static ArrayList<Test> getTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTests();
    }

    /**
     * Gets the test with the particular name given from the database
     */
    public static ArrayList<Test> getTestWithName(String name) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestWithName(name);
    }

    /**
     * Gets the TestID associated with the particular name given from the database
     */
    public static int getTestID(String testName) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestID(testName);
    }
}
