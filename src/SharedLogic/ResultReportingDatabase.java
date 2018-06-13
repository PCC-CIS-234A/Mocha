package SharedLogic;

import java.util.ArrayList;


/**
 * @author Bobby Puckett
 * @version 5/29/2018
 *
 * Description: Makes all data from the database available
 */
public class ResultReportingDatabase {
    private ArrayList<Item> items;
    private ArrayList<Result> results;
    private ArrayList<Test> tests;
    private ArrayList<TestSession> testSessions;
    private ArrayList<UserAccount> users;

    public ResultReportingDatabase() {
        retrieveAll();
    }

    /**
     * initializes all fields
     */
    private void retrieveAll() {
        items = Item.retrieveAllItems();
        results = Result.retrieveAllResults(items);
        tests = Test.retrieveAllTests(items);
        users = UserAccount.retrieveAllUsers();
        testSessions = TestSession.retrieveAllTestSessions(users, tests);
    }

    /**
     * returns the items
     * @return the items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * returns the results
     * @return the results
     */
    public ArrayList<Result> getResults() {
        return results;
    }

    /**
     * returns the tests
     * @return the tests
     */
    public ArrayList<Test> getTests() {
        return tests;
    }

    /**
     * returns the test sessions
     * @return the test sessions
     */
    public ArrayList<TestSession> getTestSessions() {
        return testSessions;
    }

    /**
     * returns the users
     * @return the users
     */
    public ArrayList<UserAccount> getUsers() {
        return users;
    }

    /**
     * Prints every object in the instance of the data class.
     * Used for debugging.
     */
    private void printObjects() {
        items.forEach(System.out::println);
        results.forEach(System.out::println);
        tests.forEach(System.out::println);
        users.forEach(System.out::println);
        testSessions.forEach(System.out::println);
    }
}
