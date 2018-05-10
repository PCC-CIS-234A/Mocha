package UserLogin.Forms;

import UserLogin.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: This class communicates with loginForm GUI
 */

public class LoginForm {
    JPanel rootPanel;
    JTextField emailTextField;
    JButton newAccountButton;
    JButton loginButton;
    JTextField passwordTextField;

    public LoginForm(String email, String password) {
        emailTextField.setText(email);
        passwordTextField.setText(password);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setEmail(emailTextField.getText());
                Main.setPassword(passwordTextField.getText());
                Main.login();
            }
        });
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setEmail(emailTextField.getText());
                Main.setPassword(passwordTextField.getText());
                Main.showRegister();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
