package UserTakingTest;

import java.util.ArrayList;

/**
 * Class for Test Logic
 * @author Liz Goltz
 * @version
 */
public class Test
{
    private int sessionID;
    private String userName;
    private int collectionID;
    private UserTakingTest.Item item; //do I need this?
    private UserTakingTest.Collection mCollection;
    private UserTakingTest.ItemPair mItemPair;
    private ArrayList<UserTakingTest.ItemPair> testQuestions;

//?What do I need to define in my constructor here?
    public Test(int sessionID, String userName, int collectionID) {
        this.sessionID = sessionID;
        this.userName = userName;
        this.collectionID = collectionID;
    }

    public UserTakingTest.Collection getCollection() { return mCollection; }

 /*   public void setCollection(ArrayList<Item> collection) {
        this.mCollection = new Collection();
        MochaDB db = new MochaDB(); //do I need do declare this as a field?
        ArrayList items = db.readItems(this.collectionID);
        this.mCollection.setItems(items);
    }
*/
    public ArrayList<UserTakingTest.ItemPair> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(UserTakingTest.Collection collection) {
        ArrayList<UserTakingTest.Item> testItems = new ArrayList();
        testItems = collection.getItems();
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
