package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 4/25/2018.
 * Description: This is the GUI that allows the admin to edit the items in the database.
 */
public class EditTest {
    private JPanel rootPanel;
    private JTextField itemTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JScrollPane itemScrollPane; //Doesn't do anything yet. I plan on using this for Sprint 2
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton; //Doesn't do anything yet
    private JPanel actionButtonPanel;
    private JLabel uniqueItemJLabel;
    private ArrayList<Item> items;
    private DefaultListModel listModel;
    private int myTestID;

    public EditTest(int testID) {
        myTestID = testID;

        rootPanel.setPreferredSize(new Dimension(400, 300));
    //    finishButton.setEnabled(false);
        actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        listModel = new DefaultListModel();

       // ArrayList<Item> items = Item.getTestItems();
        items = Item.getTestItems(myTestID);

        //Add all items to the list
        for(Item item: items) {
            //System.out.println(item.getTestID() + ": " + item.getName());
            item.setTableAction(Item.TableAction.KEEP);
            listModel.addElement(item.getName());
            System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
       //     System.out.println(item.getTestID() + ": " + item.getName());
        }

        enableFinishButton();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = itemTextField.getText();

                addItem(str);

                enableFinishButton();
                        /*
                //Enable Finish button if more than one item.
                // Currently, if Add is clicked or the enter key is hit
                // in the text field when there isonly one item, the
                // Finish button will be enabled again
                // (That needs to be fixed still).
                int numListEle = itemList.getModel().getSize();
                if(numListEle > 1) {
                    finishButton.setEnabled(true);
                }
                */

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
            }
        });
        itemList.setModel(listModel);
        itemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = itemTextField.getText();

                addItem(str);

              //  listModel.addElement(itemTextField.getText());

                enableFinishButton();

                /*
                //Enable Finish button if more than one item.
                // Currently, if Add is clicked or the enter key is hit
                // in the text field when there isonly one item, the
                // Finish button will be enabled again
                // (That needs to be fixed still).
                int numListEle = itemList.getModel().getSize();
                if(numListEle > 1) {
                    finishButton.setEnabled(true);
                }
*/

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
/*
                System.out.println("First group on delete:");
                for(Item item: items) {
                       // System.out.println("listModel.getElementAt(selectedIndex)" + listModel.getElementAt(selectedIndex));
                        System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
                }
*/
                if (itemList.isSelectionEmpty() == false) {
                    int selectedIndex = itemList.getSelectedIndex();
         //           Object obj = listModel.getElementAt(selectedIndex);
        //            String str = obj.toString();
         //           deleteItem(str);



                if(selectedIndex != -1) {
                    Object obj = listModel.getElementAt(selectedIndex);
                    String str= obj.toString();

                    //set the TableAction of the item being deleted
                    System.out.println("Second group on delete:");
                    for(Item item: items) {
                        if(item.getName().equals(str)) {
                            item.setTableAction(Item.TableAction.DEL);
                            System.out.println("listModel.getElementAt(selectedIndex)" + listModel.getElementAt(selectedIndex));
                            System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
                        }
                    }
                    listModel.remove(selectedIndex);
                }

                /*
                    //Might need to remove below if statement
                    if (selectedIndex != -1) {
                        listModel.remove(selectedIndex);
                    }
                    */

                    enableFinishButton();
/*
                //Disable Finish button if less than two items
                int numListEle = itemList.getModel().getSize();
                if(numListEle < 2) {
                    finishButton.setEnabled(false);
                }
                */
                }
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

                boolean viewOnly = true;
                for(Item item: items) {
                    if (item.getTableAction() != Item.TableAction.KEEP) {
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
        });
    }

    /*
    public void deleteItem(String str) {
    //  Item item = (Item) obj;
        int index = -1;
        for(Item item: items) {
            if(str.equals(item.getName())) {
                index = items.indexOf(item);
            }
        }
     //   int index = items.indexOf(item);
        items.remove(index);

        System.out.println("After deleting:");
        for(Item i: items) {
            System.out.println(i.getTestID() + ": " + i.getName());
        }
    }
    */

    public void addItem(String suggestedItem) {
/*
        Item newItem = new Item(1, str);
        items.add(newItem);
        listModel.addElement(itemTextField.getText());
*/



            Object[] objects = listModel.toArray();

          //  String suggestedItem = itemTextField.getText();
            if(listModel.isEmpty()) {
  //              listModel.addElement(suggestedItem);
                if(suggestedItem.trim().length() > 0) {
       //             Item newItem = new Item(myTestID, str);
      //              items.add(newItem);
      //              listModel.addElement(itemTextField.getText());
                    setupTableAction(suggestedItem);
                    listModel.addElement(suggestedItem);
                    uniqueItemJLabel.setText("That item should work!");
                }
            } else {
                int unique = 1;
                for (Object obj: objects) {
                    if(suggestedItem.equals(obj.toString())) {
                        //  System.out.println("That item has already been entered");
                        uniqueItemJLabel.setText(suggestedItem + " has already been entered ");
//                    uniqueItemJLabel.setVisible(true);
                        unique = 0;
                    }
                }
                if (unique == 1) {
//                uniqueItemJLabel.setVisible(false);
                    if(suggestedItem.trim().length() > 0) {
                     //   uniqueItemJLabel.setText("That item should work!");
                      //              listModel.addElement(suggestedItem);
                        setupTableAction(suggestedItem);
                        listModel.addElement(suggestedItem);
                        uniqueItemJLabel.setText("That item should work!");

               //         Item newItem = new Item(myTestID, str);
               //         items.add(newItem);
               //         listModel.addElement(itemTextField.getText());
                    }
                }
            }




        System.out.println("After adding:");
        for(Item item: items) {
            System.out.println(item.getTestID() + ": " + item.getName());
        }

/*
        int isNew = 1;

        //set the TableAction of the item being added back in after previously being deleted
        for(Item item: items) {
            if(suggestedItem.equals(item.getName())) {
                item.setTableAction(Item.TableAction.KEEP);
                System.out.println("Edited item:");
                System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
                isNew = 0;
            }
        }

    //    listModel.addElement(suggestedItem);

        //add new item and set it to ins
        if(isNew == 1) {
            //testID is currently hardcoded to 1. Later it should be a variable for when there are multiple tests.
            Item newItem = new Item(myTestID, suggestedItem);
            newItem.setTableAction(Item.TableAction.INS);
            items.add(newItem);
            System.out.println("New item:");
            System.out.println(newItem.getTestID() + ": " + newItem.getName() + "; " + newItem.getTableAction());
        }
*/

     //   listModel.addElement(suggestedItem);

        System.out.println("All items:");
        for(Item item: items) {
            System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
        }


    }

    public void setupTableAction(String suggestedItem) {
        int isNew = 1;

        //set the TableAction of the item being added back in after previously being deleted
        for(Item item: items) {
            if(suggestedItem.equals(item.getName())) {
                item.setTableAction(Item.TableAction.KEEP);
                System.out.println("Edited item:");
                System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
                isNew = 0;
            }
        }

        //    listModel.addElement(suggestedItem);

        //add new item and set it to ins
        if(isNew == 1) {
            //testID is currently hardcoded to 1. Later it should be a variable for when there are multiple tests.
            Item newItem = new Item(myTestID, suggestedItem);
            newItem.setTableAction(Item.TableAction.INS);
            items.add(newItem);
            System.out.println("New item:");
            System.out.println(newItem.getTestID() + ": " + newItem.getName() + "; " + newItem.getTableAction());
        }
    }

    //Enable Finish button if more than one item.
    // Currently, if Add is clicked or the enter key is hit
    // in the text field when there isonly one item, the
    // Finish button will be enabled again
    // (That needs to be fixed still).
    public void enableFinishButton() {
        int numListEle = itemList.getModel().getSize();

        /*
        if(numListEle > 1) {
            finishButton.setEnabled(true);
        } else {
            finishButton.setEnabled(false);
        }
        */

        if(numListEle < 2) {
            finishButton.setEnabled(false);
        } else {
            finishButton.setEnabled(true);
        }
    }


 /*   public boolean updateDBItems(ArrayList<Item> items) {
        AdminSetupDB db = new AdminSetupDB();
        boolean success = db.mergeItems(items, myTestID);
        return success;
    }
*/

/*
    public boolean updateDBItems(ArrayList<Item> items) {
        AdminSetupDB db = new AdminSetupDB();
        ArrayList<Item> itemsInDB = db.getTestItems(myTestID);
        for(Item item: items) {
            boolean holds = itemsInDB.contains(item);
            System.out.println("itemsInDB.contains(dbItem): " + item.getName() + ": " + holds);
        }


        System.out.println("Item names in DB:");
        for(Item item: itemsInDB) {
            System.out.println(item.getName() + ": " + itemsInDB.contains(item));
        }

        //change this to return boolean(s) from db class
        return true;

    }
*/


    public boolean updateDBItems(ArrayList<Item> items) {
        ArrayList<Item> toDelete = new ArrayList<>();
        ArrayList<Item> toInsert = new ArrayList<>();


        for (Item item: items) {
            if(item.getTableAction() == Item.TableAction.DEL) {
                System.out.println("Deleting " + item.getName());
                toDelete.add(item);
            } else if(item.getTableAction() == Item.TableAction.INS) {
                System.out.println("Inserting " + item.getName());
                toInsert.add(item);
            }
        }

        /*
        for (Item item: items) {
            if (item.getTableAction() == Item.TableAction.KEEP) {
                System.out.println("Deleting " + item.getName());
                toDelete.add(item);
            }
        }
        */

        boolean success = false;
        if(toDelete.isEmpty() == false) {
            AdminSetupDB db = new AdminSetupDB();
            success = db.deleteItems(toDelete);
        }
/*
        for (Item item: items) {
            if(item.getTableAction() == Item.TableAction.INS) {
                System.out.println("Inserting " + item.getName());
                toInsert.add(item);
            }
        }
  */
        if(toInsert.isEmpty() == false) {
            AdminSetupDB db = new AdminSetupDB();
            success = db.insertItems(toInsert);
        }

        return success;
    }


    public void closeCreateTest(boolean success) {

        if(success == true) {
            JOptionPane.showMessageDialog(rootPanel, "Success!");
        } else if(success == false) {
            JOptionPane.showMessageDialog(rootPanel, "Failed");
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
