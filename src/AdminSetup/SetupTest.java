package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Rebecca Kennedy
 * @version 4/23/2018
 * Description: This is the controller class for the AdminSetup GUIs.
 * This class will have a GUI that the Admin can interact with.
 */
public class SetupTest {
    private static JFrame frame = null;
    private JPanel rootPanel;
    private JRadioButton createTestRadioButton;
    private JRadioButton editTestRadioButton;
 //   private JRadioButton ViewTestRadioButton;
    private JButton okButton;

    public SetupTest() {
        rootPanel.setPreferredSize(new Dimension(300, 200));

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(createTestRadioButton);
        radioGroup.add(editTestRadioButton);
     //   radioGroup.add(ViewTestRadioButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });
    }

    public static void showCreatTest() {
        frame.getContentPane().removeAll();
        frame.setTitle("Enter Items");
        frame.getContentPane().add(new CreateTest().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    private void okButtonActionPerformed(ActionEvent e) {
        if (createTestRadioButton.isSelected()) {
            showCreatTest();
        }
        if (editTestRadioButton.isSelected()) {
            //Do something...
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
