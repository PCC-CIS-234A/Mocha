package AdminSetup;

import javax.swing.*;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018
 * Description: This is a temporary Main class to start up the SetupTest GUI.
 */
public class Main {
    public static void createAndShowSetupTest() {
        JFrame frame = new JFrame("Setup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SetupTest().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowSetupTest());
    }
}
