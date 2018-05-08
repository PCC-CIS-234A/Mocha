package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 4/25/2018.
 * Description: This GUI allows the admin to choose whether he wants to add another test
 * or edit an existing test. When he makes his choice, the appropriate GUI should display.
 *
 * Currently only two of my GUIs show. I plan to display ViewTest GUI later.
 *
 * After the Admin selects "Edit/View Test", he will be presented within this GUI the test
 * (When Sprint 2, a list of the tests) in the database. Next to the displayed test(s), it
 * will display to him whether or not it can be edited or viewed only. After he chooses his
 * test, either the EditTest GUI will display or the ViewTest GUI will display.
 */
public class ChooseActionOnTest {
    private static JFrame frame = null;
    private JPanel rootPanel;
    private JRadioButton createTestRadioButton;
    private JRadioButton editTestRadioButton;
    private JButton okButton;
    private JButton backButton; //Doesn't do anything yet. Plan to have it return the user to the GUI that called this GUI.
    private JScrollPane testScrollPane;
    private JList testList;
    private ArrayList<Test> tests;
    private DefaultListModel listModel;

    public ChooseActionOnTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel();
        testList.setModel(listModel);

        // ArrayList<Item> items = Item.getTestItems();
        tests = Test.getTests();

        //Add all items to the list
        for(Test test: tests) {
            System.out.println("ChooseActionOnTest values: " + test.getName());
            String listStr = test.getName();
            System.out.println("Editable: " + test.getEditable());
            /*
            if(test.getEditable() == true) {
                listStr = listStr + "   Edit/View";
            } else if(test.getEditable() == false) {
                listStr = listStr + "   View";
            }*/
            listModel.addElement(listStr);
            //       System.out.println(item.getTestID() + ": " + item.getName() + "; " + item.getTableAction());
            System.out.println(test.getTestID() + ": " + test.getName());
        }

     //   ArrayList<Test> allTests = Test.getTests();
        ArrayList<TestSession> takenTests = TestSession.getTakenTests();
        //       System.out.println("All tests:");
        for (Test t: tests) {
            System.out.println("All tests:");
            System.out.println(t.getTestID() + "; " + t.getEditable());

            System.out.println("Taken test:");
            for (TestSession ts: takenTests) {

                if(t.getTestID() == ts.getTestID()) {
                    //   System.out.println(t.getTestID() + ": " + ts.getTestID());
                    t.setEditable(false);
                    System.out.println(t.getTestID() + ": " + ts.getTestID() + "; " + t.getEditable());
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
    }

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

    public void showViewOrEditGUI(String testName) {
 //       if (createTestRadioButton.isSelected()) {
  //          SetupTest.showCreateTest();
 //       }else if (editTestRadioButton.isSelected()) {
            for(Test test: tests) {
                if(test.getName().equals(testName)) {
                    boolean editable = test.getEditable();
                    System.out.println(editable);
                    if(editable == true) {
                        SetupTest.showEditTest();
                    } else if (editable == false) {
                        SetupTest.showViewTest();
                    }
                }
            }

            /*
            if(editable == true) {
                SetupTest.showEditTest();
            } else {
                SetupTest.showViewTest();
            }
            */



            /*
            if(editable == true) {
                SetupTest.showEditTest();
            } else {
                SetupTest.showViewTest();
            }
            */


      //      System.out.println("Taken tests:");
      //      for (TestSession ts: takenTests) {
      //          System.out.println(ts.getTestID());
       //     }

     //   }

    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
