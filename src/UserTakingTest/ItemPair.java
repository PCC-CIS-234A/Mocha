package UserTakingTest;

/**
 * Class to hold an item pairing and win code
 * @author Liz Goltz
 * @version
 */
public class ItemPair {
    private UserTakingTest.Item item1;
    private UserTakingTest.Item item2;
    private int winItem;

    public ItemPair(UserTakingTest.Item item1, UserTakingTest.Item item2) {
        this.item1 = item1;
        this.item2 = item2;
        //?winItem will be set by user input, do I need to initialize it in constructor?
        this.winItem = 0; //can i initialize this to null?
    }

    public UserTakingTest.Item getItem1() {
        return item1;
    }

    public void setItem1(UserTakingTest.Item item1) {
        this.item1 = item1;
    }

    public UserTakingTest.Item getItem2() {
        return item2;
    }

    public void setItem2(UserTakingTest.Item item2) {
        this.item2 = item2;
    }

    public int getWinItem() {
        return winItem;
    }

    public void setWinItem(int winItem) {
        this.winItem = winItem;
    }
}
