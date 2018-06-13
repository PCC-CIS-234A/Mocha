package ResultReporting;

import SharedLogic.TestSession;
import SharedLogic.Result;
import SharedLogic.ResultReportingItem;
import SharedLogic.ResultReportingDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * @author Rebecca Kennedy
 * @version 5/30/2018.
 * Displays cumulative statistics.
 */
public class CumulativeStatistics {
    private JPanel cumStatPanel;
    private JTextArea infoTextArea;
    private JScrollPane cumStatScrollPane;
    private JFrame frame;

    private String testName;
    private ArrayList<Result> results;
    private ResultScoresForm rsf;
    private int testID;
    private ResultReportingDatabase data;
    ArrayList<ResultReportingItem> reportingItems;
    private ArrayList<ResultReportingItem> highestScoredItems;

    public CumulativeStatistics(JFrame frame, ResultScoresForm resScoForm) {

        cumStatPanel.setPreferredSize(new Dimension(450, 300));
        this.frame = frame;
        infoTextArea.setMargin(new Insets(10, 15, 10, 15));

        rsf = resScoForm;
        getData();

        frame.setTitle("Cumulative Statistics - " + testName);

        organizePopRankItemFirst();

        infoTextArea.setEditable(false);
    } //end constructor

    /**
     * Calls the methods that will decide and display this statistics.
     * This statistic figures out what percent of the population ranks
     * each item first.
     */
    private void organizePopRankItemFirst() {
        findHighestScoredItems();
        Object[] objects = popRankItemFirst();
        printPopRankItemFirst(objects);
    }

    /**
     * Gets the data needed for this class
     */
    private void getData() {
        //Get TestSession to get other values
        TestSession ts = rsf.getTestSession();

        //Gets the Test Name
        if(ts.getMyTest() != null) {
            testName = ts.getMyTest().getMyName();
        }

        //Gets the Test ID
        if (ts.getMyTest() != null) {
            testID = ts.getMyTest().getMyTestID();
        }

        //Gets all the data from the database
        data = rsf.getResultReportingDatabase();
    }

    /**
     * Figures out which of the items got the highest scores and then
     * stores them in an ArrayList
     */
    public void findHighestScoredItems() {

        ArrayList<TestSession> allTestSessions = new ArrayList<>();
        highestScoredItems = new ArrayList<>();

        data.getTestSessions().forEach(dataResult -> {
            if (dataResult.getMyTest().getMyTestID() == testID) {
                allTestSessions.add(dataResult);
            }
        });

        for (TestSession testSession : allTestSessions) {

            results = new ArrayList<>();
            if (testSession != null) {
                int id = testSession.getMySessionID();

                data.getResults().forEach(dataResult -> {
                    if (dataResult.getMySessionID() == id) {
                        results.add(dataResult);
                    }
                });
            }

            reportingItems = ResultReportingItem.buildReportingItems(results);

            if(areAllEqual(reportingItems) == false) {
                addHighestItems(reportingItems);
            }
        } //end for loop
    }

    /**
     * Creates an ArrayList of of ResultReportingItem names as Strings.
     * @param arrayList
     * @return names
     */
    private ArrayList<String> namesArrayList(ArrayList<ResultReportingItem> arrayList) {
        ArrayList<String> names = new ArrayList<>();
        for(ResultReportingItem item : arrayList) {
            names.add(item.getMyName());
        }
        return names;
    }

    /**
     * Creates an ArrayList of of String names as Strings.
     * @param arrayList
     * @return names
     */
    public ArrayList<String> namesStrArrayList(ArrayList<String> arrayList) {
        ArrayList<String> names = new ArrayList<>();
        for(String item : arrayList) {
            names.add(item);
        }
        return names;
    }

    /**
     * Decides what percent of the population ranks each item first.
     * @return objects
     */
    public Object[] popRankItemFirst() {
        ArrayList<String> names = namesArrayList(highestScoredItems);

        List<String> namesList = names;
        Set<String> namesSet = new HashSet<>(namesList);

        HashMap<String, Float> map = new HashMap<>();

        infoTextArea.append("Population percentage that ranked an item most preferred:\n");

        for(String name : namesSet) {
            float decimal = (((float)Collections.frequency(namesList, name)) / (float)highestScoredItems.size());
            float perc = percent(decimal);
            map.put(name,perc);
        }

        Object[] objects = map.entrySet().toArray();
        sortItemsByPerc(objects);

        return objects;
    }

    /**
     * Sorts items and their respective percentage in decending order by their percent value.
     * @param objects
     */
    private void sortItemsByPerc(Object[] objects) {
        Arrays.sort(objects, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Float>) o2).getValue().compareTo(((Map.Entry<String, Float>) o1).getValue());
            }
        });
    }

    /**
     * Calculates the percent value of the given decimal
     * @param decimal
     * @return
     */
    private float percent(float decimal) {
        float percent = decimal * 100;
        return percent;
    }

    /**
     * Creates and returns a string of the item and its respective percent value.
     * This string is expected to be used in the JTestArea.
     * @param item
     * @param percent
     * @return
     */
    private String getItemAndPercStr(String item, Float percent) {
        String percentStr = String.format("%%%4.1f", percent);
        String str = item + ": \t" + percentStr + "\n";
        return str;
    }

    /**
     * Prints the popRankItemFirst() data in the JTextArea.
     * @param objects
     */
    private void printPopRankItemFirst(Object[] objects) {

        for (Object obj : objects) {
            String name = ((Map.Entry<String, Float>) obj).getKey();
            float percent = ((Map.Entry<String, Float>) obj).getValue();
            String str = getItemAndPercStr(name, percent);
            infoTextArea.append(str);
        }

        ArrayList<String> names = namesArrayList(reportingItems);

        ArrayList<String> objStrs = new ArrayList<>();
        for (Object obj : objects) {
            String name = ((Map.Entry<String, Float>) obj).getKey();
            objStrs.add(name);
        }

        ArrayList<String> objNames = namesStrArrayList(objStrs);

        for (String itemName : names) {

            boolean anyEqual = false;
            for (String objName : objNames) {
                if (objName.equals(itemName)) {
                        anyEqual = true;
                }
            } //end for loop

            if(anyEqual == false) {
                float zeroPerc = 0;
                String str = getItemAndPercStr(itemName, zeroPerc);
                infoTextArea.append(str);
            } //end if

        } //end for loop
    }

    /**
     * Checks if the given items' scores are all equal
     * @param reportingItems
     * @return allEqual
     */
    public boolean areAllEqual(ArrayList<ResultReportingItem> reportingItems) {
        boolean allEqual = true;
        for (ResultReportingItem item : reportingItems) {
            if (item.getScore() != (reportingItems.get(0).getScore())) {
                allEqual = false;
            }
        }
        return allEqual;
    }

    /**
     * Adds the items with the highest scores to highestScoredItems
     * @param reportingItems
     */
    public void addHighestItems(ArrayList<ResultReportingItem> reportingItems) {
        ResultReportingItem highest = reportingItems.get(0);

        //Adds the last item in the ArrayList that had the highest score
        for (ResultReportingItem item : reportingItems) {
            if (item.getScore() > highest.getScore()) {
                highest = item;
            }
        }

        highestScoredItems.add(highest);

        //Adds any other items that had the same score
        for (ResultReportingItem item : reportingItems) {
            if((item.getScore() == highest.getScore()) && (item.getMyName().equals(highest.getMyName()) == false)) {
                highestScoredItems.add(item);
            }
        }
    }

    /**
     * Getter for cumStatPanel
     *
     * @return the cumStatPanel
     */
    public JPanel getcumStatPanel() {
        return cumStatPanel;
    }
}
