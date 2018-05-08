package UserLogin.Objects;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Create an UserAccount Object
 */

public class UserAccount {
    private String myUserName;
    private String myPassword;
    private String myEmail;
    private String myRole;

    /**
     * Create a user account object with the given properties
     */
    public UserAccount(String myUserName, String myPassword, String myEmail, String myRole){
        this.setMyUserName(myUserName);
        this.setMyPassword(myPassword);
        this.setMyEmail(myEmail);
        this.setMyRole(myRole);
    }
    // Getters and Setters
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
