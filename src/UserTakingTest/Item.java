package UserTakingTest;

/**
 * Class to hold an Item
 * @author Liz Goltz
 * @version 6/3/2018
 *
 * modifications:
 * - added tally field with getter & setter
 */
public class Item {
    private int mItemID;
    private int mTestID;
    private String mName;
    private int tally;

    public Item(int itemID, int testID, String name) {
        mItemID = itemID;
        mTestID = testID;
        mName = name;
    }
    public int getTally() {
        return tally;
    }

    public void setTally(int tally) {
        this.tally = tally;
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
