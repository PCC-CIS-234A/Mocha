package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 5/7/2018.
 * Description: This is the GUI that allows the admin to view the test's items. No changes
 * can be made to the database from this GUI.
 */
public class ViewTest {
    private JPanel rootPanel;
    private JScrollPane itemScrollPane;
    private JList itemList;
    private JButton finishButton;
    private ArrayList<Item> items;
    private DefaultListModel listModel;
    private int myTestID;

     public ViewTest(int testID) {
         myTestID = testID;

        rootPanel.setPreferredSize(new Dimension(300, 200));

        listModel = new DefaultListModel();
        itemList.setModel(listModel);

        items = Item.getTestItems(myTestID);

        //Add all items to the list
        for(Item item: items) {
            listModel.addElement(item.getName());
        }

         finishButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 SetupTest.showChooseActionOnTest();
             }
         });
     }

    public int getTestID() {
         return myTestID;
    }

    public void setTestID(int testID) {
         myTestID = testID;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
