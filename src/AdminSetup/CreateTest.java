package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018.
 *Description: This GUI allows the admin to add the items that will make up a test.
 */
public class CreateTest {
    private JPanel rootPanel;
    private JTextField itemTextField;
    private JButton addButton;
    private JScrollPane itemScrollPane;
    private JList itemList;
    private JButton cancelButton;
    private JButton finishButton; //Doesn't do anything yet

    public CreateTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));
        finishButton.setEnabled(false);

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
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}