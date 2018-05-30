package ResultReporting;

import javax.swing.*;

/**
 * Creates the ResultReporting GUI when main() is called
 *
 * @author  Bobby Puckett
 * @version 5.29.2018
 */
public class ResultReportingStartup {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResultReportingStartup::createAndShowGui);
    }

    /**
     * Creates and shows the GUI
     */
    public static void createAndShowGui() {
        JFrame frame = new JFrame("Result Scores");
        ResultScoresForm resultScoresForm = new ResultScoresForm(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(resultScoresForm.getResultScoresPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
}
