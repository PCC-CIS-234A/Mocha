package BusinessLogic;

import UserLogin.DB;
import UserLogin.Objects.UserAccount;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: This is the UserLogin "business logic" class
 * This class force users to follow some business/security rules when
 * they create an account on the Register Form
 * The rules are as following:
 * Users need to fill out all fields
 * User's email need to be unique
 * User's password need to have:
 *     Minimum of 8 characters and maximum of 15 characters
 *     At least one number
 *     At least 1 lowercase
 *     At least 1 Uppercase
 */

public class UserAccount_BL {
    private DB db;
    public UserAccount_BL(){
        db = new DB();
    }

    /**
     * Check data from Register Form
     * @param username
     * @param password
     * @param cpassword
     * @param email
     * @return false if there is any fields on the Register Form is null
     */
    public boolean checkNotNull(String username, String password,String cpassword, String email){
        if(username.equals("")) return true;
        if(password.equals("")) return true;
        if(cpassword.equals("")) return true;
        if(email.equals("")) return true;

        return false;
    }

    /**
     * Check password and confirm password matching
     * @param password
     * @param confirmedPassword
     * @return false if password and confirm password are not matching, else return true
     */
    public boolean checkMatchingPassword(String password,String confirmedPassword) {
        if (password.equals(confirmedPassword)){
            return false;
        }
        return true;
    }

    /**
     * Check if email already existing on the database
     * @param email
     * @return false if the email exits and false otherwise
     */
    public boolean checkEmailExists(String email){
        UserAccount user = db.getUserByEmail(email);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Check password if it is longer than 8 characters and shorter than 15 characters
     * @param password
     * @return true if it meet the requirement and false otherwise
     */
    public Boolean checkPasswordLength(String password) {
        if (password.length() > 15 || password.length() < 8) {
            return true;
        }
        return false;
    }

    /**
     * Check if password contains a number
     * @param password
     * @return true if it has a number and false otherwise
     */
    public Boolean checkPasswordNumber(String password) {
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers )) {
            return true;
        }
        return false;
    }

    /**
     * Check if password contains an uppercase
     * @param password
     * @return true if it contains an uppercase letter and false otherwise
     */
    //Write another function that takes a password and validates that it contains an uppercase letter.

    public Boolean checkPasswordUppercase(String password) {
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars )) {
            return true;
        }
        return false;
    }

    /**
     * Check if password contains a lowercase
     * @param password
     * @return true if it contains a lowercase letter and false otherwise
     */
    public Boolean checkPasswordLowercase(String password) {
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars )) {
            return true;
        }
        return false;
    }
}
