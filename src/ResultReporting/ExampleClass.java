package ResultReporting;

    // this class was created so that Git would add the folder path. Delete this
    // and create one of your own.

import javax.swing.*;

public class ExampleClass {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());

    }

    public static void createAndShowGui() {
        //results pane
        JFrame frame  = new JFrame("Result Scores");
        frame.getContentPane().add(new ResultScoresForm().getResultScoresPanel());
        frame.setVisible(true);
        frame.pack();



        //users pane
//        JFrame frame2 = new JFrame("User List");
//        frame2.getContentPane().add(new SelectUserForm().getUserListPanel());
//        frame2.setVisible(true);
//        frame2.pack();
    }
}
