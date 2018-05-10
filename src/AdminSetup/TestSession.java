package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/7/2018.
 * Description: This class represents a test session from the database table TESTSESSION.
 */
public class TestSession {
    private int myTestID;
    private String myName;

    TestSession (int testID) {
        myTestID = testID;
    }

    /**
     * Gets the TestID
     */
    public int getTestID() {
        return myTestID;
    }

    /**
     * Sets the TestID
     */
    public void setTestID(int testID) {
        myTestID = testID;
    }

    /**
     * Gets the all the TestIDs from the database of all tests already taken by a user
     */
    public static ArrayList<TestSession> getTakenTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTakenTests();
    }
}
