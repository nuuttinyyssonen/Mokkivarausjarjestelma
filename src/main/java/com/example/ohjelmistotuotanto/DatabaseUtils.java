package com.example.ohjelmistotuotanto;

import java.sql.*;

public class DatabaseUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/vn";
    private static final String USER = "root";
    private static final String PASSWORD = "salasana";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Get a connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertMokki(int alue_id, String postinro, String mokkinimi, String katuosoite, double hinta, String kuvaus, int henkilomaara, String varustelu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, alue_id);
            pstmt.setString(2, postinro);
            pstmt.setString(3, mokkinimi);
            pstmt.setString(4, katuosoite);
            pstmt.setDouble(5, hinta);
            pstmt.setString(6, kuvaus);
            pstmt.setInt(7, henkilomaara);
            pstmt.setString(8, varustelu);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                System.out.println("Failed to close resource: " + ex.getMessage());
            }
        }
    }
}
