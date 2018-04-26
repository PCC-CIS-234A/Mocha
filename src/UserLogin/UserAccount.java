/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Create UserAccount Object
 */

public class UserAccount {
    private String myUserName;
    private String myPassword;
    private String myEmail;
    private String myRole;

    public UserAccount(){}
    public UserAccount(String myUserName, String myPassword, String myEmail, String myRole){
        this.setmyUserName(myUserName);
        this.setmyPassword(myPassword);
        this.setmyEmail(myEmail);
        this.setmyRole(myRole);
    }

    public String getmyUserName() {
        return myUserName;
    }

    public void setmyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public String getmyPassword() {
        return myPassword;
    }

    public void setmyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getmyEmail() {
        return myEmail;
    }

    public void setmyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getmyRole() {
        return myRole;
    }

    public void setmyRole(String myRole) {
        this.myRole = myRole;
    }
}
