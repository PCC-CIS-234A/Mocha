package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/2/2018.
 */
public class Test {
    private int myTestID;
    private String myName;

    Test (int testID, String name) {
        myTestID = testID;
        myName = name;
    }

    public int getTestID() {
        return myTestID;
    }

    public String getName() {
        return myName;
    }

    public void setTestID(int testID) {
        myTestID = testID;
    }

    public void setName(String name) {
        myName = name;
    }

    public static ArrayList<Test> getTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTests();
    }

    public static ArrayList<Test> getTestWithName(String name) {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTestWithName(name);
    }
}
