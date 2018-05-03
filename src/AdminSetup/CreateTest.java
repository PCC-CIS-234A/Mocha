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
    private JLabel confirmTestNameJLabel;
    private JLabel uniqueItemJLabel;
    private DefaultListModel listModel;

    public CreateTest() {
      /*  rootPanel.setPreferredSize(new Dimension(300, 200)); OLD SIZE*/
        rootPanel.setPreferredSize(new Dimension(600, 350));
        finishButton.setEnabled(false);
        /*actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));*/
        nameTestPanel.setBorder(BorderFactory.createTitledBorder("Name Test"));
        addItemPanel.setBorder(BorderFactory.createTitledBorder("Add Items"));

        confirmTestNameJLabel.setVisible(false);
//        uniqueItemJLabel.setVisible(false);

        listModel = new DefaultListModel();

        itemTextField.setText(null);
        itemTextField.requestFocusInWindow();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUniqueItem();

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
                addUniqueItem();

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
                checkTestName();
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
                checkTestName();
            }
        });
    }

    public void addUniqueItem() {
        Object[] objects = listModel.toArray();

        String suggestedItem = itemTextField.getText();
        if(listModel.isEmpty()) {
            listModel.addElement(suggestedItem);
            uniqueItemJLabel.setText("That item should work!");
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
                uniqueItemJLabel.setText("That item should work!");
                listModel.addElement(suggestedItem);
            }
        }
    }

    public void checkTestName() {
        String name = testNameTextField.getText();
        int success = compareTestNames(name);
        if (success == -1) {
            updateTestNameLabel("There is already a test named " + testNameTextField.getText());
        } else if (success == 0) {
            updateTestNameLabel("That's a good name!");
        } else {
            System.out.println("There was a problem when checking the test name.");
        }
    }

    public int compareTestNames(String name) {
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

    public void updateTestNameLabel(String text) {
        confirmTestNameJLabel.setText(null);

        Timer t = new Timer(1500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                confirmTestNameJLabel.setText(text);
                confirmTestNameJLabel.setVisible(true);
            }
        });
        t.setRepeats(false);
        t.start();

    }
/*
    public Array getSuggestedItems() {
        listModel.toArray();
    }
*/
    public JPanel getRootPanel() {
        return rootPanel;
    }
}
