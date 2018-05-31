package ResultReporting;

import SharedLogic.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/30/2018.
 * Shows the results as a matrix. This currently only displays the items in a System.out.println().
 */
public class ResultMatrix {
    private JPanel resultMatrixPanel;
    private JFrame frame;
    private ResultScoresForm rsf;

    public ResultMatrix(JFrame frame, ResultScoresForm resScoForm) {
        resultMatrixPanel.setPreferredSize(new Dimension(500, 400));
        this.frame = frame;

        rsf = resScoForm;
        printItems();
    } //end constructor

    private void printItems() {
        System.out.println("Items:");

        int id = rsf.getTestID();


        ArrayList<String> itemNames = Item.readItems(id);
        for (String item : itemNames) {
            System.out.println(item);
        }


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
