package UserTakingTest;

import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Class to start User Taking Test GUI
 * @author Liz Goltz
 * @version 4/8/2018
 */

public class Main {
    private static JFrame mFrame = null;

    private static void createGUI() {

        mFrame = new JFrame("Compare Items");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  showNewTest()
        showItemCompare();
    }

    private static void showItemCompare() {
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new GUI().getRootPanel());
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }

    public static void main(String[] args) {
        invokeLater(new Runnable() {
            public void run() { createGUI(); }
        });
    }

    public static void finished() {
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new TestOver().getRootPanel());
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }
}
