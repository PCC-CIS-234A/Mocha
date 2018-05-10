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
    //Back button doesn't do anything yet. Plan to have it return the user to the Login GUI in Sprint 2.
    private JButton backButton;
    private JScrollPane testScrollPane;
    private JList testList;
    private JPanel testJPanel;
    private ArrayList<Test> tests;
    private DefaultListModel listModel;

    public ChooseActionOnTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));

        testJPanel.setVisible(false);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel();
        testList.setModel(listModel);

        tests = Test.getTests();

        //Add all tests to the list
        for(Test test: tests) {
            String listStr = test.getName();
            listModel.addElement(listStr);
        }

        ArrayList<TestSession> takenTests = TestSession.getTakenTests();

        for (Test t: tests) {

            for (TestSession ts: takenTests) {

                if(t.getTestID() == ts.getTestID()) {
                    t.setEditable(false);
                }
            }
        }

        createTestRadioButton.setSelected(true);

        //Sets default select to be the first test
        testList.setSelectedIndex(0);

        testScrollPane.setVisible(true);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(createTestRadioButton);
        radioGroup.add(editTestRadioButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });
        editTestRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    testJPanel.setVisible(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    testJPanel.setVisible(false);
                }
            }
        });
    }

    /**
     * Performs the actions that will happen when the OK button is hit
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
     * Decides which GUI should appear next
     */
    public void showViewOrEditGUI(String testName) {

            for(Test test: tests) {
                if(test.getName().equals(testName)) {
                    boolean editable = test.getEditable();
                    if(editable == true) {
                        int id = test.getTestID();
                        SetupTest.showEditTest(id);
                    } else if (editable == false) {
                        int id = test.getTestID();
                        SetupTest.showViewTest(id);
                    }
                }
            }
    }

    /**
     * Gets the rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }
}