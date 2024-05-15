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
                int varaus_id = rs.getInt("varaus_id");
                varaukset.add(new Varaus(asiakas_id, mokkiId, varattu_alkupvm.toLocalDate(), varattu_loppupvm.toLocalDate(), varattu_pvm.toLocalDate(), vahvistus_pvm.toLocalDate(), varaus_id));
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
    public static int getAsiakasIdBySukunimi(String sukunimi){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int asiakas_id = 0;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM asiakas WHERE sukunimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sukunimi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                asiakas_id = rs.getInt("asiakas_id");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return asiakas_id;
    }
    public static void updateVaraus(int varaus_id, int asiakas_id, int mokki_id, LocalDate varattu_pvm_alku, LocalDate varattu_pvm_loppu){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            String sql = "UPDATE varaus SET asiakas_id = ?, mokki_id = ?, varattu_alkupvm = ?, varattu_loppupvm = ? WHERE varaus_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asiakas_id);
            pstmt.setInt(2, mokki_id);
            Date varattu_alkupvm_sql = Date.valueOf(varattu_pvm_alku);
            Date varattu_loppupvm_sql = Date.valueOf(varattu_pvm_loppu);
            pstmt.setDate(3, varattu_alkupvm_sql);
            pstmt.setDate(4, varattu_loppupvm_sql);
            pstmt.setInt(5, varaus_id);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Updated " + affectedRows + "rows.");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }finally {
            close(pstmt);
            close(conn);

        }
    }
    public static List<Varaus> selectVarausByAsiakasID(int asiakas_id){
        List<Varaus> varaukset = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM varaus WHERE asiakas_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asiakas_id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int mokki_id = rs.getInt("mokki_id");
                int asiakasId = rs.getInt("asiakas_id");
                Date varattu_pvm = rs.getDate("varattu_pvm");
                Date vahvistus_pvm = rs.getDate("vahvistus_pvm");
                Date varattu_alkupvm = rs.getDate("varattu_alkupvm");
                Date varattu_loppupvm = rs.getDate("varattu_loppupvm");
                int varaus_id = rs.getInt("varaus_id");
                varaukset.add(new Varaus(asiakasId, mokki_id, varattu_alkupvm.toLocalDate(), varattu_loppupvm.toLocalDate(), varattu_pvm.toLocalDate(), vahvistus_pvm.toLocalDate(), varaus_id));
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
        String alueenNimi = "";
        try {
            conn = getConnection();
            String sql = "SELECT * FROM alue WHERE nimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                alue_id = rs.getInt("alue_id");
                alueenNimi = rs.getString("nimi");
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

    public static List<Alue> selectAlue(String nimi) {
        List<Alue> alue = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM alue WHERE nimi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nimi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("alue_id");
                String alue_nimi = rs.getString("nimi");
                alue.add(new Alue(alue_nimi, id));
            }
            System.out.println("here" + alue);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return alue;
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
                int id = rs.getInt("palvelu_id");
                String nimi = rs.getString("nimi");
                int alue_id = rs.getInt("alue_id");
                String kuvaus = rs.getString("kuvaus");
                double hinta = rs.getDouble("hinta");
                palvelut.add(new Palvelu(id, alue_id, nimi, kuvaus, hinta));
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

    public static void deletePalveluById(int id) {
        Connection conn = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM palvelu WHERE palvelu_id = ?";
            deleteStmt = conn.prepareStatement(sql);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            conn.commit();
            System.out.println("All related varaus records deleted.");

        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(deleteStmt);
            close(conn);
        }
    }

    public static void updatePaveluById(int Id, int alue_id, String nimi, String kuvaus, double hinta) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            String sql = "UPDATE palvelu SET alue_id = ?, nimi = ?, kuvaus = ?, hinta = ?, alv = ? WHERE palvelu_id = ?";
            double alv = hinta * 0.10;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, alue_id);
            pstmt.setString(2, nimi);
            pstmt.setString(3, kuvaus);
            pstmt.setDouble(4, hinta);
            pstmt.setDouble(5, alv);
            pstmt.setInt(6, Id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Updated " + affectedRows + "rows.");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }finally {
            close(pstmt);
            close(conn);

        }
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
            String sql = "UPDATE mokki SET alue_id = ?, postinro = ?,mokkinimi = ?, katuosoite = ?, hinta = ?, kuvaus = ?, henkilomaara = ?, varustelu = ? WHERE mokki_id = ?";
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

    public static void deleteMokkiById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            deleteVarausByMokkiId(id);
            conn = getConnection();
            String sql = "DELETE mokki FROM mokki WHERE mokki_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteVarausByMokkiId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT varaus_id FROM varaus WHERE mokki_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            String sql_delete = "DELETE FROM varaus WHERE varaus_id = ?";
            deleteStmt = conn.prepareStatement(sql_delete);

            while (rs.next()) {
                int varaus_id = rs.getInt("varaus_id");
                deleteStmt.setInt(1, varaus_id);
                deleteStmt.executeUpdate();
            }
            conn.commit();
            System.out.println("All related varaus records deleted.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
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
                int id = rs.getInt("asiakas_id");
                String postinro = rs.getString("postinro");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                String lahiosoite = rs.getString("lahiosoite");
                String email = rs.getString("email");
                String puhelinnro = rs.getString("puhelinnro");
                asiakas.add(new Asiakas(id, postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro));
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

    public static void updateAsiakasById(int id, Asiakas muokattavaAsiakas) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE asiakas SET etunimi = ?, sukunimi = ?, lahiosoite = ?, email = ?, puhelinnro = ? WHERE asiakas_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, muokattavaAsiakas.getEtunimi());
            pstmt.setString(2, muokattavaAsiakas.getSukunimi());
            pstmt.setString(3, muokattavaAsiakas.getLahiosoite());
            pstmt.setString(4, muokattavaAsiakas.getEmail());
            pstmt.setString(5, muokattavaAsiakas.getPuhelinnro());
            pstmt.setInt(6, id);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Update error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteAsiakasById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            deleteVarausByAsiakasId(id);
            conn = getConnection();
            String sql = "DELETE asiakas FROM asiakas WHERE asiakas_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteVarausByAsiakasId(int asiakas_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT varaus_id FROM varaus WHERE asiakas_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, asiakas_id);
            rs = pstmt.executeQuery();

            String sql_delete = "DELETE FROM varaus WHERE varaus_id = ?";
            deleteStmt = conn.prepareStatement(sql_delete);

            while (rs.next()) {
                int varaus_id = rs.getInt("varaus_id");
                deleteStmt.setInt(1, varaus_id);
                deleteStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("All related varaus records deleted.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }


    public static void updateAlue(int alue_id, String nimi){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            System.out.println(alue_id + nimi);
            conn = getConnection();
            String sql = "UPDATE alue SET nimi = ? WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nimi);
            pstmt.setInt(2, alue_id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Updated " + affectedRows + "rows.");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }finally {
            close(pstmt);
            close(conn);

        }
    }

    public static void deletePalveluByAlueId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT palvelu_id FROM palvelu WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            String sql_delete = "DELETE FROM palvelu WHERE palvelu_id = ?";
            deleteStmt = conn.prepareStatement(sql_delete);

            while (rs.next()) {
                int palvelu_id = rs.getInt("palvelu_id");
                deleteStmt.setInt(1, palvelu_id);
                deleteStmt.executeUpdate();
            }
            conn.commit();
            System.out.println("All related varaus records deleted.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteMokkiByAlueId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT mokki_id FROM mokki WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            String sql_delete = "DELETE FROM mokki WHERE mokki_id = ?";
            deleteStmt = conn.prepareStatement(sql_delete);

            while (rs.next()) {
                int mokki_id = rs.getInt("mokki_id");
                deleteStmt.setInt(1, mokki_id);
                deleteStmt.executeUpdate();
            }
            conn.commit();
            System.out.println("All related varaus records deleted.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteAlueById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            deletePalveluByAlueId(id);
            deleteMokkiByAlueId(id);
            String sql = "DELETE FROM alue WHERE alue_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    public static void deleteVarausById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM varaus WHERE varaus_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Inserted " + affectedRows + " rows.");
        } catch (Exception ex) {
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            close(pstmt);
            close(conn);
        }
    }
}
