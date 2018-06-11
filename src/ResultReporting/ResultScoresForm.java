package ResultReporting;

import SharedLogic.*;
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
    private ResultReportingDatabase resultReportingDatabase;
    private JFrame frame;

    public ResultScoresForm(JFrame frame) {
        resultScoresPanel.setPreferredSize(new Dimension(600, 400));
        this.frame = frame;

        resultReportingDatabase = new ResultReportingDatabase();

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

        ArrayList<Result> results = new ArrayList<>();
        if (testSession != null) {
            int id = testSession.getMySessionID();

            resultReportingDatabase.getResults().forEach(dataResult -> {
                if (dataResult.getMySessionID() == id) {
                    results.add(dataResult);
                }
            });

            ArrayList<ResultReportingItem> resultReportingItems = ResultReportingItem.buildReportingItems(results);

            if (resultReportingItems.size() > 0) {
                for (ResultReportingItem i : resultReportingItems) {
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
