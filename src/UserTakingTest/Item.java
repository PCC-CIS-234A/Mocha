

/**
 * An item in the test collection
 * @author Liz Goltz
 * @version
 */
public class Item {
    private int itemID;
    private String itemName;

    public Item(int itemID, String itemName){
        this.itemID = itemID;
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
