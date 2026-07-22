package com.expensetracker.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String PROPERTIES_FILE = "resources/db.properties";

    public static Connection getConnection() {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, username, password);

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to connect to database.", e);
        }
    }
}