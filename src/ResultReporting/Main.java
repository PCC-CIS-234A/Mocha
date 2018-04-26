package ResultReporting;

    // this class was created so that Git would add the folder path. Delete this
    // and create one of your own.

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }

    public static void createAndShowGui() {
        //results pane
        JFrame frame  = new JFrame("Result Scores");
        ResultScoresForm resultScoresForm = new ResultScoresForm(new DatabaseConnectionResults().getUsers());


        //testing the form
//        resultScoresForm.addTest("thing!");
//        resultScoresForm.addTest("Other!");
//
//        Item testItem = new Item("Green", 12, 0, 0);
//        resultScoresForm.addResultItem(testItem);
        //testing the form END

        frame.getContentPane().add(resultScoresForm.getResultScoresPanel());
        frame.setVisible(true);
        frame.pack();
    }
}
