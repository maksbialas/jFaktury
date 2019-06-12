package faktury;

import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    public static void createDatabase() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database", "sa", "");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void createTable() throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database", "sa", "");

            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS `podmioty`;");

            statement.executeUpdate("CREATE TABLE `podmioty` (" +
                    "`Id` int(6) unsigned NOT NULL auto_increment," +
                    "`nazwa` varchar(50) default NULL," +
                    "`email` varchar(50) default NULL," +
                    "`adres` varchar(50) default NULL," +
                    "`nip` varchar(50) default NULL," +
                    "`tel` varchar(50) default NULL," +
                    "PRIMARY KEY  (Id)" +
                    ") ;");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void addSubjectEntry(int id, String nazwa, String email, String adres, String nip, String tel) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database", "sa", "");

            Statement statement = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `podmioty` (`Id`,`nazwa`,`email`, `adres`, `nip`, `tel`) VALUES (?,? ,? ,? ,? ,?);");
            stmt.setString(1, Integer.toString(id));
            stmt.setString(2, nazwa);
            stmt.setString(3, email);
            stmt.setString(4, adres);
            stmt.setString(5, nip);
            stmt.setString(6, tel);

            stmt.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void updateSubjectEntry(int id, String nazwa, String email, String adres, String nip, String tel) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database", "sa", "");

            PreparedStatement stmt = conn.prepareStatement("UPDATE `podmioty` SET `nazwa` = ?,`email` = ?, `adres` = ?, `nip` = ?, `tel` = ? WHERE `Id`= ?");
            stmt.setString(1, nazwa);
            stmt.setString(2, email);
            stmt.setString(3, adres);
            stmt.setString(4, nip);
            stmt.setString(5, tel);
            stmt.setString(6, Integer.toString(id));
            stmt.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static ArrayList<String> getDefaultSeller() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:./data/database", "sa",
                    "");

            Statement statement = conn.createStatement();

            statement.execute("SELECT * FROM podmioty WHERE Id = 1");
            ResultSet rs = statement.getResultSet();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                for (int ii = 2; ii <= md.getColumnCount(); ii++) {
                    list.add(rs.getObject(ii) + "");
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public static ArrayList<String> getSubjectNames() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:./data/database", "sa",
                    "");

            Statement statement = conn.createStatement();

            statement.execute("SELECT nazwa FROM podmioty");
            ResultSet rs = statement.getResultSet();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                for (int ii = 1; ii <= md.getColumnCount(); ii++) {
                    list.add(rs.getObject(ii) + "");
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
//        list.remove(0);
        return list;
    }

    public static ArrayList<String> getSubjectByName(String nazwa) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:./data/database", "sa",
                    "");

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM podmioty WHERE nazwa = ?");
            stmt.setString(1, nazwa);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                for (int ii = 2; ii <= md.getColumnCount(); ii++) {
                    list.add(rs.getObject(ii) + "");
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public static void deleteByName(String nazwa) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:./data/database", "sa",
                    "");

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM podmioty WHERE nazwa = ?");
            stmt.setString(1, nazwa);
            stmt.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static int getMaxId() throws SQLException {
        Connection conn = null;
        int max = 0;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:./data/database", "sa",
                    "");

            Statement statement = conn.createStatement();

            statement.execute("SELECT Id FROM podmioty WHERE Id = (SELECT max(Id) FROM podmioty)");
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                max = Integer.parseInt(rs.getObject(1) + "");
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return max;
    }
}
