package AdminSetup.ImageLibrary;

import Database.ItemDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author: Anh Nguyen
 * @version: 5/20/2018
 *
 * Description: This is The GUI that allows admin to insert/change/remove image of an item
 */
public class EditImageForm {
    private JButton finishButton;
    private JPanel rootPanel;
    private JFrame myFrame;
    private DroppablePicturePanel droppablePanel;
    private ItemDB itDB;


    public EditImageForm(String itemName, JFrame myFrame) {
        myFrame.setTitle("Edit Image");
        rootPanel.setPreferredSize(new Dimension(400, 300));
        droppablePanel.setImage(null);

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                itDB = new ItemDB();
                BufferedImage image = droppablePanel.getImage();
                boolean message = itDB.updateItemByName(itemName,image);
                rootPanel.setVisible(false);
                myFrame.dispose();
                JOptionPane.showMessageDialog(null, "Update Succeeded!");

            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
