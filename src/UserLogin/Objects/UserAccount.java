package UserLogin.Objects;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Create an UserAccount Object
 */

public class UserAccount {
    private int myUserID;
    private String myUserName;
    private String myPassword;
    private String myEmail;
    private String myRole;

    /**
     * Create a user account object with the given properties
     */
    public UserAccount(int userID, String userName, String password, String email, String role){
        setMyUserID(userID);
        this.setMyUserName(userName);
        this.setMyPassword(password);
        this.setMyEmail(email);
        this.setMyRole(role);
    }
    // Getters and Setters

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
}
