package ResultReporting;

import SharedLogic.Item;
import SharedLogic.TestSession;
import SharedLogic.Result;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Rebecca Kennedy
 * @version 5/30/2018.
 * Displays cumulative statistics.
 */
public class CumulativeStatistics {
    private JPanel cumStatPanel;
    private JFrame frame;

  //  private DefaultTableModel matrixTableModel;
    private String testName;
    private List<ReportingItem> items;
    private ArrayList<Result> results;
    private ArrayList<Result> allResults;
    private Container content;
    private ResultScoresForm rsf;
    private int testSessionID;
    private int testID;
    private Data data;
    private ArrayList<ReportingItem> highestScoredItems;

    public CumulativeStatistics(JFrame frame, ResultScoresForm resScoForm) {

        cumStatPanel.setPreferredSize(new Dimension(500, 400));
        this.frame = frame;

        rsf = resScoForm;
        getData();
        printData();
    } //end constructor

    private void printData() {
        //System.out.println(testID);
    }

    private void getData() {
        //Get TestSession to get other values
        TestSession ts = rsf.getTestSession();

        //Get the Session ID.
        testSessionID = ts.getMySessionID();

        //Get the Test ID and Test Name
        if (ts.getMyTest() != null) {
            testID = ts.getMyTest().getMyTestID();
//            testName = ts.getMyTest().getMyName();
        }
        System.out.println("First testID: " + testID);

        //Get ReportingItems which are a child of SharedLogic.Item and store them in a List
//        items = rsf.getReportingItems();

        //Get Results
        //      results = rsf.getResults();


      //  allResults = new ArrayList<>();
        data = new Data();

/*
        data.getResults().forEach(dataResult -> {
            //Item1 and Item2 should belong to the same test so I will just get Item1's TestID
            if (dataResult.getMyItemOne().getMyTestID() == testID) {
                allResults.add(dataResult);
            }
        });


        System.out.println("ALL RESULTS");
        for (Result rs : allResults) {
            System.out.println();
            System.out.println("TestID: " + rs.getMyItemOne().getMyTestID());
            System.out.println("ResultID: " + rs.getMyResultID());
            System.out.println("Item1: " + rs.getMyItemOne().getMyName());
            System.out.println("Item2: " + rs.getMyItemTwo().getMyName());
        }
        */

/*
        ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(allResults);

        System.out.println("REPORTING ITEMS");
        for (ReportingItem item : reportingItems) {
            int score = item.getScore();
            System.out.println();
            System.out.println("Name: " + item.getMyName());
            System.out.println("Score: " + score);
        }
        */



/*
        for (ReportingItem item : items) {
            int score = item.getScore();
            System.out.println();
            System.out.println("Name: " + item.getMyName());
            System.out.println("Score: " + score);
        }
*/


        //CODE I'M WORKING ON
        ArrayList<TestSession> allTestSessions = new ArrayList<>();

        System.out.println("testID: " + testID);


        System.out.println("DATARESULT");
        data.getTestSessions().forEach(dataResult -> {
            if (dataResult.getMyTest().getMyTestID() == testID) {
                allTestSessions.add(dataResult);
            }
        });

        System.out.println("CODE I'M WORKING ON");
        for (TestSession testSession : allTestSessions) {

            results = new ArrayList<>();
            if (testSession != null) {
                int id = testSession.getMySessionID();

                data.getResults().forEach(dataResult -> {
                    if (dataResult.getMySessionID() == id) {
                        results.add(dataResult);
                    }
                });

                System.out.println("SessionID:" + id);
                }//NEW

                ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(results);

            boolean allEqual = true;
            for(ReportingItem item : reportingItems) {
                if(item.getScore() != (reportingItems.get(0).getScore())) {
                    allEqual = false;
                }
            }

            highestScoredItems = new ArrayList<>();
            ReportingItem highest = reportingItems.get(0);

            if(allEqual == false) {



                System.out.println("REPORTING ITEMS");
                for (ReportingItem item : reportingItems) {
                    // addHighestScoredItems(item);

                    //  highestScoredItems = new ArrayList<>();
                    //   ReportingItem highest = reportingItems.get(0);

                    System.out.println();
                    System.out.println("highest.getMyName(): " + highest.getMyName());
                    System.out.println("highest.getScore(): " + highest.getScore());
                    System.out.println("item.getMyName(): " + item.getMyName());
                    System.out.println("item.getScore(): " + item.getScore());
                    System.out.println();

                    if (item.getScore() > highest.getScore()) {
                        //maxItemCharLength = charLength;
                        System.out.println("Before highest: " + highest.getMyName());
                        System.out.println("Before item: " + item.getMyName());
                        highest = item;
                        System.out.println("After highest: " + highest.getMyName());
                        System.out.println("After item: " + item.getMyName());
                    }
                    //ORIG              }


                    System.out.println("Right before adding highest: " + highest.getMyName());
                    System.out.println("Right before adding highest: " + highest.getScore());

                    /*******
                     *  Note to self:
                     *  Right now I don't think this supports multiple winners. Maybe that's the problem?
                     *  For example, in test 5, football, basketball, and badminton are all tied for the
                     *  top with 1 point each.
                     ******/

                    highestScoredItems.add(highest);
                }

            }

        //    }
        } //end for loop

        System.out.println("HighestScoredItems");
        for(ReportingItem item : highestScoredItems) {
            int score = item.getScore();
            System.out.println();
            System.out.println("TestID: " + item.getMyTestID());
            System.out.println("ItemID: " + item.getMyItemID());
            System.out.println("Name: " + item.getMyName());
            System.out.println("Score: " + score);
        }

        /*
        results = new ArrayList<>();
        if (testSession != null) {
            int id = testSession.getMySessionID();

            data.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == id) {
                    results.add(dataResult);
                }
            });

            ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(results);
        }
*/


        /*
        ArrayList<TestSession> testSessions = data.getTestSessions();

        results = new ArrayList<>();
        for (TestSession testSession : testSessions) {
            if (testSession != null) {
                int id = testSession.getMySessionID();

                data.getResults().forEach(dataResult -> {
                    if (dataResult.getMySessionID() == id) {
                        results.add(dataResult);
                    }
                });
            }

                for (ReportingItem item : items) {
                    int score = item.getScore();
                    System.out.println();
                    System.out.println("Name: " + item.getMyName());
                    System.out.println("Score: " + score);
                }
            }





            results = new ArrayList<>();
        if (testSession != null) {
            int id = testSession.getMySessionID();

            data.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == id) {
                    results.add(dataResult);
                }
            });
            */
    }

/*
    //private ArrayList<ReportingItem> getHighestScoredItems(ArrayList<ReportingItem> reportingItems) {
     private void addHighestScoredItems(ReportingItem item) {

        highestScoredItems = new ArrayList<>();
        ReportingItem highest = reportingItems.get(0);

    //    System.out.println("REPORTING ITEMS");
      //  for (ReportingItem item : reportingItems) {
        //    int score = item.getScore();
        //    System.out.println();
        //    System.out.println("TestID: " + item.getMyTestID());
       //     System.out.println("Name: " + item.getMyName());
        //    System.out.println("Score: " + score);
        //    System.out.println("Can you see me here?");
            System.out.println("item.getScore(): " + item.getScore());
            System.out.println("highest.getScore(): " + highest.getScore());

            if (item.getScore() > highest.getScore()) {
                //maxItemCharLength = charLength;
                System.out.println("Before highest: " + highest.getMyName());
                System.out.println("Before item: " + item.getMyName());
                highest = item;
                System.out.println("After highest: " + highest.getMyName());
                System.out.println("After item: " + item.getMyName());
            }
     //   }


        highestScoredItems.add(highest);
        //return highestScoredItems;

        /*
        int maxItemCharLength = matrixTable.getColumnModel().getColumn(0).getMinWidth();
        for (ReportingItem item : items) {
            int charLength = item.getMyName().length();
            if (charLength > maxItemCharLength) {
                maxItemCharLength = charLength;
            }
        }
        return maxItemCharLength;
        */ /*
    }
    */

    /**
     * Getter for cumStatPanel
     *
     * @return the cumStatPanel
     */
    public JPanel getcumStatPanel() {
        return cumStatPanel;
    }
}
