package UserTakingTest;

import Database.Database;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for User Taking Test Logic
 * @author Liz Goltz
 * @version 4/3/2018
 */
public class Test
{
    private int sessionID;
    private int testID;
    private int userID;
    private String userName;
    private int collectionID;
    private ArrayList<Item> mCollection;
    private ArrayList<ItemPair> testQuestions;
    private Database db;

    public Test(int userID, int collectionID) {
        db = new Database();
        this.userID = userID;
        this.collectionID = collectionID;
        testID = 1;
        mCollection = db.readItems(collectionID);
    }

    /**
     * Method to create a list of test questions of all the unique pairings from a list of items
     * @return testQuestions a list of item pairs /test questions
     */
    public ArrayList<ItemPair> makeTestQuestions () {

        testQuestions = new ArrayList<ItemPair>();
        //for each item in the ArrayList of items, create a pairing with all subsequent items

        for (int i = 0; i < mCollection.size() - 1; i++) {
            for (int j = i+1; j < mCollection.size(); j++) {
                System.out.println(mCollection.get(i).getName() + " " + mCollection.get(j).getName());
                ItemPair question = new ItemPair(mCollection.get(i), mCollection.get(j));
                testQuestions.add(question);
            }
        }
        return testQuestions;
    }

    public void saveTestAnswers (ArrayList<ItemPair> results) {
        this.sessionID = getSessionID();
        db.saveResults(sessionID, results);
    }

    public ArrayList<ItemPair> getTestQuestions() { return testQuestions; }

    public void setTestQuestions(ArrayList<String> mCollection) {
        this.testQuestions = makeTestQuestions();
    }

    public int getSessionID() {
        TestSession mTestSession = db.assignSessionID(testID, userID);
        this.sessionID = mTestSession.getSessionID();
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCollectionID() { return collectionID; }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    public ArrayList<Item> getCollection() { return mCollection; }

    public void setCollection(int collectionID) {
        this.mCollection = db.readItems(this.collectionID);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }
}