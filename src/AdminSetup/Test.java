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
    private boolean editable = true;

    Test (String name) {
        myName = name;
    }

    public int getTestID() {
        return myTestID;
    }

    public String getName() {
        return myName;
    }

    public boolean getEditable() {
        return editable;
    }


    public void setTestID(int testID) {
        myTestID = testID;
    }

    public void setName(String name) {
        myName = name;
    }

    public void setEditable(boolean isEditable) {
        editable = isEditable;
    }

    public static ArrayList<Test> getTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTests();
    }

    public static ArrayList<Test> getTestWithName(String name) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestWithName(name);
    }

    public static int getTestID(String testName) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestID(testName);
    }
}
