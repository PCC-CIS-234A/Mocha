package ResultReporting;

    // this class was created so that Git would add the folder path. Delete this
    // and create one of your own.

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //this line demonstrates the database
        Database database = new Database();

        //this line demonstrates the GUI
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    /**
     * creates and shows the GUI
     */
    public static void createAndShowGui() {
        //results pane
        JFrame frame  = new JFrame("Result Scores");
        ResultScoresForm resultScoresForm = new ResultScoresForm(new DatabaseConnectionResults().getUsers());

        frame.getContentPane().add(resultScoresForm.getResultScoresPanel());
        frame.setVisible(true);
        frame.pack();
    }
}
