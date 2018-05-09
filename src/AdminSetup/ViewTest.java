package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Becky
 * @version 5/7/2018.
 */
public class ViewTest {
    private JPanel rootPanel;
    private JScrollPane itemScrollPane;
    private JList itemList;
    private JButton finishButton;
    private JButton cancelButton;
    private ArrayList<Item> items;
    private DefaultListModel listModel;
    private int myTestID;

 //   public ViewTest() {
     public ViewTest(int testID) {
         myTestID = testID;

        rootPanel.setPreferredSize(new Dimension(300, 200));

        listModel = new DefaultListModel();
        itemList.setModel(listModel);

        // ArrayList<Item> items = Item.getTestItems();
        items = Item.getTestItems(myTestID);

        //Add all items to the list
        for(Item item: items) {
            //System.out.println(item.getTestID() + ": " + item.getName());
            //       item.setTableAction(Item.TableAction.KEEP);
            listModel.addElement(item.getName());
            //       System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
            System.out.println(item.getTestID() + ": " + item.getName());
        }
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetupTest.showChooseActionOnTest();
            }
        });
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
