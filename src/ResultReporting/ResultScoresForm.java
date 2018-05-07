package ResultReporting;

import SharedLogic.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Bobby Puckett
 * @version 5/1/2018
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
    private ArrayList<UserAccount> users;

    public ResultScoresForm(ArrayList<UserAccount> users) {
        resultScoresPanel.setPreferredSize(new Dimension(600, 400));

        //dimensions for sprint 2
        //resultScoresPanel.setPreferredSize(new Dimension(800,400));

        this.users = users;

        initializeTestList();
        initializeResultsTable();
        initializeUserComboBox();

        initializeFinishButton();
    }


    /**
     * This method adds a test to the testList
     *
     * @param test the test object to be added
     */
    public void addTest(Test test) {
        testListVector.add(test.getMyName());
    }

    /**
     * This method adds a row to the resultsTable
     *
     * @param reportingItem the reportingItem to be added
     */
    public void addReportingItem(ReportingItem reportingItem) {
        Vector<String> rowData = new Vector<>();

        rowData.add(String.valueOf(reportingItem.getMyItemID()));
        rowData.add(reportingItem.getMyName());
        rowData.add(String.valueOf(reportingItem.getWins()));
        rowData.add(String.valueOf(reportingItem.getLosses()));
        rowData.add(String.valueOf(reportingItem.getTies()));

        resultsTableModel.addRow(rowData);
    }

    /**
     * deletes and reinstanciates the ResultTable
     *
     * @param selectedUser the user to get tests to repopulate the table from
     */
    private void repopulateResultTable(UserAccount selectedUser) {
        int rowCount = resultsTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            resultsTableModel.removeRow(0);
        }

        ArrayList<TestSession> userTestSessions =TestSession.retrieveUserTestSessions(selectedUser);
        TestSession testSession;
        if (userTestSessions.size() > 0) {
            testSession = userTestSessions.get(0);
            ArrayList<ReportingItem> test = ReportingItem.retrieveReportingItemsOnTest(testSession.getMyTest().getMyTestID());

            for (ReportingItem i : test) {
                addReportingItem(i);
            }
        }
        else {
            System.out.println("The user has no test sessions");
        }

    }



    /**
     * keeps the constructor short by initializing the testList
     */
    private void initializeTestList() {
        testListVector = new Vector<>();
        //@todo make sure this still works
        testList.setListData(testListVector);

        //disabling test pannel for first sprint
        testPanel.setVisible(false);
    }

    /**
     * keeps the constructor short by initializing the resultsTable
     */
    private void initializeResultsTable() {
        resultsTableModel = new DefaultTableModel();

        resultsTableModel.addColumn("User Name");
        resultsTableModel.addColumn("Item");
        resultsTableModel.addColumn("Wins");
        resultsTableModel.addColumn("Losses");
        resultsTableModel.addColumn("Ties");

        resultsTable.setModel(resultsTableModel);
    }

    /**
     * keeps the constructor short by initializing the userComboBox.
     * <p>
     * Suppressing the unchecked warning because I'm using a non-primitive data type
     * and adding it to the ComboBox
     */
    @SuppressWarnings("unchecked")
    private void initializeUserComboBox() {

        for (UserAccount u : users) {
            userComboBox.addItem(u);
        }

        userComboBox.addActionListener(e -> {
            repopulateResultTable((UserAccount) userComboBox.getSelectedItem());
        });

        repopulateResultTable((UserAccount) userComboBox.getSelectedItem());
    }

    /**
     * keeps the constructor short by initializing the finishButton
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
     * Getter for resultScoresPanel
     *
     * @return the resultScoresPanel
     */
    public JPanel getResultScoresPanel() {
        return resultScoresPanel;
    }
}
