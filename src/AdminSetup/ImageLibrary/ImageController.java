package AdminSetup.ImageLibrary;

import javax.swing.*;

/**
 * @author: Anh Nguyen
 * @version: 5/30/2018
 *
 * Description: This is the controller class for Image Library
 */

public class ImageController {

    private static JFrame mFrame = null;

    public static void createGUI() {
        mFrame = new JFrame();

    }
    public static void showImage(String itemName) {
        mFrame.getContentPane().removeAll();
        mFrame.getContentPane().add(new EditImageForm(itemName,mFrame).getRootPanel());
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }
}
