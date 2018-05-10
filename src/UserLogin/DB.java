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
    private Connection mConnection = null;

    public DB(){
        db = new Database();
    }

    /**
     * Insert a row into USERACCOUNT table
     * @param userName user's username
     * @param password user's password
     * @param email user's email
     * @param role user's role
     * @return null if this method failed to insert data to database
     */

    public UserAccount insertUser(String userName, String password, String email, String role) {
        //   connect();
        String query = "INSERT INTO USERACCOUNT (UserName, Password, Email, Role) VALUES" +
                "('"+userName+"', '"+password+"', '"+email+"', 'user');SELECT SCOPE_IDENTITY() AS ID;";

        try {
            Database db = new Database();
            ResultSet rs =  db.execute(query);

            if (rs.next()) {
                return new UserAccount(
                        rs.getInt("ID"), userName, password, email, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
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
            Database db = new Database();
            ResultSet rs =  db.execute(query);
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

        db.close();
        return null;
    }
}
