package UserLogin;

import UserLogin.Forms.LoginForm;
import UserLogin.Forms.RegisterForm;
import UserLogin.Objects.UserAccount;

import javax.swing.*;


import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: This is the controller class for UserLogin GUIs
 * This class is also control what a user can do based on their role
 * (user can take tests/admin can set up a test/ therapist can view the report)
 * For sprint 1: only show different messages for different roles
 */


public class Main {
    private static JFrame myFrame = null;
    private static String myEmail = "";
    private static String myPassword = "";

    public static void createGUI() {
        myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
        showLogin();
    }

    public static void showLogin() {
        myFrame.setTitle("Log in");
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(new LoginForm(myEmail, myPassword).getRootPanel());
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    public static void showRegister() {
        myFrame.setTitle("Register");
        myFrame.setSize(300, 150);
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(new RegisterForm(myEmail, myPassword).getRootPanel());
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    public static void setEmail(String email) {
        myEmail = email;
    }

    public static void setPassword(String password) {
        myPassword = password;
    }

    public static void login() {
     //check Email and Password

        DB db = new DB();
        UserAccount user = db.getUser(myEmail,myPassword);

    // If user's login is failed , error message will pop up
        if(user == null) {
            JOptionPane.showMessageDialog(null,"Incorrect Email or Password");
            return;
        }
        String role = user.getMyRole();
    // If user's login is successful, different roles will assigned different messages
        if(role.equals("user")) {
            // redirect to User page
            JOptionPane.showMessageDialog(null, "User Taking Test Page");
            return;
        }
        if(role.equals("admin")) {
            // redirect to admin page
           JOptionPane.showMessageDialog(null, "Show Admin Setup Page");
          //  new SetupTest();
            return;
        }
        if(role.equals("therapist")) {
            // redirect to therapist page
            JOptionPane.showMessageDialog(null, "Show Report Page");
            return;
        }
        }
    // The program start from here
    public static void main(String[] args) {
        invokeLater(new Runnable() {
           public void run() {
               createGUI();
           }
        });
    }
}
