package UserTakingTest;

/**
 * Class to define a user
 * @author Liz Goltz
 * @version 5/9/2018
 */

public class User {
    private int mUserID;
    private String mUserName;
    private String mPassword;
    private String mEmail;
    private String mRole;

    public User(int userID, String userName, String password, String email, String role){
        setUserID(userID);
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
        this.setRole(role);
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        this.mUserID = userID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        this.mRole = role;
    }
}

