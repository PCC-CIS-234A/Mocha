package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018.
 * Description: This GUI allows the admin to enter the test name and items
 * that will make up a test.
 */
public class CreateTest {
    private JPanel rootPanel;
    private JTextField itemTextField;
    private JButton addButton;
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton;
    private JScrollPane itemScrollPane;
    private JPanel addItemPanel;
    private JPanel nameTestPanel;
    private JTextField testNameTextField;
    private JLabel confirmTestNameJLabel;
    private JLabel uniqueItemJLabel;
    private JPanel actionButtonPanel;
    private JButton deleteButton;
    private DefaultListModel listModel;

    public CreateTest() {
        rootPanel.setPreferredSize(new Dimension(400, 300));

        finishButton.setEnabled(false);

        setupBorders();

        confirmTestNameJLabel.setVisible(false);

        listModel = new DefaultListModel();

        focusOnItemTextField();

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

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButtonActionPerformed(e);
            }
        });

        testNameTextField.addFocusListener(new FocusAdapter() {
            String name;

            @Override
            public void focusGained(FocusEvent e) {
                name = testNameTextField.getText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String newName = testNameTextField.getText();
                if(!name.equals(newName)) {
                    checkTestName(newName);
                }
            }
        });
    } //end constructor

    /**
     * Performs the actions that will happen when the OK button is pushed
     */
    private void addActionPerformed(ActionEvent e) {
        addUniqueItem();
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
     * Performs the actions that will happen when the Finish button is pushed
     */
    private void finishButtonActionPerformed(ActionEvent e) {
        String testName = testNameTextField.getText();
        Object[] objects = listModel.toArray();
        ArrayList<String> items = new ArrayList<>();

        //Get strings in in the list
        for (Object obj: objects) {
            items.add(obj.toString());
        }

        int i = checkTestName(testName);

        if(i == 1) {
            boolean success = createTestWithItems(testName, items);
            closeCreateTest(success);
        }
    }

    /**
     * Performs the actions that will happen when the Delete button is pushed
     */
    private void deleteButtonActionPerformed(ActionEvent e) {
        if (itemList.isSelectionEmpty() == false) {
            int selectedIndex = itemList.getSelectedIndex();

            listModel.remove(selectedIndex);

            enableFinishButton();
        }
    }

    /**
     * Sets up numerous borders for the GUI
     */
    public void setupBorders() {
        nameTestPanel.setBorder(BorderFactory.createTitledBorder("Name Test"));
        addItemPanel.setBorder(BorderFactory.createTitledBorder("Add Items"));
        actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
    }

    /**
     * Creates the test in the database along with its items
     */
    public boolean createTestWithItems(String testName, ArrayList<String> itemStrings) {

        //Update Test
        AdminSetupDB db = new AdminSetupDB();
        Test test = new Test(testName);
        boolean testSuccess = db.insertTest(test);

        //Get test id from database
        int testID = Test.getTestID(testName);

        //Update Items
        ArrayList<Item> items = new ArrayList<>();

        for(String str: itemStrings) {
            Item item = new Item(testID, str);
            items.add(item);
        }

        boolean itemSuccess = db.insertItems(items);

        if(testSuccess == true && itemSuccess == true) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Adds a unique item to the list
     */
    public void addUniqueItem() {
        Object[] objects = listModel.toArray();

        String suggestedItem = itemTextField.getText();
        if(listModel.isEmpty()) {
            if(suggestedItem.trim().length() > 0) {
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
                    uniqueItemJLabel.setText("That item should work!");
                    listModel.addElement(suggestedItem);
                }
            }
        }
    }

    /**
     * Checks that the name suggested by the admin will work
     */
    public int checkTestName(String name) {
        int i;
        if(name.trim().length() > 0) {
            int success = compareTestNames(name);
            if (success == -1) {
                updateTestNameLabel("There is already a test named " + testNameTextField.getText());
                i = 0;
            } else if (success == 0) {
                updateTestNameLabel("That's a good name!");
                i = 1;
            } else {
                System.out.println("There was a problem when checking the test name.");
                i = -1;
            }
        } else {
            updateTestNameLabel("Please enter a test name.");
            i = -1;
        }
        return i;
    }

    /**
     * Compares the test name given by the admin against test names in the database
     */
    public int compareTestNames(String name) {
        ArrayList<Test> tests = Test.getTestWithName(name);
        for(Test t: tests) {
            if(t.getName() != null) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * After 1 second has gone by, will update the test names JLabel
     */
    public void updateTestNameLabel(String text) {
        confirmTestNameJLabel.setText(null);

        Timer t = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                confirmTestNameJLabel.setText(text);
                confirmTestNameJLabel.setVisible(true);
            }
        });
        t.setRepeats(false);
        t.start();

    }

    /**
     * Enables or disables the Finish button
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
     * Closes the GUI and tells whether or not whatever it was trying to do was successful or not
     */
    public void closeCreateTest(boolean success) {

        if(success == true) {
            JOptionPane.showMessageDialog(rootPanel, "Success!");
        } else if(success == false) {
            JOptionPane.showMessageDialog(rootPanel, "Failed");
        }
        SetupTest.showChooseActionOnTest();
    }

    /**
     * Gets the rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }
}
