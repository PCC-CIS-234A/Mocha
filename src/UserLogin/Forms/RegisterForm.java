package UserLogin.Forms;

import UserLogin.BusinessLogic.UserAccount_BL;
import UserLogin.DB;
import UserLogin.Main;
import UserLogin.Objects.UserAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: This class communicates with RegisterForm GUI and also check user input on the register form
 */

public class RegisterForm {
    JPanel rootPanel;
    JButton loginExistingButton;
    JButton registerButton;

    private JTextField userNameField;
    private JPasswordField passwordTextField;
    private JPasswordField passwordConfirmField;
    private JTextField emailTextField;
    private DB db;

    /**
     * This method communicate with UserAccount_BL to check user input
     * and it will output proper warning message to let user know what they should do
     * @return true if all the tests are passed
     */
    private Boolean RegisterData() {

        String strUsername = userNameField.getText();
        String strPassword = new String(passwordTextField.getPassword());
        String strConfirmPassword = new String(passwordConfirmField.getPassword());
        String strEmail = emailTextField.getText();

        UserAccount_BL userBL = new UserAccount_BL();

        boolean chkNotNull = userBL.checkNotNull(strUsername,strPassword,strConfirmPassword,strEmail);
            if(chkNotNull == true){
                JOptionPane.showMessageDialog(null,
                        "Please fill out all the fields");
                passwordTextField.requestFocusInWindow();
            return false;
            } else {

                boolean chkEmailExist = userBL.checkEmailExists(strEmail);
                    if(chkEmailExist == true){
                        JOptionPane.showMessageDialog(null,
                            "Email existed");
                        emailTextField.requestFocusInWindow();
                        return false;
                    } else {
                        boolean chkPasswordLength = userBL.checkPasswordLength(strPassword);
                            if(chkPasswordLength == true) {
                                JOptionPane.showMessageDialog(null,
                                    "Password must contain a minimum of 8 characters and a maximum of 15 character");
                                passwordTextField.requestFocusInWindow();
                                return false;
                            } else {

                                boolean chkPasswordNumber = userBL.checkPasswordNumber(strPassword);
                                    if(chkPasswordNumber == true) {
                                        JOptionPane.showMessageDialog(null,
                                            "password must contain at least a number");
                                        passwordTextField.requestFocusInWindow();
                                        return false;
                                     } else {

                                        boolean chkPasswordUppercase = userBL.checkPasswordUppercase(strPassword);
                                            if (chkPasswordUppercase == true) {
                                                JOptionPane.showMessageDialog(null,
                                                    "password need to have uppercase letter");
                                                passwordTextField.requestFocusInWindow();
                                                return false;
                                            } else {

                                            boolean chkPasswordLowerCase = userBL.checkPasswordLowercase(strPassword);
                                                if (chkPasswordLowerCase == true){
                                                    JOptionPane.showMessageDialog(null,
                                                        "password need to have lowercase letter");
                                                    passwordTextField.requestFocusInWindow();
                                                    return false;
                                                } else {

                                                    boolean chkMatchingPassword = userBL.checkMatchingPassword(strPassword,strConfirmPassword);
                                                        if (chkMatchingPassword == true) {
                                                            JOptionPane.showMessageDialog(null,
                                                                "Password and confirm password are not matched");
                                                            passwordConfirmField.requestFocusInWindow();
                                                            return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        db = new DB();
        UserAccount result = db.insertUser(strUsername,strPassword,strEmail,"user");
        return true;
    }

    // This methods communicate with RegisterForm GUI
    public RegisterForm(String email, String password) {
        emailTextField.setText(email);
        passwordTextField.setText(password);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(emailTextField.getText());
                if (RegisterData() == true) {

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
