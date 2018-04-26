import java.sql.*;
import java.util.ArrayList;

/**
 * @author Anh Nguyen
 * @version 4/25/2018
 * Description: Read data from 234a_Mocha Database
 */


public class Database {

    private static final String SERVER = "cisdbss.pcc.edu";
    private static final String DATABASE = "234a_Mocha";
    private static final String USERNAME = "234a_Mocha";
    private static final String PASSWORD = "@#$Mocha";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"
            + SERVER + "/" + DATABASE + ";user=" + USERNAME + ";password=" + PASSWORD;
    private Connection myConnection = null;

    private Statement s = null;
    private void connect() {
        if(myConnection != null)
            return;
        try {
            myConnection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertUser(UserAccount user){
        connect();


        String query = "INSERT INTO UserAccount (UserName,Password,Email) VALUES" +
                "('" +user.getmyUserName()+"', '"+user.getmyPassword()+"', '" +user.getmyEmail()+"');";
        try {
            s = myConnection.createStatement();
            s.execute(query);
            //stmt.setString(1, state);

        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        close();
        return true;
    }
    public UserAccount getUser(String email, String password) {

        System.out.println(email);
        System.out.println(password);
        connect();
        String query = "SELECT * FROM UserAccount WHERE Email= '"+email+"' and Password= '"+password+"'";
        System.out.println(query);
        try {

            PreparedStatement stmt = myConnection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

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

        close();
        return null;
    }

    public void close() {
        if(myConnection != null) {
            System.out.println("Closing database connection.");
            try {
                myConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() {
        close();
    }
}
