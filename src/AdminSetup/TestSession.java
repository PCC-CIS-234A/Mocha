package AdminSetup;

import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/7/2018.
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
