import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class SelectUserForm {
    private JButton okButton;
    private JList userList;
    private JPanel userListPanel;
    private JLabel selectLabel;

    private final Vector<String> userListVector = new Vector<>();

    public SelectUserForm() {
        userListPanel.setPreferredSize(new Dimension(300,350));

        userListVector.add("Steve Johnson");
        userListVector.add("Carolina Yurbek");
        userListVector.add("Peter Capaldi");
        userListVector.add("Grolk Flemendorf");
        userListVector.add("Crilso Oglibek");
        userListVector.add("Ilsey Yurma");

        userList.setListData(userListVector);
    }

    public JPanel getUserListPanel() {
        return userListPanel;
    }
}
