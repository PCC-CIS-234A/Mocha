package UserLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: GUI for Register Form
 */

public class RegisterForm {
    JPanel rootPanel;
    private JTextField userNameField;
    JButton loginExistingButton;
    JButton registerButton;
    private JPasswordField passwordTextField;
    private JPasswordField passwordConfirmField;

    private Database db;
    private JTextField emailTextField;

    // Verify User input
    private Boolean RegisterData() {

        String strUsername = userNameField.getText();
        String strPassword = new String(passwordTextField.getPassword());
        String strConfirmPassword = new String(passwordConfirmField.getPassword());

        String strEmail = emailTextField.getText();

        if (strUsername.equals("")) // Username
        {
            JOptionPane.showMessageDialog(null,
                    "Please Input (Username)");
            userNameField.requestFocusInWindow();
            return false;
        }
        if (strPassword.equals("")) // Password
        {
            JOptionPane.showMessageDialog(null,
                    "Please Input (Password)");
            passwordTextField.requestFocusInWindow();
            return  false;
        }

        if (strConfirmPassword.equals("")) // Confirm Password
        {
            JOptionPane.showMessageDialog(null,
                    "Please Input (Confirm Password)");
            passwordConfirmField.requestFocusInWindow();
            return false;
        }
        if (!strPassword.equals(strConfirmPassword)) // Password math
        {
            JOptionPane.showMessageDialog(null,
                    "Please Input (Password Not Match!)");
            passwordTextField.requestFocusInWindow();
            return false;
        }

        if (strEmail.equals("")) // Email
        {
            JOptionPane.showMessageDialog(null,
                    "Please Input (Email)");
            emailTextField.requestFocusInWindow();
            return false;
        }

        db = new Database();
        UserAccount user = new UserAccount(strUsername,strPassword,strEmail,"user");
        boolean result = db.insertUser(user);
        return true;

    }


    public RegisterForm(String email, String password) {
        emailTextField.setText(email);
        passwordTextField.setText(password);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // some logic to verify that the user is the legitimate user.

                if (RegisterData() == true) {

               // System.out.println("Test");

                Main.setEmail(emailTextField.getText());
                Main.setPassword(passwordTextField.getText());
                 Main.showLogin();}

            }
        });
        loginExistingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setEmail(emailTextField.getText());
                Main.setPassword(passwordTextField.getText());
                Main.showLogin();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
