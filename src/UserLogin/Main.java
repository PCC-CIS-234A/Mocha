package UserLogin;


import AdminSetup.SetupTest;
import Database.UserAccountDB;
import ResultReporting.ResultReportingStartup;
import UserLogin.Forms.LoginForm;
import UserLogin.Forms.RegisterForm;
import SharedLogic.UserAccount;

import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Anh Nguyen
 * @version 6/6/2018
 * Description: This is the controller class for UserLogin GUIs
 * This class is also control what a user can do based on their role
 * (user can take tests/admin can set up a test/ therapist can view the report)
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

        UserAccountDB userAccountDb = new UserAccountDB();
        UserAccount user = userAccountDb.getUser(myEmail,myPassword);

    // If user's login is failed , error message will pop up

        if(user == null) {
            JOptionPane.showMessageDialog(null,"Incorrect Email or Password");
            return;
        }

        String role = user.getMyRole();
        myFrame.dispose();

        if(role.equals("user")) {
            // redirect to User page
            UserTakingTest.Main.main(null);
            return;
        }
        if(role.equals("admin")) {
            // redirect to admin page
            new SetupTest();
            return;
        }
        if(role.equals("therapist")) {
            // redirect to therapist page
            SwingUtilities.invokeLater(ResultReportingStartup::createAndShowGui);
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
