package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import AdminSetup.ImageLibrary.DroppablePicturePanel;
import AdminSetup.ImageLibrary.ImageController;
import SharedLogic.Item;

/**
 * @author Rebecca Kennedy
 * @version 4/25/2018.
 * Description: This is the GUI that allows the admin to edit the items in the database.
 *
 * Modification
 * @author: Anh Nguyen
 * @version: 5/30/2018
 * Description: add addMouseListener()- perform action when user click on an item on "Edit Test" GUI
 *
 */
public class EditTest {
    private JPanel rootPanel;
    private JTextField itemTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JScrollPane itemScrollPane;
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton;
    private JPanel actionButtonPanel;
    private JLabel uniqueItemJLabel;
    private AdminSetup.ImageLibrary.PicturePanel PicturePanel;
    private DroppablePicturePanel showImagePanel;
    private ArrayList<AdminSetupItem> items;
    private DefaultListModel listModel;
    private int myTestID;

    public EditTest(int testID) {

        myTestID = testID;

        rootPanel.setPreferredSize(new Dimension(600, 400));

        actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        actionButtonPanel.setPreferredSize(new Dimension(250, 400));

        listModel = new DefaultListModel();

        setInitialKeep();

        enableFinishButton();

        itemList.setModel(listModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActionPerformed(e);
            }
        });

        itemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActionPerformed(e);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButtonActionPerformed(e);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetupTest.showChooseActionOnTest();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishButtonActionPerformed(e);
            }
        });

        // End constructor

        /**
         * Modify by Anh Nguyen
         * Perform action when user click on an item
         */
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1){
                    Object obj = itemList.getSelectedValue();
                    String itemName = obj.toString();
                    Item item = Item.retrieveItemOnName(itemName);
                    PicturePanel.setImage(item.getMyImage());
                }
                if (e.getClickCount() == 2) {
                    Object obj = itemList.getSelectedValue();
                    String itemName = obj.toString();


                    ImageController.createGUI();
                    ImageController.showImage(itemName);

                }
                super.mouseClicked(e);
            }
        });
    }

    /**
     * Performs the actions that will happen when the OK button is pushed
     */
    private void addActionPerformed(ActionEvent e) {
        String str = itemTextField.getText();
        addItem(str);

        enableFinishButton();

        focusOnItemTextField();
    }

    /**
     * Focuses on itemTextField
     */
    public void focusOnItemTextField() {
        itemTextField.setText(null);
        itemTextField.requestFocusInWindow();
    }

    /**
     * Performs the actions that will happen when the Delete button is pushed
     */
    private void deleteButtonActionPerformed(ActionEvent e) {
        if (itemList.isSelectionEmpty() == false) {
            int selectedIndex = itemList.getSelectedIndex();

            if(selectedIndex != -1) {
                Object obj = listModel.getElementAt(selectedIndex);
                String str= obj.toString();

                //set the TableAction of the item being deleted
                for(AdminSetupItem item: items) {
                    if(item.getMyName().equals(str)) {
                        item.setTableAction(AdminSetupItem.TableAction.DEL);
                    }
                }
                listModel.remove(selectedIndex);
            }

            enableFinishButton();
        }
    }

    /**
     * Performs the actions that will happen when the Finish button is pushed
     */
    private void finishButtonActionPerformed(ActionEvent e) {

        boolean viewOnly = true;
        for(AdminSetupItem item: items) {
            if (item.getTableAction() != AdminSetupItem.TableAction.KEEP) {
                viewOnly = false;
            }
        }

        if(viewOnly == false) {
            boolean success = updateDBItems(items);
            closeCreateTest(success);
            SetupTest.showChooseActionOnTest();
        } else {
            SetupTest.showChooseActionOnTest();
        }

    }

    /**
     * Adds an item to the list
     */
    public void addItem(String suggestedItem) {
            Object[] objects = listModel.toArray();

            if(listModel.isEmpty()) {
                if(suggestedItem.trim().length() > 0) {
                    setupTableAction(suggestedItem);
                    listModel.addElement(suggestedItem);
                    uniqueItemJLabel.setText("That item should work!");
                }
            } else {
                int unique = 1;

                for (Object obj: objects) {
                    if(suggestedItem.equals(obj.toString())) {
                        uniqueItemJLabel.setText(suggestedItem + " has already been entered ");
                        unique = 0;
                    }
                }

                if (unique == 1) {
                    if(suggestedItem.trim().length() > 0) {
                        setupTableAction(suggestedItem);
                        listModel.addElement(suggestedItem);
                        uniqueItemJLabel.setText("That item should work!");
                    }
                }
            } //end else

    }

    /**
     * Sets up the TableAction for the items in the list
     */
    public void setupTableAction(String suggestedItem) {
        int isNew = 1;

        //set the TableAction of the item being added back in after previously being deleted
        for(AdminSetupItem item: items) {
            if(suggestedItem.equals(item.getMyName())) {
                item.setTableAction(AdminSetupItem.TableAction.KEEP);
                isNew = 0;
            }
        }

        //add new item and set it to ins
        if(isNew == 1) {
            AdminSetupItem newItem = new AdminSetupItem(myTestID, suggestedItem);
            newItem.setTableAction(AdminSetupItem.TableAction.INS);
            items.add(newItem);
        }
    }

    /**
     * Enables or disables the finish button
     */
    public void enableFinishButton() {
        int numListEle = itemList.getModel().getSize();

        if(numListEle < 2) {
            finishButton.setEnabled(false);
        } else {
            finishButton.setEnabled(true);
        }
    }

    /**
     * Inserts and deletes items in the database
     */
    public boolean updateDBItems(ArrayList<AdminSetupItem> items) {
        ArrayList<AdminSetupItem> toDelete = new ArrayList<>();
        ArrayList<AdminSetupItem> toInsert = new ArrayList<>();

        for (AdminSetupItem item: items) {
            if(item.getTableAction() == AdminSetupItem.TableAction.DEL) {
                toDelete.add(item);
            } else if(item.getTableAction() == AdminSetupItem.TableAction.INS) {
                toInsert.add(item);
            }
        }

        boolean success = false;
        if(toDelete.isEmpty() == false) {
            success = SharedLogic.Item.deleteItems(toDelete);
        }

        if(toInsert.isEmpty() == false) {
            success = SharedLogic.Item.insertItems(toInsert);
        }

        return success;
    }

    /**
     * Tells whether or not whatever the admin was trying to do was successful or not before closing
     */
    public void closeCreateTest(boolean success) {
        if(success == true) {
            JOptionPane.showMessageDialog(rootPanel, "Success!");
        } else if(success == false) {
            JOptionPane.showMessageDialog(rootPanel, "Failed");
        }
    }

    /**
     * Sets all items to KEEP as the initial enum value
     */
    public void setInitialKeep() {
        //gets items belonging to this test from database
        items = AdminSetupItem.retrieveItemsOnTestAsAdminSetupItem(myTestID);

        for (AdminSetupItem item : items) {
            item.setTableAction(AdminSetupItem.TableAction.KEEP);
            listModel.addElement(item.getMyName());
        }
    }

    /**
     * Gets rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }
}
