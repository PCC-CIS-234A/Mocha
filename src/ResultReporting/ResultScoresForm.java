package ResultReporting;

import SharedLogic.*;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Bobby Puckett
 * @version 5/8/2018
 *
 * Description: Provides a way to communicate with the ResultScores GUI
 */
public class ResultScoresForm {
    private JPanel resultScoresPanel;
    private JPanel testPanel;
    private JList<String> testList;
    private JLabel testLabel;
    private JPanel resultsPanel;
    private JLabel resultsLabel;
    private JTable resultsTable;
    private JButton finishButton;
    private JComboBox userComboBox;

    private Vector<String> testListVector;
    private DefaultTableModel resultsTableModel;
    private Data data;

    public ResultScoresForm() {
        resultScoresPanel.setPreferredSize(new Dimension(600, 400));

        //dimensions for sprint 2
        //resultScoresPanel.setPreferredSize(new Dimension(800,400));
        data = new Data();

        initializeTestList();
        initializeResultsTable();
        initializeUserComboBox();
        initializeFinishButton();
    }

    /**
     * Keeps the constructor short by initializing the testList
     */
    private void initializeTestList() {
        testListVector = new Vector<>();
        testList.setListData(testListVector);

        //disabling test pannel for first sprint
        testPanel.setVisible(false);
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
     * Suppressing the unchecked warning because I'm using a non-primitive data type
     * and adding it to the ComboBox
     */
    @SuppressWarnings("unchecked")
    private void initializeUserComboBox() {

        for (UserAccount u : data.getUsers()) {
            if (u.getMyRole().equals("user")) {
                userComboBox.addItem(u);
            }
        }

        userComboBox.addActionListener(e -> {
            repopulateResultTable((UserAccount) userComboBox.getSelectedItem());
        });

        repopulateResultTable((UserAccount) userComboBox.getSelectedItem());
    }

    /**
     * Keeps the constructor short by initializing the finishButton
     */
    private void initializeFinishButton() {
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Adds a test to the testList
     *
     * @param test the test object to be added
     */
    public void addTest(Test test) {
        testListVector.add(test.getMyName());
    }

    /**
     * Adds a row to the resultsTable
     *
     * @param reportingItem the reportingItem to be added
     */
    public void addReportingItem(ReportingItem reportingItem) {
        Vector<String> rowData = new Vector<>();

        rowData.add(String.valueOf(reportingItem.getScore()));
        rowData.add(reportingItem.getMyName());
        rowData.add(String.valueOf(reportingItem.getWins()));
        rowData.add(String.valueOf(reportingItem.getLosses()));
        rowData.add(String.valueOf(reportingItem.getTies()));

        resultsTableModel.addRow(rowData);
    }

    /**
     * Deletes and reinstanciates the ResultTable
     *
     * @param selectedUser the user to get tests to repopulate the table from
     */
    private void repopulateResultTable(UserAccount selectedUser) {
        int rowCount = resultsTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            resultsTableModel.removeRow(0);
        }
        ArrayList<TestSession> userTestSessions = new ArrayList<>();

        data.getTestSessions().forEach(testSession -> {
            if (testSession.getMyUser().getMyUserName().equals(selectedUser.getMyUserName())) {
                userTestSessions.add(testSession);
            }
        });

        TestSession testSession;

        if (userTestSessions.size() > 0) {
            testSession = userTestSessions.get(0);
            ArrayList<Result> results = new ArrayList<>();

            data.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == testSession.getMySessionID()) {
                    results.add(dataResult);
                }
            });

            ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(results);

            if (reportingItems.size() > 0) {
                for (ReportingItem i : reportingItems) {
                    addReportingItem(i);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, selectedUser.getMyUserName() + " has not taken this reportingItems");
            }
        }

        else {
            if (selectedUser != null) {
                JOptionPane.showMessageDialog(null, selectedUser.getMyUserName() + " has not taken any tests");
            }
            else {
                JOptionPane.showMessageDialog(null, "user has not taken any tests");
            }
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
