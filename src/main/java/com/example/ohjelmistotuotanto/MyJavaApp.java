package com.example.ohjelmistotuotanto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyJavaApp {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Replace with your database credentials
            String url = "jdbc:mysql://localhost:3306/vn";
            String user = "root";
            String password = "salasana";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get a connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established!");

            // Create a Statement
            Statement stmt = conn.createStatement();

            // Execute a UPDATE query
            String sql = "DELETE FROM alue WHERE alue_id = '1'";
            int rowsAffected = stmt.executeUpdate(sql);

            System.out.println("Rows affected : " + rowsAffected);
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
