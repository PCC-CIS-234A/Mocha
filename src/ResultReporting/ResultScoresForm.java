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
 * @version 4/25/2018
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
        resultScoresPanel.setPreferredSize(new Dimension(600,400));

        //dimensions for sprint 2
        //resultScoresPanel.setPreferredSize(new Dimension(800,400));

        this.users = users;

        initializeTestList();
        initializeResultsTable();
        initializeUserComboBox();
    }

    /**
     * This method adds a test to the testList
     * @param testName the name of the test to be added
     */
    public void addTest(String testName) {
        testListVector.add(testName);
    }

    /**
     * This method adds a row to the resultsTable
     * @param item the item to be added
     */
    public void addResultItem(Item item) {
        resultsTableModel.addRow(item.createRowData());
    }

    private void repopulateResultTable(User selectedItem) {
        int rowcount = resultsTableModel.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            resultsTableModel.removeRow(0);
        }

        Test test = selectedItem.getTests().get(0);

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
     * keeps the constructor short by inistializing the resultsTable
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
     * Getter for resultScoresPanel
     * @return the resultScoresPanel
     */
    public JPanel getResultScoresPanel() {
        return resultScoresPanel;
    }
}
