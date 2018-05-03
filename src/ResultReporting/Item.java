package ResultReporting;

import java.util.Vector;

public class Item {
    private int itemID;
    private String name;
    private int wins;
    private int losses;
    private int ties;

    public Item(int itemID, String name, int wins, int losses, int ties) {
        this.itemID = itemID;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public Item(String name, int wins, int losses, int ties) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    /**
     * Creates the Vector used by the resultsTable
     * @return the Vector created
     */
    public Vector<String> createRowData() {
        Vector<String> rowData = new Vector<>();

        rowData.add("Sample User");
        rowData.add(name);
        rowData.add(String.valueOf(wins));
        rowData.add(String.valueOf(losses));
        rowData.add(String.valueOf(ties));

        return rowData;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }
    public void addTie() {
        ties++;
    }

    public String getName() {
        return name;
    }

    public int getItemID() {
        return itemID;
    }

    /**
     * Calculates the rank for an item
     * @return the rank
     */
    public int calculateRank() {
        return wins - losses;
    }

}
