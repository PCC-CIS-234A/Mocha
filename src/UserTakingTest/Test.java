package UserTakingTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Test Logic
 * @author Liz Goltz
 * @version 4/3/2018
 */
public class Test
{
    private int sessionID;
    private String userName;
    private int collectionID;
    private ArrayList<String> mCollection;
    private ArrayList<UserTakingTest.ItemPair> testQuestions;

    public Test(int sessionID, String userName, int collectionID) {
        this.sessionID = sessionID;
        this.userName = userName;
        this.collectionID = collectionID;
        setCollection(collectionID);
        this.testQuestions = makeTestQuestions(this.mCollection);
    }

    public ArrayList<String> getCollection() { return mCollection; }

    public void setCollection(int collectionID) {
        this.mCollection = new ArrayList<>();
        MochaDB db = new MochaDB(); //do I need do declare this as a field?
        this.mCollection = db.readItems(this.collectionID);
    }

    /**
     * Method to create a list of test questions of all the unique pairings from a list of items
     * @param items a list of items
     * @return testQuestions a list of item pairs /test questions
     */
    //?should I set this as private?
    public ArrayList<UserTakingTest.ItemPair> makeTestQuestions (ArrayList<String> items) {
        testQuestions = new ArrayList<ItemPair>();
        //for each item in the ArrayList of items, create a pairing with all subsequent items
        for (int i = 0; i < items.size(); i++) {
            for (int j = i+1; j < items.size(); j++) {
                System.out.println(items.get(i) + " " + items.get(j));
                ItemPair question = new ItemPair(items.get(i), items.get(j));
                testQuestions.add(question);
            }
        }
        return testQuestions;
    }

    public ArrayList<UserTakingTest.ItemPair> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(ArrayList<String> mCollection) {
        this.testQuestions = makeTestQuestions(mCollection);
    }

    public int getSessionID() { return sessionID; }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //?login class?
    public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }
}
