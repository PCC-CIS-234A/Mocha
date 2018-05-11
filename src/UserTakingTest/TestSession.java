package UserTakingTest;

import java.util.Date;

/**
 * Class to define a test session
 *
 * @author Liz Goltz
 * @version 4/9/2018
 */

public class TestSession {
    private int sessionID;
    private int testID;
    private int userID;
    private Date testDate;

    public TestSession(int sessionID, int testID, int userID, Date testDate) {
        this.sessionID = sessionID;
        this.testID = testID;
        this.userID = userID;
        this.testDate = testDate;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
