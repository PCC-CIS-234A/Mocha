package Database;

import SharedLogic.UserAccount;

import java.sql.*;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Read user info from USERACCOUNT table
 * This class talks to the base-database package to get access to Mocha database
 */

public class UserAccountDB extends Database{
    public UserAccount insertUser(String userName, String password, String email, String role) {
        String query = "INSERT INTO USERACCOUNT (UserName, Password, Email, Role) VALUES" +
                "('"+userName+"', '"+password+"', '"+email+"', 'user');SELECT SCOPE_IDENTITY() AS ID;";

        try {
            ResultSet rs =  super.execute(query);

            if (rs.next()) {
                return new UserAccount(
                        rs.getInt("ID"), userName, password, email, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
        return null;

    }

    /**
     * Read user info from the USERACCOUNT table for a given email
     * @param email the email of a given user
     * @return null if user's email not existed
     */
    public UserAccount getUserByEmail(String email){

        String query = "SELECT * FROM USERACCOUNT WHERE Email= '"+email+"'";

        try {
            //Database db = new Database();
            ResultSet rs =  super.execute(query);
            UserAccount user = null;
            while(rs.next()) {
                user = new UserAccount(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Role")
                );
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
        return null;
    }

    /**
     * Read user info from database for a given email and password
     * @param email the email of a given user
     * @param password the password of a given user
     * @return null if the pair of email and password are not correct
     */
    public UserAccount getUser(String email, String password) {

        String query = "SELECT * FROM USERACCOUNT WHERE Email= '"+email+"' and Password= '"+password+"'";

        try {
            ResultSet rs = super.execute(query);
            UserAccount user = null;
            while(rs.next()) {
                user = new UserAccount(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Role")
                );
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.close();
        return null;
    }
}
