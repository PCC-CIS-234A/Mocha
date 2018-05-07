package SharedLogic;

import Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Anh Nguyen abd bobby Puckett
 * @version 5/4/2018
 * Description: Create UserAccount Object
 */

public class UserAccount {
    private int myUserID;
    private String myUserName;
    private String myPassword;
    private String myEmail;
    private String myRole;

    // QUERY FIELDS
    private static final String GET_ALL_USERS = "SELECT * FROM USERACCOUNT";
    private static final String INSERT_USER = "INSERT INTO USERACCOUNT (UserName,Password,Email) VALUES('";
    private static final String GET_USER_ON_NAME_PASSWORD = "SELECT * FROM USERACCOUNT WHERE Email= '";
    private static final String GET_USER_ON_ID = "SELECT * FROM USERACCOUNT WHERE UserID = ";
    // QUERY FIELDS END


    // CONSTRUCTORS
    public UserAccount() {
    }

    public UserAccount(String userName, String password, String email, String role) {
        this.setMyUserName(userName);
        this.setMyPassword(password);
        this.setMyEmail(email);
        this.setMyRole(role);
    }

    public UserAccount(int userID, String userName, String password, String email, String role) {
        setMyUserID(userID);
        this.setMyUserName(userName);
        this.setMyPassword(password);
        this.setMyEmail(email);
        this.setMyRole(role);
    }
    //CONSTRUCTORS END


    // DATABASE METHODS
    public static boolean insertUser(UserAccount user) {

        Database database = new Database();

        String query = INSERT_USER + user.getMyUserName() + "', '" + user.getMyPassword() + "', '" + user.getMyEmail() + "');";

        database.execute(query);

        return true;
    }

    public static UserAccount retrieveUserOnEmailPassword(String email, String password) {
        Database database = new Database();

        System.out.println(email);
        System.out.println(password);

        String query = GET_USER_ON_NAME_PASSWORD + email + "' and Password= '" + password + "'";
        System.out.println(query);

        ResultSet userResultSet = database.execute(query);

        UserAccount user = null;

        try {
            if (userResultSet.next()) {
                user = new UserAccount(userResultSet.getInt("UserID"),
                        userResultSet.getString("UserName"),
                        userResultSet.getString("Password"),
                        userResultSet.getString("Email"),
                        userResultSet.getString("Role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static ArrayList<UserAccount> retrieveAllUsers() {
        ArrayList<UserAccount> users = new ArrayList<>();

        Database database = new Database();

        ResultSet userResultSet = database.execute(GET_ALL_USERS);
        try {
            while (userResultSet.next()) {
                int newUserID = userResultSet.getInt("UserID");
                String newName = userResultSet.getString("UserName");
                String newPassword = userResultSet.getString("Password");
                String newEmail = userResultSet.getString("Email");
                String role = userResultSet.getString("Role");

                UserAccount user = new UserAccount(newUserID, newName, newPassword, newEmail, role);

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public static UserAccount retrieveUserOnID(int id) {
        Database database = new Database();

        try {
            ResultSet userResultSet = database.execute(GET_USER_ON_ID + id);

            if (userResultSet.next()) {
                int newUserID = userResultSet.getInt("UserID");
                String newName = userResultSet.getString("UserName");
                String newPassword = userResultSet.getString("Password");
                String newEmail = userResultSet.getString("Email");
                String role = userResultSet.getString("Role");

                UserAccount user = new UserAccount(newUserID, newName, newPassword, newEmail, role);

                return user;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    // DATABASE METHODS END


    // GETTERS / SETTERS
    public int getMyUserID() {
        return myUserID;
    }

    public void setMyUserID(int myUserID) {
        this.myUserID = myUserID;
    }

    public String getMyUserName() {
        return myUserName;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getMyRole() {
        return myRole;
    }

    public void setMyRole(String myRole) {
        this.myRole = myRole;
    }
    // GETTERS / SETTERS END

    @Override
    public String toString() {
        return getMyUserName();
    }
}

/*
 * NOTES:
 *
 * Kim -    I added the getMyUser() and insertUser()
 *          method for you, so you shouldn't have to do anything except change the package
 *          and call the method from UserAccount like -
 *
 *          UserAccount.retrieveUserOnEmailPassword(email, password);
 *          UserAccount.insertUser(user);
 *
 *          (the getMyUser() method I renamed to retrieveUserOnEmailPassword to follow the naming
 *          for the other classes of "retrieve + <Object> + On + <Fields>")
 */
