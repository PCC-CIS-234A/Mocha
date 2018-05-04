package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
        private static final String MOCHA_DB = "234a_Mocha";
        private static final String SERVER = "cisdbss.pcc.edu";
        private static final String USERNAME = "234a_Mocha"; //?is this the user taking test?
        private static final String PASSWORD = "@#$Mocha";
        private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"
                + SERVER + ";user=" + USERNAME + ";password=" + PASSWORD;
        private Connection mConnection = null;

        private void connect() {
            if (mConnection != null)
                return;
            try {
                mConnection = DriverManager.getConnection(CONNECTION_STRING);
                System.out.println("Connected to database.");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void close() {
            if (mConnection != null) {
                System.out.println("Closing database connection.");
                try {
                    mConnection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}
