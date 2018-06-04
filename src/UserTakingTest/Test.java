package UserTakingTest;

import Database.Database;
import java.sql.Date;
import java.util.*;

/**
 * Class for User Taking Test Logic
 * @author Liz Goltz
 * @version 6/3/2018
 *
 * modifications:
 *  - added random question feature
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
        testQuestions = new ArrayList<ItemPair>();
    }

    /**
     * Class to make random test questions according to specifications
     * @return testQuestions - an arraylist of ItemPairs
     */
    public ArrayList<ItemPair> makeRandomTestQuestions() {
        testQuestions = new ArrayList<>();
        ArrayList<ItemPair> tempQuestions = makeTestQuestions();
        initializeTally();
        while (!tempQuestions.isEmpty()) {
           ItemPair lowest = Collections.min(tempQuestions, new Comparator<ItemPair>() {
                @Override
                public int compare(ItemPair o1, ItemPair o2) {
                    return o1.getTally() - o2.getTally();
                }
           });
           testQuestions.add(lowest);
           tempQuestions.remove(lowest);
           incrementTally(lowest.getItem1());
           incrementTally(lowest.getItem2());
        }
        return testQuestions;
    }

    void initializeTally() {
        for (Item item : mCollection) {
            item.setTally(0);
        }
    }

    void incrementTally(Item item) {
      item.setTally(item.getTally() + 1);
    }

    /**
     * Method to create a randomized list of test questions of all the unique pairings from a list of items
     * @return testQuestions a list of item pairs /test questions
     */
    public ArrayList<ItemPair> makeTestQuestions() {
        Random rand = new Random();
        ArrayList<ItemPair> testQuestions = new ArrayList<ItemPair>();
        for (int i = 0; i < mCollection.size() - 1; i++) {
            for (int j = i+1; j < mCollection.size(); j++) {
                int random = rand.nextInt(testQuestions.size() + 1);
                ItemPair question = new ItemPair();
                if (random % 2 == 0) {
                    question.setItem1(mCollection.get(i));
                    question.setItem2(mCollection.get(j));
                }
                else {
                    question.setItem1(mCollection.get(j));
                    question.setItem2(mCollection.get(i));
                }
                System.out.println(question.getItem1().getName() + " " + question.getItem2().getName());
                testQuestions.add(question);
            }
        }
        //Collections.shuffle(testQuestions);
        return testQuestions;
    }

    public void saveTestAnswers (ArrayList<ItemPair> results) {
        this.sessionID = getSessionID();
        db.saveResults(sessionID, results);
    }

    public ArrayList<ItemPair> getTestQuestions() { return testQuestions; }

    public void setTestQuestions(ArrayList<String> mCollection) {
        this.testQuestions = makeRandomTestQuestions();
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
