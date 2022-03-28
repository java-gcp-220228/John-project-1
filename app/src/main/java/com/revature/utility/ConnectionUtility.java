package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
    private ConnectionUtility() {}

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());

        String url = System.getenv("p1_url");
        String username = System.getenv("p1_username");
        String password = System.getenv("p1_password");

        return DriverManager.getConnection(url, username, password);
    }
}
