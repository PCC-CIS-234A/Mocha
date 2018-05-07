package ResultReporting;

import SharedLogic.Item;
import SharedLogic.Result;

import java.util.ArrayList;

public class ReportingItem extends Item {
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


    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getWins() {
        return wins;
    }

    private void addWin() {
        wins++;
    }

    private void addLoss() {
        losses++;
    }

    private void addTie() {
        ties++;
    }


    public static ArrayList<ReportingItem> retrieveReportingItemsOnTest(int testSessionID) {
        ArrayList<Result> results = Result.retrieveTestSessionResults(testSessionID);
        ArrayList<ReportingItem> reportingItems = new ArrayList<>();

        //this is quick and dirty. Let me know if you have a way to clean up the searching through the lists
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

        return reportingItems;
    }

    private static int checkForResult(ArrayList<ReportingItem> reportingItems, Item item) {
        for (ReportingItem reportingItem : reportingItems) {
            if (reportingItem.getMyName().equals(item.getMyName())) {
                return reportingItems.indexOf(reportingItem);
            }
        }

        return -1;
    }


}
