package SharedLogic;

import Database.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 *
 * Description: Retrieves, stores, and manipulates a test session from the TestSession table in the database
 */
public class TestSession {
    private int mySessionID;
    private Test myTest;
    private UserAccount myUser;
    private Date myTestDate;
    private ArrayList<Result> myResults;
    private int myTestID;

    // QUERY FIELDS
    private static final String GET_TEST_SESSION_ON_USER = "SELECT * FROM TESTSESSION WHERE UserID = ";
    private static final String GET_ALL_TEST_SESSIONS = "SELECT * FROM TESTSESSION";
    // QUERY FIELDS END

    // CONSTRUCTORS
    public TestSession(int sessionID, Test test, UserAccount user, Date testDate) {
        this.mySessionID = sessionID;
        this.myTest = test;
        this.myUser = user;
        this.myTestDate = testDate;

        this.myResults = Result.retrieveTestSessionResults(sessionID);
    }
    public TestSession(int sessionID, Test test, UserAccount user, Date testDate, ArrayList<Result> allResults) {
        this.mySessionID = sessionID;
        this.myTest = test;
        this.myUser = user;
        this.myTestDate = testDate;
        this.myResults = new ArrayList<>();

        allResults.forEach(result -> {
            if (result.getMySessionID() == sessionID) {
                this.myResults.add(result);
            }
        });

//        this.myResults = Result.retrieveTestSessionResults(sessionID);
    }
    public TestSession(int testID) {
        this.myTestID = testID;
    }
    // CONSTRUCTORS END

    //DATABASE METHODS
    public static ArrayList<TestSession> retrieveTestSessionsOnUser(int userID) {
        ArrayList<TestSession> testSessions = new ArrayList<>();

        Database database = new Database();

        ResultSet testSessionsResultSet = database.execute(GET_TEST_SESSION_ON_USER + userID);
        try {
            while (testSessionsResultSet.next()) {
                int newSessionID = testSessionsResultSet.getInt("SessionID");
                int newTestID = testSessionsResultSet.getInt("TestID");
                Date newTestDate = testSessionsResultSet.getDate("TestDate");

                Test test = Test.retrieveTestOnID(newTestID);
                UserAccount user = UserAccount.retrieveUserOnID(userID);

                TestSession testSession = new TestSession(newSessionID, test, user, newTestDate);

                testSessions.add(testSession);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return testSessions;
    }

    public static ArrayList<TestSession> retrieveTestSessionsOnUser(UserAccount user) {
        int userID = user.getMyUserID();
        ArrayList<TestSession> testSessions = new ArrayList<>();

        Database database = new Database();

        ResultSet testSessionsResultSet = database.execute(GET_TEST_SESSION_ON_USER + userID);

        try {
            ArrayList<Result> allResults = Result.retrieveAllResults();

            while (testSessionsResultSet.next()) {
                int newSessionID = testSessionsResultSet.getInt("SessionID");
                int newTestID = testSessionsResultSet.getInt("TestID");
                Date newTestDate = testSessionsResultSet.getDate("TestDate");

                Test test = Test.retrieveTestOnID(newTestID);

                TestSession testSession = new TestSession(newSessionID, test, user, newTestDate, allResults);

                testSessions.add(testSession);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return testSessions;
    }

    public static ArrayList<TestSession> retrieveAllTestSessions(ArrayList<UserAccount> users, ArrayList<Test> tests) {
//        int userID = user.getMyUserID();
        ArrayList<TestSession> testSessions = new ArrayList<>();

        Database database = new Database();

        ResultSet testSessionsResultSet = database.execute(GET_ALL_TEST_SESSIONS);

        try {
            ArrayList<Result> allResults = Result.retrieveAllResults();

            while (testSessionsResultSet.next()) {
                int newSessionID = testSessionsResultSet.getInt("SessionID");
                int newTestID = testSessionsResultSet.getInt("TestID");
                int newUserID = testSessionsResultSet.getInt("UserID");
                Date newTestDate = testSessionsResultSet.getDate("TestDate");

                Test newTest = null;
                for(Test test : tests) {
                    if (test.getMyTestID() == newTestID) {
                        newTest = test;
                        break;
                    }
                }

                UserAccount newUser = null;
                for (UserAccount user : users) {
                    if (user.getMyUserID() == newUserID) {
                        newUser = user;
                    }
                }

                TestSession testSession = new TestSession(newSessionID, newTest, newUser, newTestDate, allResults);

                testSessions.add(testSession);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return testSessions;
    /**
     * Retrieves the taken tests from the database
     *
     * Added by Rebecca Kennedy on 2/24/18 (2:02 am)
     * @return
     */
    public static ArrayList<TestSession> retrieveTakenTests() {
        ArrayList<TestSession> ts = new ArrayList<>();

        Database database = new Database();
        String query = "SELECT TESTSESSION.TestID\n" +
                "FROM TESTSESSION;";
        try {
            ResultSet rs = database.execute(query);
            while(rs.next()) {
                TestSession t = new TestSession(rs.getInt("TestID"));
                ts.add(t);
            }
            return ts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //DATABASE METHODS END


    // GETTERS / SETTERS
    public void setMyTest(Test myTest) {
        this.myTest = myTest;
    }

    public void setMyResults(ArrayList<Result> myResults) {
        this.myResults = myResults;
    }

    public void setMySessionID(int mySessionID) {
        this.mySessionID = mySessionID;
    }

    public void setMyTestDate(Date date) {
        this.myTestDate = date;
    }

    public int getMySessionID() {
        return mySessionID;
    }

    public ArrayList<Result> getMyResults() {
        return myResults;
    }

    public Date getMyTestDate() {
        return myTestDate;
    }

    public Test getMyTest() {
        return myTest;
    }

    public UserAccount getMyUser() {
        return myUser;
    }

    public void setMyUser(UserAccount myUser) {
        this.myUser = myUser;
    }

    public int getMyTestID() {
        return myTestID;
    }
    // GETTERS / SETTERS END


    @Override
    public String toString() {
        return String.valueOf(getMySessionID());
    }
}


/*
 * NOTES:
 *
 * Liz -    below is a method from Test.java which I thought you might want somewhere, but it didn't seem to fit with this
 *          class. I've translated it for you :)
 */
//    public ArrayList<Result> makeTestQuestions (ArrayList<String> items) {
//        testQuestions = new ArrayList<Result>();
//        //for each item in the ArrayList of items, create a pairing with all subsequent items
//        for (String item : items) {
//            //declare & create a temp arraylist of subsequent item names
//            ArrayList<String> temp = new ArrayList<>();
//            temp.addAll(items.indexOf(item+1), items);
//            for (String tempItem : temp) {
//                Result question = new Result(item, tempItem, mySessionID);
//                testQuestions.add(question);
//            }
//        }
//        return testQuestions;
//    }