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
    private JScrollPane itemScrollPane;
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton;
    private JPanel actionButtonPanel;
    private JLabel uniqueItemJLabel;
    private ArrayList<Item> items;
    private DefaultListModel listModel;
    private int myTestID;

    public EditTest(int testID) {
        myTestID = testID;

        rootPanel.setPreferredSize(new Dimension(400, 300));

        actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        listModel = new DefaultListModel();

        items = Item.getTestItems(myTestID);

        for(Item item: items) {
            item.setTableAction(Item.TableAction.KEEP);
            listModel.addElement(item.getName());
        }

        enableFinishButton();

        itemList.setModel(listModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = itemTextField.getText();
                addItem(str);

                enableFinishButton();

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
            }
        });

        itemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = itemTextField.getText();
                addItem(str);

                enableFinishButton();

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemList.isSelectionEmpty() == false) {
                    int selectedIndex = itemList.getSelectedIndex();

                if(selectedIndex != -1) {
                    Object obj = listModel.getElementAt(selectedIndex);
                    String str= obj.toString();

                    //set the TableAction of the item being deleted
                    for(Item item: items) {
                        if(item.getName().equals(str)) {
                            item.setTableAction(Item.TableAction.DEL);
                        }
                    }
                    listModel.remove(selectedIndex);
                }

                    enableFinishButton();
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

    public void setupTableAction(String suggestedItem) {
        int isNew = 1;

        //set the TableAction of the item being added back in after previously being deleted
        for(Item item: items) {
            if(suggestedItem.equals(item.getName())) {
                item.setTableAction(Item.TableAction.KEEP);
                isNew = 0;
            }
        }

        //add new item and set it to ins
        if(isNew == 1) {
            Item newItem = new Item(myTestID, suggestedItem);
            newItem.setTableAction(Item.TableAction.INS);
            items.add(newItem);
        }
    }

    public void enableFinishButton() {
        int numListEle = itemList.getModel().getSize();

        if(numListEle < 2) {
            finishButton.setEnabled(false);
        } else {
            finishButton.setEnabled(true);
        }
    }

    public boolean updateDBItems(ArrayList<Item> items) {
        ArrayList<Item> toDelete = new ArrayList<>();
        ArrayList<Item> toInsert = new ArrayList<>();

        for (Item item: items) {
            if(item.getTableAction() == Item.TableAction.DEL) {
                toDelete.add(item);
            } else if(item.getTableAction() == Item.TableAction.INS) {
                toInsert.add(item);
            }
        }

        boolean success = false;
        if(toDelete.isEmpty() == false) {
            AdminSetupDB db = new AdminSetupDB();
            success = db.deleteItems(toDelete);
        }

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
