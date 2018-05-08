package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/2/2018.
 */
public class Test {
    private int myTestID;
    private String myName;
    private boolean editable = true;

  //  Test (int testID, String name) {
    Test (String name) {
 //       myTestID = testID;
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

    /*
    public static ArrayList<Test> getTakenTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTakenTests();
    }*/

    public static ArrayList<Test> getTestWithName(String name) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestWithName(name);
    }

    public static int getTestID(String testName) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestID(testName);
    }
}
