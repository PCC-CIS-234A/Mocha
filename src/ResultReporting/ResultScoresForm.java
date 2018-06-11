package ResultReporting;

import SharedLogic.*;
import UserLogin.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Bobby Puckett and Rebecca Kennedy (RK made small changes)
 * @version 5/29/2018
 * <p>
 * Description: Provides a way to communicate with the ResultScores GUI
 */
public class ResultScoresForm {
    private JPanel resultScoresPanel;
    private JPanel resultsPanel;
    private JLabel resultsLabel;
    private JTable resultsTable;
    private JButton finishButton;
    private JComboBox userComboBox;
    private JComboBox testComboBox;
    private JButton detailedResultsButton; //RK added 5/30/18
    private JButton statisticsButton; //RK added 5/30/18

    private DefaultTableModel resultsTableModel;
    private ResultReportingDatabase resultReportingDatabase;
    private JFrame frame;
    private ArrayList<ResultReportingItem> reportingItems; // RK added 5/31/18
    private ArrayList<Result> results; // RK added 5/31/18

    public ResultScoresForm(JFrame frame) {
        /* RK edit 5/30/18 Changed width to 700. Kept Bobby's original line and have new line. */
      //  resultScoresPanel.setPreferredSize(new Dimension(600, 400));
        resultScoresPanel.setPreferredSize(new Dimension(700, 400));
        this.frame = frame;

        resultReportingDatabase = new ResultReportingDatabase();

        initializeResultsTable();
        initializeUserComboBox();
        initializeTestComboBox();
        initializeFinishButton();
        //RK edit 5/30/18 added detailedResultsButton and statisticsButton
        initializeDetailedResultsButton();
        initializeStatisticsButton();
    }

    /**
     * Keeps the constructor short by initializing the resultsTable
     */
    private void initializeResultsTable() {
        resultsTableModel = new DefaultTableModel();

        resultsTableModel.addColumn("Score");
        resultsTableModel.addColumn("Item");
        resultsTableModel.addColumn("Wins");
        resultsTableModel.addColumn("Losses");
        resultsTableModel.addColumn("Ties");

        resultsTable.setModel(resultsTableModel);
    }

    /**
     * Keeps the constructor short by initializing the userComboBox.
     * <p>
     * Suppressing the unchecked warning because I'm using a non-primitive resultReportingDatabase type
     * and adding it to the ComboBox
     */
    @SuppressWarnings("unchecked")
    private void initializeUserComboBox() {
        for (UserAccount u : resultReportingDatabase.getUsers()) {
            if (u.getMyRole().equals("user")) {
                for (TestSession session : resultReportingDatabase.getTestSessions()) {
                    if (session.getMyUser().getMyUserID() == u.getMyUserID()) {
                        userComboBox.addItem(u);
                        break;
                    }
                }
            }
        }

        userComboBox.addActionListener(e -> repopulateTestComboBox((UserAccount) userComboBox.getSelectedItem()));
    }

    /**
     * Keeps the constructor short by initializing the testComboBox.
     * <p>
     * Suppressing the unchecked warning because I'm using a non-primitive resultReportingDatabase type
     * and adding it to the ComboBox
     */
    @SuppressWarnings("unchecked")
    private void initializeTestComboBox() {
        testComboBox.addActionListener(e -> repopulateResultTable((TestSession) testComboBox.getSelectedItem()));

        repopulateTestComboBox((UserAccount) userComboBox.getSelectedItem());
    }

    /**
     * Keeps the constructor short by initializing the finishButton
     */
    private void initializeFinishButton() {
        finishButton.addActionListener(e -> {
            frame.dispose();
            Main.createGUI();
        });
    }

    /**
     * RK: Adding this on 5/30/18
     *
     * Keeps the constructor short by initializing the detailedResultsButton
     * Sets up the ResultMatrix GUI
     */
    private void initializeDetailedResultsButton() {

        detailedResultsButton.addActionListener(e -> {
            JFrame detResFrame = new JFrame("Detailed Results");
            ResultMatrix resultMatrix = new ResultMatrix(detResFrame, this);
            detResFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            detResFrame.getContentPane().add(resultMatrix.getResultMatrixPanel());
            detResFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            detResFrame.setVisible(true);
            detResFrame.pack();
        });
    }

    /**
     * RK: Adding this on 5/30/18
     *
     * Keeps the constructor short by initializing the statisticsButton
     * Sets up the CumulativeStatistics GUI
     */
    private void initializeStatisticsButton() {
        statisticsButton.addActionListener(e -> {
            JFrame statFrame = new JFrame("Cumulative Statistics");
            CumulativeStatistics cumStat = new CumulativeStatistics(statFrame, this);
            statFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            statFrame.getContentPane().add(cumStat.getcumStatPanel());
            statFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            statFrame.setVisible(true);
            statFrame.pack();
        });
    }

    /**
     * Adds a row to the resultsTable
     *
     * @param resultReportingItem the resultReportingItem to be added
     */
    private void addReportingItem(ResultReportingItem resultReportingItem) {
        Vector<String> rowData = new Vector<>();

        rowData.add(String.valueOf(resultReportingItem.getScore()));
        rowData.add(resultReportingItem.getMyName());
        rowData.add(String.valueOf(resultReportingItem.getWins()));
        rowData.add(String.valueOf(resultReportingItem.getLosses()));
        rowData.add(String.valueOf(resultReportingItem.getTies()));

        resultsTableModel.addRow(rowData);
    }

    /**
     * Deletes and reinstanciates the ResultTable
     *
     * @param testSession the testSession to fill the result table with
     */
    private void repopulateResultTable(TestSession testSession) {
        UserAccount selectedUser = (UserAccount) userComboBox.getSelectedItem();
        int rowCount = resultsTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            resultsTableModel.removeRow(0);
        }

        /* RK modified 5/31/18. Kept both lines. Changed results to be a private field instead of a local variable */
      //  ArrayList<Result> results = new ArrayList<>();
        results = new ArrayList<>();
        if (testSession != null) {
            int id = testSession.getMySessionID();

            resultReportingDatabase.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == id) {
                    results.add(dataResult);
                }
            });

            /* RK modified 5/31/18. Kept both lines. Changed reportingItems to be a private field instead of a local variable */
         //   ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(results);
            reportingItems = ResultReportingItem.buildReportingItems(results);

            if (reportingItems.size() > 0) {
                for (ResultReportingItem i : reportingItems) {
                    addReportingItem(i);
                }
            } else {
                JOptionPane.showMessageDialog(null, selectedUser.getMyUserName() + " has taken this test, but the results weren't stored.");
            }
        }
    }

    /**
     * Deletes and reinstanciates the testComboBox
     *
     * @param user specifies which tests will be displayed
     */
    private void repopulateTestComboBox(UserAccount user) {
        testComboBox.removeAllItems();

        resultReportingDatabase.getTestSessions().forEach(testSession -> {
            if (testSession.getMyUser().getMyUserID() == user.getMyUserID()) {
                testComboBox.addItem(testSession);
            }
        });

        if (testComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, user.getMyUserName() + " has not taken any tests.");
            enableDetailedResultsButton(0); //  5/31/18 RK added call to enableDetailedResultsButton()
        } else {  //  5/31/18 RK added else part of if statement
            enableDetailedResultsButton(1);
        }
    }

    /**
     * RK added this method 5/31/18
     * Getter for selected TestSession
     *
     * @return the ts
     */
    public TestSession getTestSession() {
        TestSession ts = (TestSession) testComboBox.getSelectedItem();
        return ts;
    }

    /**
     * RK added this method 5/31/18
     * Getter for selected TestSession
     *
     * @return the ts
     */
    public ArrayList<ResultReportingItem> getReportingItems() {
        return reportingItems;
    }

    /**
     * RK added this method 5/31/18
     * Getter for results
     *
     * @return the results
     */
    public ArrayList<Result> getResults() {
        return results;
    }

    /**
     * 5/31/18 Rebecca Kennedy added this method
     *
     * Enables or disables the detailedResultsButton
     * @param i
     */
    public void enableDetailedResultsButton(int i) {
        if(i == 0) {
            detailedResultsButton.setEnabled(false);
        } else if (i == 1) {
            detailedResultsButton.setEnabled(true);
        }

    }

    /**
     * Getter for resultScoresPanel
     *
     * @return the resultScoresPanel
     */
    public JPanel getResultScoresPanel() {
        return resultScoresPanel;
    }
}
