package UserLogin;

import javax.swing.*;


import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description:
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
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(new LoginForm(myEmail, myPassword).getRootPanel());
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    public static void showRegister() {
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(new RegisterForm(myEmail, myPassword).getRootPanel());
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    public static void showUserTakingTest() {
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(new UserTakingTestForm().getRootPanel());
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

        // check Email and Password

        Database db = new Database();

        UserAccount user = db.getUser(myEmail,myPassword);

        if(user == null)
        {

            JOptionPane.showMessageDialog(null,"Incorrect Email or Password");
            return;
        }
        String role= user.getmyRole();
        //System.out.println(role);
        if(role.equals("user")) {
            // redirect to User page
            System.out.println("Show User taking test");
            showUserTakingTest();
        }
        if(role.equals("admin")) {
            // redirect to admin page
            System.out.println("Show admin setup test");
        }
        if(role.equals("therapist")) {
            // redirect to therapist page
            System.out.println("Show user report");
        }
        }

    public static void main(String[] args) {
        invokeLater(new Runnable() {
           public void run() {
               createGUI();
           }
        });
    }
}
