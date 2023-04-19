package edu.pwr.db.model;

import java.sql.SQLException;

public class Login {
    private static DBConnection establishedConnection;
    private static String username, password;

    public static DBConnection authenticate(String username, String password) {
        establishedConnection = new DBConnection();
        try {
            establishedConnection.connect(username, password);
            Login.password = password;
            Login.username = username;
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            return null;
        }
        return establishedConnection;
    }

    public static String getUsername() {
        return username;
    }
    public static String getPassword() {
        return password;
    }
}
