package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 4/25/2018.
 * Description: This GUI allows the admin to choose whether he wants to add another test
 * or edit an existing test. When he makes his choice, the appropriate GUI should display.
 * A list of tests should display for the admin when he selects that he would like to
 * Edit/View the test. The admin should then select which test he would like to edit or
 * view and then that test's items should appear in the next GUI.
 */
public class ChooseActionOnTest {
    private static JFrame frame = null;
    private JPanel rootPanel;
    private JRadioButton createTestRadioButton;
    private JRadioButton editTestRadioButton;
    private JButton okButton;
    private JButton backButton;
    private JScrollPane testScrollPane;
    private JList testList;
    private JPanel testJPanel;
    private ArrayList<AdminSetupTest> tests;
    private DefaultListModel listModel;

    public ChooseActionOnTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));

        testJPanel.setVisible(false);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel();
        testList.setModel(listModel);

        addTestsToList();

        setNotEditable();

        createTestRadioButton.setSelected(true);

        //Sets default select to be the first test
        testList.setSelectedIndex(0);

        testScrollPane.setVisible(true);

        setupRadioGroup();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });

        editTestRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                editTestRadioButtonActionPerformed(e);
            }

        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonActionPerformed(e);
            }
        });
    } //end constructor

    /**
     * Performs the actions that will happen when the OK button is pushed
     */
    private void okButtonActionPerformed(ActionEvent e) {
        if (createTestRadioButton.isSelected()) {
            SetupTest.showCreateTest();
        }else if (editTestRadioButton.isSelected()) {
            int selectedIndex = testList.getSelectedIndex();
            Object obj = listModel.getElementAt(selectedIndex);
            String testName = obj.toString();
            showViewOrEditGUI(testName);
        }
    }

    /**
     * Performs the actions that will happen when the Edit Test radio button is pushed
     */
    private void editTestRadioButtonActionPerformed(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {
            testJPanel.setVisible(true);
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            testJPanel.setVisible(false);
        }
    }

    /**
     * Performs the actions that will happen when the Back button is pushed
     */
    private void backButtonActionPerformed(ActionEvent e) {
        SetupTest.disposeGUI();
        UserLogin.Main.createGUI();
    }

    /**
     * Decides which GUI should appear next
     */
    public void showViewOrEditGUI(String testName) {

            for(AdminSetupTest test: tests) {
                if(test.getMyName().equals(testName)) {
                    boolean editable = test.getEditable();
                    if(editable == true) {
                        int id = test.getMyTestID();
                        SetupTest.showEditTest(id);
                    } else if (editable == false) {
                        int id = test.getMyTestID();
                        SetupTest.showViewTest(id);
                    }
                }
            }
    }

    /**
     * Sets the editable variable for tests that have been taken to not editable
     */
    public void setNotEditable() {
        ArrayList<SharedLogic.TestSession> takenTests = SharedLogic.TestSession.retrieveTakenTests();

        for (AdminSetupTest t: tests) {

            for (SharedLogic.TestSession ts: takenTests) {
                if(t.getMyTestID() == ts.getMyTestID()) {
                    t.setEditable(false);
                }
            }
        }
    }

    /**
     * Gets all the tests and adds them to the list
     */
    public void addTestsToList() {
        //Get all tests
        tests = AdminSetupTest.retrieveAllTestsAsAdminSetupTest();

        //Add all tests to the list
        for(AdminSetupTest test: tests) {
            String listStr = test.getMyName();
            listModel.addElement(listStr);
        }
    }

    /**
     * Sets up the radio button group
     */
    public void setupRadioGroup() {
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(createTestRadioButton);
        radioGroup.add(editTestRadioButton);
    }

    /**
     * Gets the rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }
}