package AdminSetup;

import javax.swing.*;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018
 * Description: This is the controller class for the AdminSetup GUIs.
 */

public class SetupTest {
    private static JFrame frame = null;

    public SetupTest() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showChooseActionOnTest();
    }

    public static void showChooseActionOnTest() {
        frame.getContentPane().removeAll();
        frame.setTitle("Admin Setup");
        frame.getContentPane().add(new ChooseActionOnTest().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void showCreateTest() {
        frame.getContentPane().removeAll();
        frame.setTitle("Create Test");
        frame.getContentPane().add(new CreateTest().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void showEditTest(int testID) {
        frame.getContentPane().removeAll();
        frame.setTitle("Edit Test");
        frame.getContentPane().add(new EditTest(testID).getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void showViewTest(int testID) {
        frame.getContentPane().removeAll();
        frame.setTitle("View Test");
        frame.getContentPane().add(new ViewTest(testID).getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
