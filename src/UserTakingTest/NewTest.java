import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Liz Goltz on 4/10/2018
 * Class to display intro GUI to a new test
 */
public class NewTest {
    private JLabel sessionId;
    private JButton startButton;
    private JPanel rootPanel;
    private JLabel testName;
    private JLabel userName;

    //constructor for NewTest Class
    public NewTest(String userName, String testName, String sessionId)
    {
        rootPanel.setPreferredSize(new Dimension(300, 200));
        this.userName.setText(userName);
        this.testName.setText(testName);
        this.sessionId.setText(sessionId);

      /* startButton.addActionListener(new ActionListener()
        {
            Main.showItemCompare();
        }
        )*/
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
