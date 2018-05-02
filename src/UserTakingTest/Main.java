package UserTakingTest;

import javax.swing.*;
import java.util.ArrayList;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * Class to start Story3 GUI - User Taking Test
 * @author Liz Goltz
 * @version 4/8/2018
 */

public class Main {
    private static JFrame mFrame = null;
    private static String mItem1 = "cat";
    private static String mItem2 = "dog";
    private static int mWin = 0;

    private static String mCollectionID = "";
    private static String mTestName = "";
    private static String mUserName = "";
    private static String mSessionID = "";
    private static ArrayList items;

    private static void createGUI() {
        mFrame = new JFrame("Compare Items");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  showNewTest();
        showItemCompare(mItem1, mItem2);
    }

    private static void showItemCompare(String mItem1, String mItem2) {
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new GUI(mItem1, mItem2).getRootPanel());
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }

    public static void main(String[] args) {
        invokeLater(new Runnable() {
            public void run() { createGUI(); }
        });
    }
}
