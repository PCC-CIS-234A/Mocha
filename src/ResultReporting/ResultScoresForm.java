package ResultReporting;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
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
    private ArrayList<User> users;

    public ResultScoresForm(ArrayList<User> users) {
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
     * @param testName the name of the test to be added
     */
    public void addTest(String testName) {
        testListVector.add(testName);
    }

    /**
     * This method adds a row to the resultsTable
     *
     * @param item the item to be added
     */
    public void addResultItem(Item item) {
        resultsTableModel.addRow(item.createRowData());
    }

    /**
     * deletes and reinstantiates the ResultTable
     *
     * @param selectedUser the user to get tests to repopulate the table from
     */
    private void repopulateResultTable(User selectedUser) {
        int rowCount = resultsTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            resultsTableModel.removeRow(0);
        }

        Test test = selectedUser.getTests().get(0);

        for (Item i : test.getItems()) {
            addResultItem(i);
        }
    }

    /**
     * keeps the constructor short by initializing the testList
     */
    private void initializeTestList() {
        testListVector = new Vector<>();
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
     *
     * Suppressing the unchecked warning because I'm using a non-primitive data type
     * and adding it to the ComboBox
     */
    @SuppressWarnings("unchecked")
    private void initializeUserComboBox() {

        for (User u : users) {
            userComboBox.addItem(u);
        }

        userComboBox.addActionListener(e -> {
            repopulateResultTable((User) userComboBox.getSelectedItem());
        });

        repopulateResultTable((User) userComboBox.getSelectedItem());

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
