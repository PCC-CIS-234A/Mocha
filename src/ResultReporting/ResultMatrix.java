package ResultReporting;

import SharedLogic.Item;
import SharedLogic.TestSession;
import SharedLogic.Result;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

/**
 * @author Rebecca Kennedy
 * @version 5/30/2018.
 * Shows the results as a matrix.
 */
public class ResultMatrix {
    private JPanel resultMatrixPanel;
    private JScrollPane matrixScrollPane;
    private JTable matrixTable;
    private JFrame frame;
    private Container content;
    private ResultScoresForm rsf;

    private DefaultTableModel matrixTableModel;
    private String testName;
    private List<ReportingItem> items;
    private ArrayList<Result> results;

    public ResultMatrix(JFrame frame, ResultScoresForm resScoForm) {

        this.frame = frame;
        rsf = resScoForm;

        getData();
        sortItems();

        frame.setTitle("Detailed Results - " + testName);
        content = frame.getContentPane();

        setupResultMatrixPanel();

    } //end constructor

    /**
     * Gets the Winner of the particular Result
     * @param rs
     * @return
     */
    private String getWinner(Result rs) {
        int code = rs.getMyResultCode();
        if (code == 1) {
            String name = rs.getMyItemOne().getMyName();
            return name;
        } else if (code == 2) {
            String name = rs.getMyItemTwo().getMyName();
            return name;
        }  else {
            String name = "tie";
            return name;
        }
    }

    /**
     * Creates the columns which will be used as headings
     * @return
     */
    private String[] createColumns() {
        int horz = items.size() + 1;
        String columns[] = new String[horz];

        columns[0] = " ";
        for(int i = 1; i < horz; i++) {
            ReportingItem item = items.get(i - 1);
            columns[i] = item.getMyName();
        }
        return columns;
    }

    /**
     * Creates the rows which will be used for row headings and
     * also the rows which will contain the data
     * @return
     */
    private String[][] createRows() {
        String col[] = createColumns();
        int vert = items.size();
        int horz = items.size() + 1;

        String[][] rows = new String[vert][horz];

        //loops it through the row headings
        for(int i = 0; i < vert; i++) {
            ReportingItem item = items.get(i);
            rows[i][0] = item.getMyName();
            //loops it through the each cell
            for (int j = 1; j < horz; j++) {
                Result result = getResult(rows[i][0], col[j]);

                String winner;
                if(result != null) {
                    winner = getWinner(result);
                } else {
                    winner = " ";
                }

                rows[i][j] = winner;
            }
        }
        return rows;
    }

    /**
     * Gets the result that will match the column header and row header for that particular cell
     * @param columnHeader
     * @param rowHeader
     * @return
     */
    private Result getResult(String columnHeader, String rowHeader) {

        for(Result rs : results) {
            Item item1 = rs.getMyItemOne();
            Item item2 = rs.getMyItemTwo();

            //Checks that the column matches item1 and that the row matches item2
            boolean match1 = columnHeader.equals(item1.getMyName());
            boolean match2 = rowHeader.equals(item2.getMyName());
            //Now the reverse matches. Checks that the column matches item2 and that the row matches item1
            boolean match3 = columnHeader.equals(item2.getMyName());
            boolean match4 = rowHeader.equals(item1.getMyName());

            if ((match1 && match2) || (match3 && match4)) {
                return rs;
            }
        }
        return null;

    }

    /**
     * Sets up the resultMatrixPanel. That includes the setup
     * for the matrixTable.
     */
    private void setupResultMatrixPanel() {
        //Setup table's row and column values
        String rows[][] = createRows();
        String columns[] = createColumns();
        matrixTableModel = new DefaultTableModel(rows ,columns);
        matrixTable.setModel(matrixTableModel);

        //Setup how rows and columns look
        setColFeatures();
        setRowFeatures();

        matrixScrollPane.setViewportView(matrixTable);
        content.add(resultMatrixPanel, BorderLayout.CENTER);

        matrixTable.setPreferredSize(new Dimension(getTableWidth(), getTableHeight()));
        matrixTable.setPreferredScrollableViewportSize(matrixTable.getPreferredSize());
    }

    /**
     * Sets up how the columns look
     */
    private void setColFeatures() {
        JTableHeader header = matrixTable.getTableHeader();
        header.setPreferredSize(new Dimension(getTableWidth(), 50));
        header.setBackground(new Color(49, 98, 196));
        header.setForeground(Color.white);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    /**
     * Sets up how the rows look
     */
    private void setRowFeatures() {
        matrixTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                //Beautifies the row header
                component.setBackground(column == 0 ? new Color(72, 127, 75) : new Color(255,250,250));
                component.setFont(column == 0 ? new Font("SansSerif", Font.BOLD, 12) : new Font("SansSerif", Font.PLAIN, 12));

                MatteBorder whiteBorder = new MatteBorder(1, 1, 0, 0, Color.white);
                ((DefaultTableCellRenderer) component).setBorder(whiteBorder);

                //Beautifies the rows
                int align = DefaultTableCellRenderer.CENTER;
                ((DefaultTableCellRenderer)component).setHorizontalAlignment(align);

                String rows[][] = createRows();
                String cols[] = createColumns();

                //When the row's value is the same value as the column header,
                // turn the value blue which is the same color as the column header
                if (value == cols[column]) {
                    component.setForeground(new Color(49, 98, 196));
                }
                //When the row (which is not a row header) has a value the same as the row header,
                // turn it green which is the same color as the row header
                else if (value == rows[row][0] && column > 0) {
                    component.setForeground(new Color(72, 127, 75));
                }
                //When it is the row header, turn it white
                else if (column == 0) {
                    component.setForeground(Color.white);
                }
                //Everything else, turn it black
                else {
                    component.setForeground(new Color(0, 0, 0));
                }

                return component;
            }
        });
    }

    /**
     * Gets the setup height of the table
     * @return
     */
    private int getTableHeight() {
        //Set each row to be 50 pixels high
        matrixTable.setRowHeight(50);

        int numRows = matrixTable.getRowCount();
        int rowHeight = matrixTable.getRowHeight();

        int tableHeight = numRows * rowHeight;
        return tableHeight;
    }

    /**
     * Gets the setup width of the table
     * @return
     */
    private int getTableWidth() {

        int numRows = matrixTable.getColumnCount();

        for(int i = 0; i < numRows; i++)
            matrixTable.getColumnModel().getColumn(i).setMinWidth(8);

        int rowCharWidth = itemLength();
        // 10 is hardcoded pixels
        int rowWidth = rowCharWidth * 10;

        int tableWidth = numRows * rowWidth;
        return tableWidth;
    }

    /**
     * Gets the length of the item with the longest name
     * @return
     */
    private int itemLength() {
        int maxItemCharLength = matrixTable.getColumnModel().getColumn(0).getMinWidth();
        for (ReportingItem item : items) {
            int charLength = item.getMyName().length();
            if(charLength > maxItemCharLength) {
                maxItemCharLength = charLength;
            }
        }
        return maxItemCharLength;
    }

    /**
     * Sorts the items by their itemID
     */
    private void sortItems() {
        Collections.sort(items, new Comparator<ReportingItem>() {
            @Override
            public int compare(ReportingItem o1, ReportingItem o2) {
                if (o1.getMyItemID() > o2.getMyItemID()) {
                    return 1;
                } else if (o1.getMyItemID() < o2.getMyItemID()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * Gets the data we need to work with from the ResultScoresForm
     */
    private void getData() {
        //Get TestSession to get other values
        TestSession ts = rsf.getTestSession();

        //Get the Test ID and Test Name
        if(ts.getMyTest() != null) {
            testName = ts.getMyTest().getMyName();
        }

        //Get ReportingItems which are a child of SharedLogic.Item and store them in a List
        items = rsf.getReportingItems();

        //Get Results
        results = rsf.getResults();
    }

    /**
     * Getter for resultMatrixPanel
     *
     * @return the resultMatrixPanel
     */
    public JPanel getResultMatrixPanel() {
        return resultMatrixPanel;
    }

}
