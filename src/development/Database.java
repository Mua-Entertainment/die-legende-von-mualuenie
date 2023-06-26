package development;

import engine.SafeList;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private final String HOST = "sql7.freesqldatabase.com";
    private final String USER = "sql7628410";
    private final String PASSWORD = "ua2Jkhhx4J";
    private final String PORT = "3306";
    private final String CONNECTION_URL = "jdbc:mysql://" + HOST + ':' + PORT + '/' + USER;
    private DataFile data;
    private Connection con;
    private boolean connected;

    public Database() {
        connected = true;
        data = new DataFile();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            connected = false;
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    private void disconnect(SQLException e) {
        connected = false;
        e.printStackTrace();
    }

    public void setHighscore(int value) {
        try {
            data.setHighscore(value);

            if (connected) {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO highscores (id, value) VALUE ('" + data.getUUID() + "', " + value + ")");
                stmt.executeUpdate("REMOVE FROM highscores WHERE id = '" + data.getUUID() + '\'');
            }
        } catch (SQLException e) {
            disconnect(e);
        }
    }

    public int getHighscore() {
        return data.getHighscore();
    }

    public HashMap<UUID, Integer> getSortedHighscores() {
        HashMap<UUID, Integer> result = new HashMap<>();

        try {
            String query = "SELECT * FROM highscores ORDER BY value ASC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("id"));
                int value = rs.getInt("value");
                result.put(uuid, value);
            }
        } catch (SQLException e) {
            disconnect(e);
        }

        return result;
    }
}