package ResultReporting;

import SharedLogic.Item;
import SharedLogic.Result;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author  Bobby Puckett
 * @version 5.8.2018
 *
 * An extension of the Item class which also stores the number of wins/losses/ties for that Item
 */
public class ReportingItem extends Item implements Comparable<ReportingItem> {
    private int wins;
    private int losses;
    private int ties;

    public ReportingItem(int itemID, int testID, String name) {
        super(itemID, testID, name);
    }

    public ReportingItem(int itemID, int testID, String name, int wins, int losses, int ties) {
        super(itemID, testID, name);

        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public ReportingItem(Item item) {
        super(item.getMyItemID(), item.getMyTestID(), item.getMyName());

        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
    }

    /**
     * returns the losses for the Item
     *
     * @return the losses for the Item
     */
    public int getLosses() {
        return losses;
    }

    /**
     * returns the ties for the Item
     *
     * @return the ties for the Item
     */
    public int getTies() {
        return ties;
    }

    /**
     * returns the wins for the Item
     *
     * @return the wins for the Item
     */
    public int getWins() {
        return wins;
    }

    /**
     * returns the score for the item (wins - losses)
     * @return wins minus losses
     */
    public int getScore() {
        return wins - losses;
    }

    /**
     * adds a win to the total wins
     */
    private void addWin() {
        wins++;
    }

    /**
     * adds a loss to the total losses
     */
    private void addLoss() {
        losses++;
    }

    /**
     * adds a tie to the total ties
     */
    private void addTie() {
        ties++;
    }

    public static ArrayList<ReportingItem> buildReportingItems(ArrayList<Result> results) {
        ArrayList<ReportingItem> reportingItems = new ArrayList<>();

        for (Result result : results) {
            int winnerExists = -1;
            int loserExists = -1;
            if (result.getTies().size() == 0) {
                Item winner = result.getWinner();
                Item loser = result.getLoser();

                winnerExists = checkForResult(reportingItems, winner);
                loserExists = checkForResult(reportingItems, loser);

                if (winnerExists != -1) {
                    reportingItems.get(winnerExists).addWin();
                } else {
                    ReportingItem newReportingItem = new ReportingItem(winner);
                    newReportingItem.addWin();
                    reportingItems.add(newReportingItem);
                }

                if (loserExists != -1) {
                    reportingItems.get(loserExists).addLoss();
                } else {
                    ReportingItem newReportingItem = new ReportingItem(loser);
                    newReportingItem.addLoss();
                    reportingItems.add(newReportingItem);
                }

            }
            else {
                ArrayList<Item> ties = result.getTies();

                for (Item tie : ties) {
                    int tieIndex = checkForResult(reportingItems, tie);

                    if (tieIndex != -1) {
                        reportingItems.get(tieIndex).addTie();
                    }
                    else {
                        ReportingItem newReportingItem = new ReportingItem(tie);
                        newReportingItem.addTie();
                        reportingItems.add(newReportingItem);
                    }
                }
            }
        }

        Collections.sort(reportingItems);

        return reportingItems;
    }

    /**
     * Gets all Items for a testSessionID, converts them to ReportingItems and returns them
     *
     * @param testSessionID determines which TestSessionID to search for
     * @return an ArrayList of ReportingItems for a given testSession
     */
    public static ArrayList<ReportingItem> retrieveReportingItemsOnTest(int testSessionID) {
        ArrayList<Result> results = Result.retrieveTestSessionResults(testSessionID);
        ArrayList<ReportingItem> reportingItems = new ArrayList<>();

        for (Result result : results) {
            int winnerExists = -1;
            int loserExists = -1;
            if (result.getTies().size() == 0) {
                Item winner = result.getWinner();
                Item loser = result.getLoser();

                winnerExists = checkForResult(reportingItems, winner);
                loserExists = checkForResult(reportingItems, loser);

                if (winnerExists != -1) {
                    reportingItems.get(winnerExists).addWin();
                } else {
                    ReportingItem newReportingItem = new ReportingItem(winner);
                    newReportingItem.addWin();
                    reportingItems.add(newReportingItem);
                }

                if (loserExists != -1) {
                    reportingItems.get(loserExists).addLoss();
                } else {
                    ReportingItem newReportingItem = new ReportingItem(loser);
                    newReportingItem.addLoss();
                    reportingItems.add(newReportingItem);
                }

            }
            else {
                ArrayList<Item> ties = result.getTies();

                for (Item tie : ties) {
                    int tieIndex = checkForResult(reportingItems, tie);

                    if (tieIndex != -1) {
                        reportingItems.get(tieIndex).addTie();
                    }
                    else {
                        ReportingItem newReportingItem = new ReportingItem(tie);
                        newReportingItem.addTie();
                        reportingItems.add(newReportingItem);
                    }
                }
            }
        }

        Collections.sort(reportingItems);

        return reportingItems;
    }

    /**
     * checks for the item in the reportingItem list and returns the index of the instance of the item or -1
     * if the item doesn't exist in the list.
     *
     * @param reportingItems the ArrayList of ReportingItems which might contain the item
     * @param item the Item to search for in the ArrayList of ReportingItems
     * @return the index of the item or -1 if the item isn't in the list.
     */
    private static int checkForResult(ArrayList<ReportingItem> reportingItems, Item item) {
        for (ReportingItem reportingItem : reportingItems) {
            if (reportingItem.getMyName().equals(item.getMyName())) {
                return reportingItems.indexOf(reportingItem);
            }
        }

        return -1;
    }

    /**
     * @param reportingItem the ReportingItem to compare against
     * @return the object with the higher score
     *
     * Allows ReportingItem ArrayLists to be sorted
     */
    @Override
    public int compareTo(ReportingItem reportingItem) {
        int score = reportingItem.getScore();

        return score - this.getScore();
    }
}
