package ResultReporting;

import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 4/25/2018
 * Description: This class will store data about a user and have methods which return useful information
 */
public class User {
    private ArrayList<Test> tests;
    private int userID;
    private String name;
    private String password;
    private String email;
    private String role;

    public User(int userID, String name, String password, String email, String role) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;

        tests = new ArrayList<>();
    }

    /**
     * gets the Test objects
     * @return an ArrayList of Test objects
     */
    public ArrayList<Test> getTests() {
        return tests;
    }

    /**
     * get the userID
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * returns the name of the user instead of the default object code
     * @return the name of the user
     */
    @Override
    public String toString() {
        return name;
    }


}
