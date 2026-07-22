package com.expensetracker;

import com.expensetracker.db.DBConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DBConnection.getConnection()) {

            System.out.println("Connected to MySQL successfully!");

        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

    }
}