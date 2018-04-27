package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton finishButton; //Doesn't do anything yet
    private JPanel actionButtonPanel;

    public EditTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));
        finishButton.setEnabled(false);
        actionButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        DefaultListModel listModel = new DefaultListModel();

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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = itemList.getSelectedIndex();
                if(selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }

                //Disable Finish button if less than two items
                int numListEle = itemList.getModel().getSize();
                if(numListEle < 2) {
                    finishButton.setEnabled(false);
                }
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
