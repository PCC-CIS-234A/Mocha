package UserTakingTest;

import java.util.ArrayList;

/**
 * Class for Test Logic
 */
public class Test
{
    private int sessionID;
    private String userName;
    private int collectionID;
    private Item item; //do I need this?
    private Collection mCollection;
    private ItemPair mItemPair;
    private ArrayList<ItemPair> testQuestions;

//?What do I need to define in my constructor here?
    public Test(int sessionID, String userName, int collectionID) {
        this.sessionID = sessionID;
        this.userName = userName;
        this.collectionID = collectionID;
    }

    public Collection getCollection() { return mCollection; }

 /*   public void setCollection(ArrayList<Item> collection) {
        this.mCollection = new Collection();
        MochaDB db = new MochaDB(); //do I need do declare this as a field?
        ArrayList items = db.readItems(this.collectionID);
        this.mCollection.setItems(items);
    }
*/
    public ArrayList<ItemPair> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(Collection collection) {
        ArrayList<Item> testItems = new ArrayList();
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
