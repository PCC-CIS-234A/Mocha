package UserTakingTest;

public class Item {
    private int mItemID;
    private int mTestID;
    private String mName;

    public Item(int itemID, int testID, String name) {
        mItemID = itemID;
        mTestID = testID;
        mName = name;
    }

    public int getItemID() {
        return mItemID;
    }

    public int getTestID() {
        return mTestID;
    }

    public String getName() {
        return mName;
    }
}
