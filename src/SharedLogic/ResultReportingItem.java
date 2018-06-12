package SharedLogic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author  Bobby Puckett
 * @version 5.8.2018
 *
 * An extension of the Item class which also stores the number of wins/losses/ties for that Item
 */
public class ResultReportingItem extends Item implements Comparable<ResultReportingItem> {
    private int wins;
    private int losses;
    private int ties;

    public ResultReportingItem(int itemID, int testID, String name) {
        super(itemID, testID, name);
    }

    public ResultReportingItem(int itemID, int testID, String name, int wins, int losses, int ties) {
        super(itemID, testID, name);

        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public ResultReportingItem(Item item) {
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

    public static ArrayList<ResultReportingItem> buildReportingItems(ArrayList<Result> results) {
        ArrayList<ResultReportingItem> resultReportingItems = new ArrayList<>();

        for (Result result : results) {
            int winnerExists = -1;
            int loserExists = -1;
            if (result.getTies().size() == 0) {
                Item winner = result.getWinner();
                Item loser = result.getLoser();

                winnerExists = checkForResult(resultReportingItems, winner);
                loserExists = checkForResult(resultReportingItems, loser);

                if (winnerExists != -1) {
                    resultReportingItems.get(winnerExists).addWin();
                } else {
                    ResultReportingItem newResultReportingItem = new ResultReportingItem(winner);
                    newResultReportingItem.addWin();
                    resultReportingItems.add(newResultReportingItem);
                }

                if (loserExists != -1) {
                    resultReportingItems.get(loserExists).addLoss();
                } else {
                    ResultReportingItem newResultReportingItem = new ResultReportingItem(loser);
                    newResultReportingItem.addLoss();
                    resultReportingItems.add(newResultReportingItem);
                }

            }
            else {
                ArrayList<Item> ties = result.getTies();

                for (Item tie : ties) {
                    int tieIndex = checkForResult(resultReportingItems, tie);

                    if (tieIndex != -1) {
                        resultReportingItems.get(tieIndex).addTie();
                    }
                    else {
                        ResultReportingItem newResultReportingItem = new ResultReportingItem(tie);
                        newResultReportingItem.addTie();
                        resultReportingItems.add(newResultReportingItem);
                    }
                }
            }
        }

        Collections.sort(resultReportingItems);

        return resultReportingItems;
    }

    /**
     * Gets all Items for a testSessionID, converts them to ReportingItems and returns them
     *
     * @param testSessionID determines which TestSessionID to search for
     * @return an ArrayList of ReportingItems for a given testSession
     */
    public static ArrayList<ResultReportingItem> retrieveReportingItemsOnTest(int testSessionID) {
        ArrayList<Result> results = Result.retrieveTestSessionResults(testSessionID);
        ArrayList<ResultReportingItem> resultReportingItems = new ArrayList<>();

        for (Result result : results) {
            int winnerExists = -1;
            int loserExists = -1;
            if (result.getTies().size() == 0) {
                Item winner = result.getWinner();
                Item loser = result.getLoser();

                winnerExists = checkForResult(resultReportingItems, winner);
                loserExists = checkForResult(resultReportingItems, loser);

                if (winnerExists != -1) {
                    resultReportingItems.get(winnerExists).addWin();
                } else {
                    ResultReportingItem newResultReportingItem = new ResultReportingItem(winner);
                    newResultReportingItem.addWin();
                    resultReportingItems.add(newResultReportingItem);
                }

                if (loserExists != -1) {
                    resultReportingItems.get(loserExists).addLoss();
                } else {
                    ResultReportingItem newResultReportingItem = new ResultReportingItem(loser);
                    newResultReportingItem.addLoss();
                    resultReportingItems.add(newResultReportingItem);
                }

            }
            else {
                ArrayList<Item> ties = result.getTies();

                for (Item tie : ties) {
                    int tieIndex = checkForResult(resultReportingItems, tie);

                    if (tieIndex != -1) {
                        resultReportingItems.get(tieIndex).addTie();
                    }
                    else {
                        ResultReportingItem newResultReportingItem = new ResultReportingItem(tie);
                        newResultReportingItem.addTie();
                        resultReportingItems.add(newResultReportingItem);
                    }
                }
            }
        }

        Collections.sort(resultReportingItems);

        return resultReportingItems;
    }

    /**
     * checks for the item in the reportingItem list and returns the index of the instance of the item or -1
     * if the item doesn't exist in the list.
     *
     * @param resultReportingItems the ArrayList of ReportingItems which might contain the item
     * @param item the Item to search for in the ArrayList of ReportingItems
     * @return the index of the item or -1 if the item isn't in the list.
     */
    private static int checkForResult(ArrayList<ResultReportingItem> resultReportingItems, Item item) {
        for (ResultReportingItem resultReportingItem : resultReportingItems) {
            if (resultReportingItem.getMyName().equals(item.getMyName())) {
                return resultReportingItems.indexOf(resultReportingItem);
            }
        }

        return -1;
    }

    /**
     * @param resultReportingItem the ResultReportingItem to compare against
     * @return the object with the higher score
     *
     * Allows ResultReportingItem ArrayLists to be sorted
     */
    @Override
    public int compareTo(ResultReportingItem resultReportingItem) {
        int score = resultReportingItem.getScore();

        return score - this.getScore();
    }
}
