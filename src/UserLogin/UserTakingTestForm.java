import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by SYTC307u8365 on 10/10/2017.
 */
public class UserTakingTestForm {
    private JButton startOverButton;
    private JPanel rootPanel;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public UserTakingTestForm() {


        startOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setEmail("");
                Main.setPassword("");
                Main.createGUI();
            }
        });
    }
}
