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

    public int getTestID() {
        return myTestID;
    }

    public void setTestID(int testID) {
        myTestID = testID;
    }

    public static ArrayList<TestSession> getTakenTests() {
        AdminSetupDB db = new AdminSetupDB();
        return db.getTakenTests();
    }
}
