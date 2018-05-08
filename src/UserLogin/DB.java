package UserLogin;

import Database.Database;
import UserLogin.Objects.UserAccount;

import java.sql.*;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Read user info from USERACCOUNT table
 * This class talks to the base-database package to get access to Mocha database
 */

public class DB {
    private Database db;
    public DB(){
        db = new Database();
    }

    /**
     * Insert a row to the USERACCOUNT table
     * @param user the user data to be added
     * @return true if insert successfully
     */
    public boolean insertUser(UserAccount user){

        String query = "INSERT INTO USERACCOUNT (UserName,Password,Email) VALUES" +
                "('" +user.getMyUserName()+"', '"+user.getMyPassword()+"', '" +user.getMyEmail()+"');";

        db.execute(query);
        db.close();
        return true;
    }

    /**
     * Read user info from the USERACCOUNT table for a given email
     * @param email the email of a given user
     * @return null if user's email not existed
     */
    public UserAccount getUserByEmail(String email){

        String query = "SELECT * FROM USERACCOUNT WHERE Email= '"+email+"'";

        try {
            Database db = new Database();
            ResultSet rs =  db.execute(query);
            UserAccount user = null;
            while(rs.next()) {
                user = new UserAccount(
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
        db.close();
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
            Database db = new Database();
            ResultSet rs = db.execute(query);
            UserAccount user = null;
            while(rs.next()) {
                user = new UserAccount(
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

        db.close();
        return null;
    }
}
