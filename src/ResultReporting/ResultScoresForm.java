package ResultReporting;

import SharedLogic.Result;
import SharedLogic.TestSession;
import SharedLogic.UserAccount;
import UserLogin.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Bobby Puckett
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

    private DefaultTableModel resultsTableModel;
    private Data data;
    private JFrame frame;

    public ResultScoresForm(JFrame frame) {
        resultScoresPanel.setPreferredSize(new Dimension(600, 400));
        this.frame = frame;

        data = new Data();

        initializeResultsTable();
        initializeUserComboBox();
        initializeTestComboBox();
        initializeFinishButton();
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
            repopulateTestComboBox((UserAccount) userComboBox.getSelectedItem());
        });
    }

    /**
     * Keeps the constructor short by initializing the testComboBox.
     * <p>
     * Suppressing the unchecked warning because I'm using a non-primitive data type
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
     * Adds a row to the resultsTable
     *
     * @param reportingItem the reportingItem to be added
     */
    private void addReportingItem(ReportingItem reportingItem) {
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
     * @param testSession the testSession to fill the result table with
     */
    private void repopulateResultTable(TestSession testSession) {
        UserAccount selectedUser = (UserAccount) userComboBox.getSelectedItem();
        int rowCount = resultsTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            resultsTableModel.removeRow(0);
        }

        ArrayList<Result> results = new ArrayList<>();
        if (testSession != null) {
            int id = testSession.getMySessionID();

            data.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == id) {
                    results.add(dataResult);
                }
            });

            ArrayList<ReportingItem> reportingItems = ReportingItem.buildReportingItems(results);

            if (reportingItems.size() > 0) {
                for (ReportingItem i : reportingItems) {
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

        data.getTestSessions().forEach(testSession -> {
            if (testSession.getMyUser().getMyUserID() == user.getMyUserID()) {
                testComboBox.addItem(testSession);
            }
        });

        if (testComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, user.getMyUserName() + " has not taken any tests.");
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
