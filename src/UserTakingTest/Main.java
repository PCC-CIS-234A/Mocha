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
    private static String mItem1 = "";
    private static String mItem2 = "";
    private static int mWin = 0;

    private static int mCollectionID = 1;
    private static String mTestName = "";
    private static String mUserName = "";
    private static int mSessionID = 0;

    private static Test mTest;
    private static ArrayList<ItemPair> mTestQuestions;

    private static void createGUI() {
        mTest = new Test(mSessionID, mUserName, mCollectionID);
        mTestQuestions = mTest.getTestQuestions();

        mFrame = new JFrame("Compare Items");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  showNewTest()
        showItemCompare(mTestQuestions);

       /* for (ItemPair question : mTestQuestions) {
            showItemCompare(question.getItem1(), question.getItem2());
        }*/
    }

/*    //?should this return an int winCode value?
    private static void showItemCompare(String mItem1, String mItem2) {
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new GUI(mItem1, mItem2).getRootPanel());
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }*/

    private static void showItemCompare(ArrayList<ItemPair> testQuestions) {
        mTestQuestions = testQuestions;
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new GUI(mTest.getTestQuestions()).getRootPanel());
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
