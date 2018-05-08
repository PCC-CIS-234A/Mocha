package UserTakingTest;

/**
 * Class to hold an item pairing and win code
 * @author Liz Goltz
 * @version 4/3/2018
 */
public class ItemPair {
    private Item item1;
    private Item item2;
    private int winItem;

    public ItemPair(Item item1, Item item2) {
        this.item1 = item1;
        this.item2 = item2;
        //?winItem will be set by user input, do I need to initialize it in constructor?
        this.winItem = 3; // 3 is a number not indicating a test answer b/c can't initialize int to null
    }

    public Item getItem1() {
        return item1;
    }

    public void setItem1(Item item1) {
        this.item1 = item1;
    }

    public Item getItem2() {
        return item2;
    }

    public void setItem2(Item item2) {
        this.item2 = item2;
    }

    public int getWinItem() {
        return winItem;
    }

    public void setWinItem(int winItem) {
        this.winItem = winItem;
    }
}
