package com.example.ohjelmistotuotanto;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

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
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql_postinro = "SELECT * FROM posti WHERE postinro = ?";
            pstmt = conn.prepareStatement(sql_postinro);
            pstmt.setString(1, postinro);
            rs = pstmt.executeQuery();
            if(!rs.next()) {
                System.out.println("here");
                String sql = "INSERT INTO posti (postinro, toimipaikka) VALUES (?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, postinro);
                pstmt.setString(2, "Kuopio");
                int affectedRows = pstmt.executeUpdate();
                System.out.println("Inserted " + affectedRows + " rows.");
            }
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

    public static void insertVaraus(int asiakas_id, int mokki_id, LocalDate varattu_pvm_alku, LocalDate varattu_pvm_loppu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO varaus (asiakas_id, mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asiakas_id);
            pstmt.setInt(2, mokki_id);

            LocalDate localDate_now = LocalDate.now();
            Date varattu_pvm = Date.valueOf(localDate_now);
            pstmt.setDate(3, varattu_pvm);
            pstmt.setDate(4, varattu_pvm);

            Date varattu_alkupvm_sql = Date.valueOf(varattu_pvm_alku);
            Date varattu_loppupvm_sql = Date.valueOf(varattu_pvm_loppu);
            pstmt.setDate(5, varattu_alkupvm_sql);
            pstmt.setDate(6, varattu_loppupvm_sql);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }
    public static List<Varaus> selectVarausByMokkiID(int mokki_id) {
        List<Varaus> varaukset = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM varaus WHERE mokki_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mokki_id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int asiakas_id = rs.getInt("asiakas_id");
                int mokkiId = rs.getInt("mokki_id");
                Date varattu_pvm = rs.getDate("varattu_pvm");
                Date vahvistus_pvm = rs.getDate("vahvistus_pvm");
                Date varattu_alkupvm = rs.getDate("varattu_alkupvm");
                Date varattu_loppupvm = rs.getDate("varattu_loppupvm");
                varaukset.add(new Varaus(asiakas_id, mokkiId, varattu_alkupvm.toLocalDate(), varattu_loppupvm.toLocalDate(), varattu_pvm.toLocalDate(), vahvistus_pvm.toLocalDate()));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return varaukset;
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
                int alue_id = rs.getInt("alue_id");
                String mokkinimi = rs.getString("mokkinimi");
                String postinro = rs.getString("postinro");
                String katuosoite = rs.getString("katuosoite");
                Double hinta = rs.getDouble("hinta");
                String kuvaus = rs.getString("kuvaus");
                int henkilomaara = rs.getInt("henkilomaara");
                String varustelu = rs.getString("varustelu");
                int mokki_id = rs.getInt("mokki_id");
                mokit.add(new Mokki(mokki_id, alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu));
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

    public static int selectAlueetByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int alue_id = 0;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM alue WHERE nimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                alue_id = rs.getInt("alue_id");

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return alue_id;
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

    public static void insertPalvelu(int alue_id, String nimi, String kuvaus, double hinta) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO palvelu (alue_id, nimi, kuvaus, hinta, alv) VALUES (?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,alue_id);
            pstmt.setString(2, nimi);
            pstmt.setString(3, kuvaus);
            pstmt.setDouble(4, hinta);
            pstmt.setDouble(5, hinta * 0.10);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }
    public static List<Palvelu> selectPalvelutByName(String name) {
        List<Palvelu> palvelut = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM palvelu WHERE nimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String nimi = rs.getString("nimi");
                int alue_id = rs.getInt("alue_id");
                String kuvaus = rs.getString("kuvaus");
                double hinta = rs.getDouble("hinta");
                palvelut.add(new Palvelu(alue_id, nimi, kuvaus, hinta));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return palvelut;
    }
    public static String getMokkiIdByMokkiName(String name){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String mokki_id = "";
        try {
            conn = getConnection();
            String sql = "SELECT * FROM mokki WHERE mokkinimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mokki_id = rs.getString("mokki_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return mokki_id;
    }
    public static List<Mokki> selectMokitByAlueId(int alueid) {
        List<Mokki> mokit = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM mokki WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, alueid);
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
                mokit.add(new Mokki(id, alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu));
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
    public static String getMokkiNameByMokkiID(int mokki_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String mokkinimi = "";
        try {
            conn = getConnection();
            String sql = "SELECT * FROM mokki WHERE mokki_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mokki_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mokkinimi = rs.getString("mokkinimi");

            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
    return mokkinimi;
    }
    public static String getAlueNameByAlueID(int alue_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String alue_nimi = "";
        try {
            conn = getConnection();
            String sql = "SELECT * FROM alue WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, alue_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alue_nimi = rs.getString("nimi");

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return alue_nimi;
    }
    public static void insertAsiakas(String etunimi, String sukunimi, String puhelinnro, String email, String lahiosoite, String postinro) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO asiakas (etunimi, sukunimi, puhelinnro, email, lahiosoite , postinro) VALUES (?, ?, ?, ?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, etunimi);
            pstmt.setString(2, sukunimi);
            pstmt.setString(3, puhelinnro);
            pstmt.setString(4, email);
            pstmt.setString(5, lahiosoite);
            pstmt.setString(6, postinro);


            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }
    public static void updateMokki(int mokki_id, int alue_id, String postinro, String mokkinimi, String katuosoite, double hinta, String kuvaus, int henkilomaara, String varustelu){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            String sql = "UPDATE mokki SET alue_id = ?, postinro = ?, katuosoite = ?, hinta = ?, kuvaus = ?, henkilomaara = ?, varustelu = ? WHERE mokki_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, alue_id);
            pstmt.setString(2, postinro);
            pstmt.setString(3, mokkinimi);
            pstmt.setString(4, katuosoite);
            pstmt.setDouble(5, hinta);
            pstmt.setString(6, kuvaus);
            pstmt.setInt(7, henkilomaara);
            pstmt.setString(8, varustelu);
            pstmt.setInt(9, mokki_id);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Updated " + affectedRows + "rows.");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }finally {
            close(pstmt);
            close(conn);

        }
    }

    public static List<Asiakas> selectAsiakasByName(String nimi) {
        List<Asiakas> asiakas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM asiakas WHERE sukunimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nimi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String postinro = rs.getString("postinro");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String lahiosoite = rs.getString("lahiosoite");
                String email = rs.getString("email");
                String puhelinnro = rs.getString("puhelinnro");
                asiakas.add(new Asiakas(postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return asiakas;
    }

}
