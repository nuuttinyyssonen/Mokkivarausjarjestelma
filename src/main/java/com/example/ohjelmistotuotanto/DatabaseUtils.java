package com.example.ohjelmistotuotanto;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void insertAlue(String nimi) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO alue (nimi) VALUES (?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nimi);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
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

    public static List<Mokki> selectMokitByName(String name) {
        List<Mokki> mokit = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM mokki WHERE mokkinimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("mokki_id");
                int alue_id = rs.getInt("alue_id");
                String mokkinimi = rs.getString("mokkinimi");
                String postinro = rs.getString("postinro");
                String katuosoite = rs.getString("katuosoite");
                Double hinta = rs.getDouble("hinta");
                String kuvaus = rs.getString("kuvaus");
                int henkilomaara = rs.getInt("henkilomaara");
                String varustelu = rs.getString("varustelu");
                mokit.add(new Mokki(alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return mokit;
    }

    public static List<Alue> selectAlueetByName(String name) {
        List<Alue> alueet = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM alue WHERE nimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String nimi = rs.getString("nimi");
                alueet.add(new Alue(nimi));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return alueet;
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
