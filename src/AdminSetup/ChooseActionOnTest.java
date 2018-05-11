package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JList itemList;

    public ChooseActionOnTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        testScrollPane.setVisible(false);

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
        }
        if (editTestRadioButton.isSelected()) {
            SetupTest.showEditTest();
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
