package ResultReporting;

import java.util.ArrayList;

/**
 * @author Bobby Puckett
 * @version 4/25/2018
 * Description: This class will store data about a user and have methods which return useful information
 */
public class User {
    ArrayList<Test> tests;
    int userID;
    String name;
    String password;
    String email;
    String role;

    public User(int userID, String name, String password, String email, String role) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;

        tests = new ArrayList<>();
    }

    public ArrayList<Test> getTests() {
        return tests;
    }

    @Override
    public String toString() {
        return name;
    }
}
