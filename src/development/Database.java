package development;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class Database {
    private final String HOST = "sql7.freesqldatabase.com";
    private final String USER = "sql7628410";
    private final String PASSWORD = "ua2Jkhhx4J";
    private final String PORT = "3306";
    private final String CONNECTION_URL = "jdbc:mysql://" + HOST + ':' + PORT + '/' + USER;
    private final DataFile data = new DataFile();

    private void connect(Consumer<Connection> func) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            func.accept(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setHighscore(int value) {
        connect(con -> {
            try {
                data.setHighscore(value);

                Statement stmt = con.createStatement();

                stmt.executeUpdate("DELETE FROM highscores WHERE id='" + data.getUUID() + '\'');
                stmt.executeUpdate("INSERT INTO highscores (id, value) VALUE ('" + data.getUUID() + "', " + value + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int getHighscore() {
        return data.getHighscore();
    }

    public HashMap<UUID, Integer> getSortedHighscores() {
        HashMap<UUID, Integer> result = new HashMap<>();

        connect(con -> {
            try {
                String query = "SELECT * FROM highscores ORDER BY value ASC";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("id"));
                    int value = rs.getInt("value");
                    result.put(uuid, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result;
    }
}