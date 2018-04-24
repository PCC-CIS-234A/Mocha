package ResultReporting;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ResultScoresForm {
    private JPanel resultScoresPanel;
    private JPanel testPanel;
    private JList<String> testList;
    private JLabel testLabel;
    private JPanel resultsPanel;
    private JLabel resultsLabel;
    private JTable resultsTable;
    private JButton finishButton;

    private Vector<String> testListVector;
    private TableModel resultsTableModel;

    public ResultScoresForm()
    {
        resultScoresPanel.setPreferredSize(new Dimension(800,400));

        initializeTestList();
        initializeResultsTable();

        addTest("thing!");
    }

    public void addTest(String testName)
    {
        testListVector.add(testName);
    }

    private void initializeTestList()
    {
        testListVector = new Vector<>();
        testList.setListData(testListVector);
    }
    private void initializeResultsTable()
    {
//        TableColumn tc = new TableColumn();
//        tc.setHeaderValue("Item Name");
//
//        resultsTable.addColumn(tc);

        Object rowdata[][] = {{"1 ","2"},
                            {"3","4"}};


        Object[] columnNames = {"Item Name","Score"};


        resultsTable.setModel(new DefaultTableModel(rowdata,columnNames));

        rowdata.
    }

    public JPanel getResultScoresPanel() {
        return resultScoresPanel;
    }
}
