import java.util.ArrayList;

/**
 * Class to manage the test collection
 */
public class Collection
{
    int collectionID;
    ArrayList<Item> items;

    public Collection() {
        this.collectionID = collectionID;
        this.items = items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getCollectionID() { return collectionID; }

    public void setCollectionID(int collectionID) { this.collectionID = collectionID; }
}
