package ResultReporting;

import java.util.Vector;

public class Item {
    private String name;
    private int wins;
    private int losses;
    private int ties;

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

    /**
     * Calculates the rank for an item
     * @return the rank
     */
    public int calculateRank() {
        return wins - losses;
    }

}
