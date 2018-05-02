package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018.
 *Description: This GUI allows the admin to add the items that will make up a test.
 */
public class CreateTest {
    private JPanel rootPanel;
    private JTextField itemTextField;
    private JButton addButton;
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton; //Doesn't do anything yet
    private JScrollPane itemScrollPane;
    private JPanel addItemPanel;
    private JPanel nameTestPanel;
    private JButton checkTestNameButton;
    private JTextField testNameTextField;

    public CreateTest() {
      /*  rootPanel.setPreferredSize(new Dimension(300, 200)); OLD SIZE*/
        rootPanel.setPreferredSize(new Dimension(600, 300));
        finishButton.setEnabled(false);
        /*actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));*/
        nameTestPanel.setBorder(BorderFactory.createTitledBorder("Name Test"));
        addItemPanel.setBorder(BorderFactory.createTitledBorder("Add Items"));

        DefaultListModel listModel = new DefaultListModel();

        itemTextField.setText(null);
        itemTextField.requestFocusInWindow();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement(itemTextField.getText());

                //Enable Finish button if more than one item.
                // Currently, if Add is clicked or the enter key is hit
                // in the text field when there isonly one item, the
                // Finish button will be enabled again
                // (That needs to be fixed still).
                int numListEle = itemList.getModel().getSize();
                if(numListEle > 1) {
                    finishButton.setEnabled(true);
                }

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
            }
        });
        itemList.setModel(listModel);
        itemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.addElement(itemTextField.getText());

                //Enable Finish button if more than one item.
                // Currently, if Add is clicked or the enter key is hit
                // in the text field when there isonly one item, the
                // Finish button will be enabled again
                // (That needs to be fixed still).
                int numListEle = itemList.getModel().getSize();
                if(numListEle > 1) {
                    finishButton.setEnabled(true);
                }

                itemTextField.setText(null);
                itemTextField.requestFocusInWindow();
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
                //checkTestName(name);
                /*
                ArrayList<Test> tests = Test.getTests();
                for (Test t: tests) {
                    System.out.println(t.getTestID() + ": " + t.getName());
                }
                */
            }
        });
        checkTestNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = testNameTextField.getText();
                int success = checkTestName(name);
                if (success == -1) {
                    System.out.println("There is already a test named " + testNameTextField.getText());
                } else if (success == 0) {
                    System.out.println("That's a good name!");
                } else {
                    System.out.println("There was a problem when checking the test name.");
                }
            }
        });
    }


    public int checkTestName(String name) {
        ArrayList<Test> tests = Test.getTestWithName(name);
        for(Test t: tests) {
            if(t.getName() != null) {
               /* System.out.println(t.getName());*/
                return -1;
            }
       /*   System.out.println(t.getTestID() + ": " + t.getName());*/
        }
        return 0;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
