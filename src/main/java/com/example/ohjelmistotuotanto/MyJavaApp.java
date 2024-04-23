package com.example.ohjelmistotuotanto;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyJavaApp {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Replace with your database credentials
            String url = "jdbc:mysql://localhost:3306";
            String user = "root";
            String password = "salasana";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get a connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established!");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            // Close the connection (if it was obtained)
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (Exception ex) {
                    System.out.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
    }
}
