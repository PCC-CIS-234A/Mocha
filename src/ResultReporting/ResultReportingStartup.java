package ResultReporting;

    // this class was created so that Git would add the folder path. Delete this
    // and create one of your own.

import javax.swing.*;
import java.util.ArrayList;
import SharedLogic.Item;
import SharedLogic.UserAccount;


public class ResultReportingStartup {

    public static void main(String[] args) {
        //this line demonstrates the database
        ArrayList<Item> items = Item.retrieveItems();

        items.forEach(item -> System.out.println(item.getMyName()));

        //this line demonstrates the GUI
        SwingUtilities.invokeLater(ResultReportingStartup::createAndShowGui);
    }

    /**
     * creates and shows the GUI
     */
    public static void createAndShowGui() {
        //results pane
        JFrame frame  = new JFrame("Result Scores");
        ResultScoresForm resultScoresForm = new ResultScoresForm(UserAccount.retrieveAllUsers());

        frame.getContentPane().add(resultScoresForm.getResultScoresPanel());
        frame.setVisible(true);
        frame.pack();
    }
}
