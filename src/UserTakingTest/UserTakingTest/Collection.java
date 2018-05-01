package UserTakingTest;

import java.util.ArrayList;

/**
 * Class to manage the test collection
 * @author Liz Goltz
 * @version
 */
public class Collection
{
    int collectionID;
    ArrayList<UserTakingTest.Item> items;

    public Collection() {
        this.collectionID = collectionID;
        this.items = items;
    }

    public void setItems(ArrayList<UserTakingTest.Item> items) {
        this.items = items;
    }

    public ArrayList<UserTakingTest.Item> getItems() {
        return items;
    }

    public int getCollectionID() { return collectionID; }

    public void setCollectionID(int collectionID) { this.collectionID = collectionID; }
}
